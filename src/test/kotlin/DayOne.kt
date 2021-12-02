import com.eder.advent.DataReader
import org.junit.jupiter.api.Test

class DayOne {

    @Test
    fun dayOne_partOne() {

        val dataReader = DataReader(javaClass.getResource("dayOneInput.txt").readText())
        var increaedCount = 0

        while (dataReader.hasNext()) {
            var previous = Int.MAX_VALUE
            if (dataReader.hasPrevious())
                previous = dataReader.getPreviousLineAsInt()


            val current = dataReader.getNextLineAsInt()

            if (current > previous)
                increaedCount++
        }

        println(increaedCount)
    }

    @Test
    fun dayOne_partTwo() {
        val dataReader = DataReader(javaClass.getResource("dayOneInput.txt").readText())

        var increasedCount = 0

        var startIndex = 0
        var endIndex = 2

        while (dataReader.isInRange(endIndex)) {
            val lines = dataReader.getLinesAsInt(startIndex, endIndex)
            startIndex+=1
            endIndex+=1
            if (dataReader.isInRange(endIndex)) {
                val nexLines = dataReader.getLinesAsInt(startIndex, endIndex)
                if (nexLines.sum() > lines.sum())
                    increasedCount++
            }
        }

        println(increasedCount)
    }

}