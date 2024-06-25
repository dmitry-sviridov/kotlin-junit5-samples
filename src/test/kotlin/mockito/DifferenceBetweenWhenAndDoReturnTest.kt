package mockito

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class DifferenceBetweenWhenAndDoReturnTest {
    /*
    Базируется на ответе на вопрос о разнице между doReturn -> `when` и `when` -> thanReturn
    https://stackoverflow.com/a/41964743/9672005
     */

    @Spy
    val list: MutableList<Int> = mutableListOf()


    @Test
    fun `will fail because IndexOutOfBoundsException`() {
        `when`(list[0]).thenReturn(1000)

        assertEquals(1000, list[0])
    }

    @Test
    fun `will pass`() {
        doReturn(1000).`when`(list)[0]
        assertEquals(1000, list[0])
    }
}
