import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import test_instance_post_processor.ServiceInitializationExtension
import test_instance_post_processor.HelloService
import kotlin.test.Test

@ExtendWith(ServiceInitializationExtension::class)
class PostProcessorTest {

    private lateinit var helloService: HelloService

    @Test
    fun testService() {
        val result = helloService.sayHello()
        Assertions.assertEquals("hello!", result)
    }
}