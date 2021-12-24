import org.junit.jupiter.api.Test
import kotlin.math.abs

class DaySeven {

    @Test
    fun daySeven_parOne() {
        val values = javaClass.getResource("daySevenInput.txt").readText().split(",").map { it.toInt() }.toMutableList()

        val median = values.average()

        var total = 0
        values.forEach{
            if (it > median) {
                var tempIt = it.toDouble()
                while (tempIt != median) {
                    tempIt--
                }
            } else {

            }
        }
        println(total)
    }

    fun sortingSelection(array: MutableList<Int>): MutableList<Int> {

        for (i in 0 until array.size - 1) {
            var min = i
            for (j in i+1 until array.size) {
                if(array[j] < array[min]) {
                    min = j
                }
            }

            var temp = array[i]
            array[i] = array[min]
            array[min] = temp
        }

        return array
    }

    fun median(array: MutableList<Int>): Double {
        sortingSelection(array)

        if (array.size % 2 == 0) {
            return ((array[array.size / 2] + array[array.size / 2 - 1]) / 2).toDouble()
        } else {
            return (array[array.size / 2]).toDouble()
        }
    }
}