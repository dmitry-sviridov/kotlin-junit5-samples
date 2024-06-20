import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import parameter_resolver.RandomInt
import parameter_resolver.RandomNumberExtension

@ExtendWith(RandomNumberExtension::class)
class ParameterResolverTest {

    @Test
    fun `test use random number injected from parameter resolver`(
        @RandomInt(min = 1, max = 100) number: Int
    ) {
        Assertions.assertTrue(number in 1..100, "Number should be in range 1 to 100")
    }
}