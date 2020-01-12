package architecture

import com.github.anddd7.application.StoryCase
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ApplicationLayerTest {
  private lateinit var compiledClasses: JavaClasses

  @BeforeAll
  fun beforeAll() {
    compiledClasses = LayeredArchitectureTest.loadClasses()
  }

  @Test
  fun `story case should annotated with spring's @Service`() {
    ArchRuleDefinition.classes()
        .that().resideInAPackage(LayeredArchitectureTest.PersistencePackage)
        .and().implement(StoryCase::class.java)
        .should().beAnnotatedWith(org.springframework.stereotype.Service::class.java)
        .`as`("The story case should be annotated with spring's 'Service'.")
        .check(compiledClasses)
  }

  @Test
  fun `verify name ending`() {
    ArchRuleDefinition.classes()
        .that().resideInAPackage(LayeredArchitectureTest.ApplicationPackage)
        .and().implement(StoryCase::class.java)
        .should().haveSimpleNameEndingWith("StoryCase")
        .`as`("The use cases should be named ending with 'StoryCase'.")
        .check(compiledClasses)
  }

  @Test
  fun `verify interface implementation`() {
    ArchRuleDefinition.classes()
        .that().resideInAPackage(LayeredArchitectureTest.ApplicationPackage)
        .and().haveSimpleNameEndingWith("StoryCase")
        .and().areNotInterfaces()
        .should().implement(StoryCase::class.java)
        .`as`("The use cases should implement 'StoryCase' interface.")
        .check(compiledClasses)
  }
}
