import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class TaggedTests {

    @Test
    @Tag("A")
    fun `A 1`() {
        println("Running test A1")
        Assertions.assertTrue(true)
    }

    @Test
    @Tag("A")
    fun `A 2`() {
        println("Running test A2")
        Assertions.assertTrue(false)
    }

    @Test
    @Tag("B")
    fun `B 1`() {
        Assertions.assertTrue(true)
    }


    @Test
    @Tag("B")
    fun `B 2`() {
        Assertions.assertTrue(true)
    }
}