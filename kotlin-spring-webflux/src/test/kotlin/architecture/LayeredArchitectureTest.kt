package architecture

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.library.Architectures
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class LayeredArchitectureTest {
  private lateinit var compiledClasses: JavaClasses

  @BeforeAll
  fun beforeAll() {
    compiledClasses = loadClasses()
  }

  @Test
  fun layer_dependencies_must_be_respected_include_the_tests() {
    Architectures.layeredArchitecture()
        .layer("Rest").definedBy(RestPackage)
        .layer("Persistence").definedBy(PersistencePackage)
        .layer("Client").definedBy(ClientPackage)
        .layer("Application").definedBy(ApplicationPackage)
        .layer("Domain").definedBy(DomainPackage)
        // respond outside request, combine/mapping the data, transfer to Reactive
        .whereLayer("Rest").mayNotBeAccessedByAnyLayer()
        // implementation of repository
        .whereLayer("Persistence").mayNotBeAccessedByAnyLayer()
        // external service/resource
        .whereLayer("Client").mayOnlyBeAccessedByLayers("Rest")
        // internal service
        .whereLayer("Application").mayOnlyBeAccessedByLayers("Rest")
        .`as`("The layer dependencies must be respected (exclude the tests)")
        .because("we must follow the DIP principle.")
        .check(compiledClasses)
  }

  companion object {
    private const val BasePackage = "com.github.anddd7"
    const val ApplicationPackage = "$BasePackage.application.."
    const val DomainPackage = "$BasePackage.domain.."
    const val DomainContextPackage= "$BasePackage.domain.context.."
    const val AdapterPackage= "$BasePackage.adapters.."
    const val RestPackage = "$BasePackage.adapters.inbound.."
    const val PersistencePackage = "$BasePackage.adapters.outbound.persistence.."
    const val ClientPackage = "$BasePackage.adapters.outbound.client.."

    fun loadClasses(): JavaClasses =
        ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(BasePackage)
  }
}
