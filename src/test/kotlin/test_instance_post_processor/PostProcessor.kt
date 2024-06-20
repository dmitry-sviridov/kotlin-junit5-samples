package test_instance_post_processor

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestInstancePostProcessor

class HelloService {
    fun sayHello() = "hello!"
}


class ServiceInitializationExtension : TestInstancePostProcessor {

    override fun postProcessTestInstance(testInstance: Any, context: ExtensionContext) {
        val serviceField = testInstance::class.java.getDeclaredField("helloService")
        serviceField.isAccessible = true
        serviceField.set(testInstance, HelloService())
    }
}
