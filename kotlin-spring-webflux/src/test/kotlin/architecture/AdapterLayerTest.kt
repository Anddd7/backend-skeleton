package architecture

import com.github.anddd7.adapters.Client
import com.github.anddd7.adapters.Handler
import com.github.anddd7.adapters.PersistenceObject
import com.github.anddd7.adapters.ReactiveHandler
import com.github.anddd7.adapters.RequestDTO
import com.github.anddd7.adapters.ResponseDTO
import com.github.anddd7.domain.Repository
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AdapterLayerTest {
  private lateinit var compiledClasses: JavaClasses

  @BeforeAll
  fun beforeAll() {
    compiledClasses = LayeredArchitectureTest.loadClasses()
  }

  @Test
  fun `repository should annotated with spring's @Repository`() {
    ArchRuleDefinition.classes()
        .that().resideInAPackage(LayeredArchitectureTest.PersistencePackage)
        .and().implement(Repository::class.java)
        .should().beAnnotatedWith(org.springframework.stereotype.Repository::class.java)
        .`as`("The repository implement should be annotated with spring's 'Repository'.")
        .check(compiledClasses)
  }

  @Test
  fun `handler and client should annotated with spring's @Component`() {
    ArchRuleDefinition.classes()
        .that().resideInAPackage(LayeredArchitectureTest.PersistencePackage)
        .and()
        .implement(Handler::class.java)
        .or().implement(ReactiveHandler::class.java)
        .or().implement(Client::class.java)
        .should().beAnnotatedWith(org.springframework.stereotype.Component::class.java)
        .`as`("The handler should be annotated with spring's 'Component'.")
        .check(compiledClasses)
  }

  @Suppress("UNCHECKED_CAST")
  fun nameRules(): Stream<Arguments> = Stream.of(
      RequestDTO::class.java to "Request",
      ResponseDTO::class.java to "DTO",
      Handler::class.java,
      ReactiveHandler::class.java to "Handler",
      Client::class.java,
      PersistenceObject::class.java to "PO"
  )
      .map {
        if (it is Pair<*, *>) {
          Arguments.of(it.first, it.second)
        } else {
          Arguments.of(it as Class<Any>, it.simpleName)
        }
      }

  @ParameterizedTest(name = "{index} {0} should be named ending with {1}")
  @MethodSource("nameRules")
  fun `verify name ending`(clazz: Class<Any>, suffix: String?) {
    val endingNaming = suffix ?: clazz.simpleName

    ArchRuleDefinition.classes()
        .that().resideInAPackage(LayeredArchitectureTest.AdapterPackage)
        .and().run {
          if (clazz.isInterface) implement(clazz) else areAssignableTo(clazz)
        }
        .should().haveSimpleNameEndingWith(endingNaming)
        .`as`("The domain services should be named ending with '$endingNaming'.")
        .check(compiledClasses)
  }

  @ParameterizedTest(name = "{index} {1} should implement {0}")
  @MethodSource("nameRules")
  fun `verify interface implementation`(clazz: Class<Any>, suffix: String?) {
    val endingNaming = suffix ?: clazz.simpleName

    ArchRuleDefinition.classes()
        .that().resideInAPackage(LayeredArchitectureTest.AdapterPackage)
        .and().haveSimpleNameEndingWith(endingNaming)
        .and().areNotInterfaces()
        .should()
        .run {
          if (clazz.isInterface) implement(clazz) else beAssignableTo(clazz)
        }
        .`as`("The domain services should implement '$endingNaming' interface.")
        .check(compiledClasses)
  }
}
