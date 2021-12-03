import com.eder.advent.DataReader
import org.junit.jupiter.api.Test

class DayThree {

    @Test
    fun dayThree_partOne() {
        val reader = DataReader(javaClass.getResource("dayThreeInput.txt").readText())

        var gammaRate = ""

        reader.getColumns().forEach {
            gammaRate += getMostCommonChar(it)
        }

        val gammaRateDeciaml = gammaRate.toInt(2)
        val epsilonRateDecimal = getInvertedBits(gammaRate).toInt(2)

        println(gammaRateDeciaml * epsilonRateDecimal)
    }

    @Test
    fun dayThree_partTwo() {
        val reader = DataReader(javaClass.getResource("dayThreeInput.txt").readText())

        var oxygen: Int
        var co2: Int

        var rows = reader.getLines()
        var columnIdx = 0

        while (rows.size > 1) {
            var mostCommonChar = getMostCommonChar(getColumnOfRemainingRows(rows, columnIdx))
            rows = rows.filter { it[columnIdx] == mostCommonChar }
            columnIdx++
        }

        oxygen = rows.first().toInt(2)

        columnIdx = 0
        rows = reader.getLines()

        while (rows.size > 1) {
            val leastCommonChar = getLeastCommonChar(getColumnOfRemainingRows(rows, columnIdx))
            rows = rows.filter { it[columnIdx] == leastCommonChar }
            columnIdx++
        }

        co2 = rows.first().toInt(2)

        println(oxygen * co2)
    }


    fun getColumnOfRemainingRows(remaining: List<String>, columnIdx: Int): String {
        var column = ""
        remaining.forEach {
            column += it[columnIdx]
        }
        return column
    }


    fun getMostCommonChar(value: String) : Char {
        val charList = value.toList()
        val counts: Map<Char, Int> = charList.groupingBy { it }.eachCount()
        var mostCommonChar = counts.maxByOrNull { it.value }?.key

        if (counts.values.all { counts.values.first() == it })
            mostCommonChar = '1'

        return mostCommonChar ?: '1'
    }

    fun getLeastCommonChar(value: String) : Char {
        val charList = value.toList()
        val counts: Map<Char, Int> = charList.groupingBy { it }.eachCount()
        var leastCommonChar = counts.minByOrNull { it.value }?.key

        if (counts.values.all { counts.values.first() == it })
            leastCommonChar = '0'

        return leastCommonChar ?: '0'
    }

    fun getInvertedBits(value: String): String {
        var invertedBitString = ""
        value.forEach {
            when (it) {
                '0' -> invertedBitString+="1"
                '1' -> invertedBitString+="0"
            }
        }
        return invertedBitString
    }

}