import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.*
import ru.otus.Day
import ru.otus.isWeekend
import java.util.stream.Stream

class ParametrizedDemoTest {

    @ParameterizedTest
    @ValueSource(strings = ["racecar", "radar", "level"])
    fun `test checks if string is a palindrome`(word: String) {
        Assertions.assertTrue(word == word.reversed(), "$word should be a palindrome")
    }

    @ParameterizedTest
    @EnumSource(value = Day::class, names = ["SATURDAY", "SUNDAY"])
    fun `test checks if day is a weekend`(day: Day) {
        Assertions.assertTrue(
            day.isWeekend(),
            "$day should be a weekend"
        )
    }

    @ParameterizedTest
    @EnumSource(value = Day::class, names = ["SATURDAY", "SUNDAY"], mode = EnumSource.Mode.EXCLUDE)
    fun `test checks if day is not weekend`(day: Day) {
        Assertions.assertFalse(
            day.isWeekend(),
            "$day should not be a weekend"
        )
    }

    @ParameterizedTest
    @CsvSource("1, 1, 2", "2, 3, 5", "3, 5, 8")
    fun `test adds two numbers`(a: Int, b: Int, expected: Int) {
        Assertions.assertEquals(expected, a + b, "$a + $b should equal $expected")
    }

    @ParameterizedTest
    @MethodSource("provideAdditionArguments")
    fun `test adds two numbers with method source`(a: Int, b: Int, expected: Int) {
        Assertions.assertEquals(expected, a + b, "$a + $b should equal $expected")
    }

    companion object {
        @JvmStatic
        fun provideAdditionArguments(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(1, 1, 2),
                Arguments.of(2, 3, 5),
                Arguments.of(3, 5, 8)
            )
        }
    }
}