package mockito

import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import ru.otus.*
import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class TestMockito {

    @RepeatedTest(10)
    fun `test calculator without mock`() {
        val calculator = MathCalculator(
            arithmeticCalculator = ArithmeticArithmeticCalculatorImpl(
                sumCalculator = SumCalculator(),
                subtractCalculator = SubtractCalculator(),
                multiplyCalculator = MultiplyCalculator(),
                divideCalculator = DivideCalculator()
            ),
            trigonometryCalculator = TrigonometryCalculator()
        )

        assertEquals(0.5, calculator.calculateSinOfSum(15, 15), absoluteTolerance = 0.001)
    }

    @Test
    fun `test mocked arithmetic calculator sum component`() {
        val sumCalculator = mock(SumCalculator::class.java)
        `when`(sumCalculator.sum(anyInt(), anyInt())).thenReturn(30)

        val calculator = MathCalculator(
            arithmeticCalculator = ArithmeticArithmeticCalculatorImpl(
                sumCalculator = sumCalculator,
                subtractCalculator = SubtractCalculator(),
                multiplyCalculator = MultiplyCalculator(),
                divideCalculator = DivideCalculator()
            ),
            trigonometryCalculator = TrigonometryCalculator()
        )

        assertEquals(0.5, calculator.calculateSinOfSum(15, 15), absoluteTolerance = 0.001)
        verify(sumCalculator).sum(15, 15).times(1)
    }

    @Test
    fun `test mocked arithmetic calculator exception thrown`() {
        val sumCalculator = mock(SumCalculator::class.java)
        `when`(sumCalculator.sum(anyInt(), anyInt())).thenThrow(IllegalArgumentException::class.java)

        val calculator = MathCalculator(
            arithmeticCalculator = ArithmeticArithmeticCalculatorImpl(
                sumCalculator = sumCalculator,
                subtractCalculator = SubtractCalculator(),
                multiplyCalculator = MultiplyCalculator(),
                divideCalculator = DivideCalculator()
            ),
            trigonometryCalculator = TrigonometryCalculator()
        )

        assertThrows<IllegalArgumentException> {
            calculator.calculateSinOfSum(-1, -1)
        }
    }
}