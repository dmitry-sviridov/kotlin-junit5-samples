package mockito

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import ru.otus.*
import kotlin.test.Test
import kotlin.test.assertEquals


@ExtendWith(MockitoExtension::class)
class TestEnterpriseCalculator {

    @Test
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

    @Mock
    lateinit var annotatedMockedCalculator: SumCalculator

    @Test
    fun `test annotated mocked arithmetic calculator sum component`() {
        `when`(annotatedMockedCalculator.sum(anyInt(), anyInt())).thenReturn(30)

        val calculator = MathCalculator(
            arithmeticCalculator = ArithmeticArithmeticCalculatorImpl(
                sumCalculator = annotatedMockedCalculator,
                subtractCalculator = SubtractCalculator(),
                multiplyCalculator = MultiplyCalculator(),
                divideCalculator = DivideCalculator()
            ),
            trigonometryCalculator = TrigonometryCalculator()
        )

        assertEquals(0.5, calculator.calculateSinOfSum(15, 15), absoluteTolerance = 0.001)
        verify(annotatedMockedCalculator).sum(15, 15).times(1)
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

    @Test
    fun `Argument matchers - argThat sample but error`() {
        val calculator = mock(SumCalculator::class.java)
        `when`(calculator.sum(argThat { it % 2 == 0 }, argThat { it % 2 == 0 })).thenReturn(20)
        `when`(calculator.sum(argThat { it % 2 == 1 }, argThat { it % 2 == 1 })).thenReturn(11111)

        assertEquals(20, calculator.sum(2, 2))
        assertEquals(11111, calculator.sum(1, 3))
    }

    @Test
    fun `Argument matchers - argThat sample pass, mockito-kotlin used`() {
        val calculator: SumCalculator = org.mockito.kotlin.mock()
        whenever(calculator.sum(
            org.mockito.kotlin.argThat { it -> it % 2 == 0 },
            org.mockito.kotlin.argThat { it -> it % 2 == 0 }
        )).thenReturn(20)
        whenever(calculator.sum(
            org.mockito.kotlin.argThat { it -> it % 2 == 1 },
            org.mockito.kotlin.argThat { it -> it % 2 == 1 }
        )).thenReturn(11111)

        assertEquals(20, calculator.sum(2, 2))
        assertEquals(11111, calculator.sum(1, 3))
    }


    @Test
    fun `do answer sample`() {
        val calculator: SumCalculator = org.mockito.kotlin.mock()

        org.mockito.kotlin.doAnswer {
            val first = it.arguments[0] as Int
            val second = it.arguments[1] as Int
            if (first % 2 == 0 && second % 2 == 0) {
                return@doAnswer 20
            }
            else if (first % 2 == 1 && second % 2 == 1) {
                return@doAnswer 11111
            }
            else {
                return@doAnswer 0
            }
        }.`when`(calculator).sum(org.mockito.kotlin.any(), org.mockito.kotlin.any())

        assertEquals(20, calculator.sum(2, 2))
        assertEquals(11111, calculator.sum(1, 3))
    }
}