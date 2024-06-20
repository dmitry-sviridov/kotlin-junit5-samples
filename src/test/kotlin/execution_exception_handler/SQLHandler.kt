package execution_exception_handler

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler
import java.sql.SQLException

@Target(AnnotationTarget.FUNCTION)
@ExtendWith(RetrySQLErrorExtension::class)
annotation class RetrySQLException(val maxRetries: Int = 3)


class RetrySQLErrorExtension : TestExecutionExceptionHandler {

    override fun handleTestExecutionException(context: ExtensionContext, throwable: Throwable) {
        val testMethod = context.testMethod.get()
        val annotation = testMethod.getAnnotation(RetrySQLException::class.java)
        if (throwable is SQLException && annotation != null) {
            val store = context.getStore(ExtensionContext.Namespace.GLOBAL)
            val retries = store.getOrDefault(context.uniqueId, Int::class.java, annotation.maxRetries)
            if (retries < 1) {
                throw throwable
            }
            store.put(context.uniqueId, retries - 1)
            println("Retrying test: " + context.displayName)
            context.testInstanceLifecycle.ifPresent { lifecycle ->
                try {
                    if (lifecycle == TestInstance.Lifecycle.PER_CLASS) {
                        context.requiredTestMethod.invoke(context.requiredTestInstance)
                    } else {
                        context.requiredTestMethod
                            .invoke(context.requiredTestClass.getDeclaredConstructor().newInstance())
                    }
                } catch (e: Exception) {
                    handleTestExecutionException(context, e.cause ?: e)
                }
            }
        } else {
            throw throwable
        }
    }
}
