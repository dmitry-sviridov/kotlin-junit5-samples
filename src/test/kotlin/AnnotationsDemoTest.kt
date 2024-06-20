import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AnnotationsDemoTest {

    companion object {
        @JvmStatic
        @BeforeAll
        fun `method will be called once before all tests`() {
            println("method will be called before all tests")
        }

        @JvmStatic
        @AfterAll
        fun `method will be called once after all tests`() {
            println("method will be called after all tests")
        }
    }


    @BeforeEach
    fun `method will be called before each test`() {
        println("method will be called before each test")
    }

    @AfterEach
    fun `method will be called after each test`() {
        println("method will be called after each test")
    }


    @Test
    fun `simple test will pass`() {
        Assertions.assertTrue(true)
    }

    @Test
    fun `another simple test will pass`() {
        Assertions.assertEquals(2, 1 + 1)
    }

    @RepeatedTest(5)
    fun `test repeated`() {
        val number = (1..100).random()
        println("Generated number: $number")
        Assertions.assertTrue(number in 1..100, "Number should be in range 1 to 100")
    }

    @Test
    fun `another simple test will fail`() {
        Assertions.assertEquals(2, 1 + 2)
    }

    @Test
    fun `test checks if exception is thrown`() {

        fun doSomethingDangerous() {
            throw IllegalArgumentException()
        }

        Assertions.assertThrows(IllegalArgumentException::class.java) {
            doSomethingDangerous()
        }
    }

    @Test
    @Disabled
    fun `disabled test will not be executed`() {
        Assertions.assertEquals(2, "10000")
    }
}