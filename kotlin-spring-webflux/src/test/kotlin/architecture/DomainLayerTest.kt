package architecture

import com.github.anddd7.domain.AggregateRoot
import com.github.anddd7.domain.Entity
import com.github.anddd7.domain.Factory
import com.github.anddd7.domain.Processor
import com.github.anddd7.domain.ReadModel
import com.github.anddd7.domain.Repository
import com.github.anddd7.domain.Service
import com.github.anddd7.domain.ValueObject
import com.github.anddd7.domain.core.Criteria
import com.github.anddd7.domain.core.DomainException
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
internal class DomainLayerTest {
  private lateinit var compiledClasses: JavaClasses

  @BeforeAll
  fun beforeAll() {
    compiledClasses = LayeredArchitectureTest.loadClasses()
  }

  @Test
  fun `domain shouldn't depend on external resource`() {
    ArchRuleDefinition.classes()
        .that().resideInAPackage(LayeredArchitectureTest.DomainPackage)
        .should().onlyDependOnClassesThat().resideInAnyPackage("java..", "kotlin..", "..domain..")
        .`as`("The domain layer should only depend on the classes in the package of java, kotlin and domain.")
        .check(compiledClasses)
  }

  @Test
  fun `domain should implement specific interface`() {
    ArchRuleDefinition.classes()
        .that().resideInAPackage(LayeredArchitectureTest.DomainContextPackage)
        .should().implement(Entity::class.java)
        .orShould().implement(AggregateRoot::class.java)
        .orShould().implement(ValueObject::class.java)
        .orShould().implement(ReadModel::class.java)
        .orShould().implement(Service::class.java)
        .orShould().implement(Factory::class.java)
        .orShould().beAssignableTo(Repository::class.java)
        .orShould().beAssignableTo(DomainException::class.java)
        .orShould().beAssignableTo(Criteria::class.java)
        .`as`("The models in the domain should implement or extend one of the interfaces / classes in  Entity, AggregateRoot, ValueObject, ReadModel, WriteModel,  Service, Policy, Factory, Repository, DomainException, Criteria.")
        .check(compiledClasses)
  }

  @Suppress("UNCHECKED_CAST")
  fun nameRules(): Stream<Arguments> = Stream.of(
      Service::class.java,
      Factory::class.java,
      Repository::class.java,
      Processor::class.java,
      DomainException::class.java to "Exception",
      Criteria::class.java
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
        .that().resideInAPackage(LayeredArchitectureTest.DomainPackage)
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
        .that().resideInAPackage(LayeredArchitectureTest.DomainPackage)
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
