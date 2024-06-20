package parameter_resolver

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import kotlin.random.Random

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class RandomInt(val min: Int = Integer.MIN_VALUE, val max: Int = Integer.MAX_VALUE - 1)


class RandomNumberExtension : ParameterResolver {

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Boolean {
        return parameterContext.parameter.type == Int::class.java &&
                parameterContext.isAnnotated(RandomInt::class.java)
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Int {
        val numberAnnotation = parameterContext.findAnnotation(RandomInt::class.java).get()
        val min = numberAnnotation.min
        val max = numberAnnotation.max
        return Random.nextInt(min, max + 1)
    }
}