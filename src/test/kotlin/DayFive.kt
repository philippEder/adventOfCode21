import com.eder.advent.DataReader
import com.eder.advent.five.Field
import com.eder.advent.five.Line
import org.junit.jupiter.api.Test

class DayFive {

    @Test
    fun partOne() {
        val lines = setUpLines()
        val straightLines = lines.filter { it.x1 == it.x2 || it.y1 == it.y2 }
        val fields = getFields(lines)

        fields.forEach { field ->
            straightLines.forEach { line ->
                if (line.usesField(field))
                    field.count++
            }
        }

        println(fields.filter { it.count > 1 }.count())
    }

    @Test
    fun partTwo() {
        val lines = setUpLines()
        val fields = getFields(lines)

        fields.forEach { field ->
            lines.forEach { line ->
                if (line.usesField(field))
                    field.count++
            }
        }

        println(fields.filter { it.count > 1 }.count())
    }

    private fun setUpLines(): List<Line> {
        val reader = DataReader(javaClass.getResource("dayFiveInput.txt").readText())
        return reader.getLines().map {
            val values = it.split("->", ",").map { it.replace(" ", "").toInt() }
            Line(values[0], values[1], values[2], values[3])
        }
    }

    private fun getBiggestX(lines: List<Line>): Int {
        val biggestX1 = lines.maxOfOrNull { it.x1 }
        val biggestX2 = lines.maxOfOrNull { it.x2 }
        return maxOf(biggestX1 ?: 0, biggestX2 ?: 0)
    }

    private fun getBiggestY(lines: List<Line>): Int {
        val biggestY1 = lines.maxOfOrNull { it.y1 }
        val biggestY2 = lines.maxOfOrNull { it.y2 }
        return maxOf(biggestY1 ?: 0, biggestY2 ?: 0)
    }

    private fun getFields(lines: List<Line>): List<Field> {
        val tuples = mutableListOf<Field>()
        for (x in 0..getBiggestX(lines)) {
            for (y in 0..getBiggestY(lines)) {
                tuples.add(Field(x, y, 0))
            }
        }
        return tuples
    }

}