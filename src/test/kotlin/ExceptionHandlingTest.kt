import execution_exception_handler.RetrySQLException
import java.lang.Thread.sleep
import java.net.ConnectException
import java.sql.SQLException
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue

class ExceptionHandlingTest {

    @RetrySQLException
    @Test
    fun testThrowsSQLException() {
        println("Running testThrowsSQLException")
        sleep(1000)
        if (Random.nextInt(from = 0, until = 100) < 70) {
            throw SQLException("SQL connection error")
        }
    }

    @RetrySQLException
    @Test
    fun testOk() {
        println("Running testOk")
        assertTrue(true)
    }

    @RetrySQLException
    @Test
    fun testThrowsConnectException() {
        println("Running testThrowsConnectException")
        sleep(1000)
        throw ConnectException("HTTP connection error")
    }
}