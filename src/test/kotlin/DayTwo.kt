import com.eder.advent.DataReader
import org.junit.jupiter.api.Test

class DayTwo {

    val FORWARD = "forward"
    val DOWN = "down"
    val UP = "up"

    @Test
    fun dayTwo_partOne() {
        val reader = DataReader(javaClass.getResource("dayTwoInput.txt").readText())

        var horizontalPosition = 0
        var verticalPosition = 0

        reader.getLines().forEach {
            val direction = getDirection(it)
            val amount = getAmount(it)

            when (direction) {
                FORWARD -> horizontalPosition += amount
                DOWN -> verticalPosition += amount
                UP -> verticalPosition -= amount
            }
        }

        println(horizontalPosition * verticalPosition)
    }


    @Test
    fun dayTwo_partTwo() {
        val reader = DataReader(javaClass.getResource("dayTwoInput.txt").readText())

        var aim = 0
        var horizontalPosition = 0
        var verticalPosition = 0


        reader.getLines().forEach {

            val direction = getDirection(it)
            val amount = getAmount(it)

            when (direction) {
                FORWARD -> {
                    horizontalPosition += amount
                    verticalPosition += aim * amount
                }
                DOWN -> aim += amount
                UP -> aim -= amount
            }
        }

        println(horizontalPosition * verticalPosition)
    }

    fun getDirection(line: String) = line.split(" ")[0]
    fun getAmount(line: String) = line.split(" ")[1].toInt()

}