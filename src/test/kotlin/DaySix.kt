import org.junit.jupiter.api.Test
import java.math.BigInteger

class DaySix {

    @Test
    fun daySix_partOne() {
        val fischal : MutableList<Fisch> = javaClass.getResource("daySixInput.txt").readText().split(",").map { Fisch(it.toInt()) }.toMutableList()


        repeat(80) {
            val tempFischal = fischal.toList()
            tempFischal.forEach {
                if (it.timer == 0) {
                    it.timer = 6
                    fischal.add(Fisch(8))
                } else {
                    it.timer--
                }

            }
        }

        println(fischal.size)
    }

    @Test
    fun daySix_partTwo() {
        val fischal : MutableList<Fisch> = javaClass.getResource("daySixInput.txt").readText().split(",").map { Fisch(it.toInt()) }.toMutableList()
        val fischal2:Map<Int, List<Fisch>> = fischal.groupBy { it.timer }
        val synchronizedFischal :MutableMap<Int, BigInteger> = mutableMapOf()

        fischal2.forEach { (key,value) ->
            synchronizedFischal[key] = value.size.toBigInteger()
        }

        synchronizedFischal[0] = BigInteger.ZERO
        synchronizedFischal[6] = BigInteger.ZERO
        synchronizedFischal[7] = BigInteger.ZERO
        synchronizedFischal[8] = BigInteger.ZERO

        repeat(257) {
            val newFischal = nullSafeGet(synchronizedFischal,0)
            synchronizedFischal[0] = nullSafeGet(synchronizedFischal,1)
            synchronizedFischal[1] = nullSafeGet(synchronizedFischal,2)
            synchronizedFischal[2] = nullSafeGet(synchronizedFischal,3)
            synchronizedFischal[3] = nullSafeGet(synchronizedFischal,4)
            synchronizedFischal[4] = nullSafeGet(synchronizedFischal,5)
            synchronizedFischal[5] = nullSafeGet(synchronizedFischal,6)
            var six = nullSafeGet(synchronizedFischal,7)
            six = six.plus(newFischal)
            synchronizedFischal[6] = BigInteger.valueOf(six.toLong())
            synchronizedFischal[7] = nullSafeGet(synchronizedFischal, 8)
            synchronizedFischal[8] = BigInteger.valueOf(newFischal.toLong())
        }

        printTotalCount(synchronizedFischal)
    }

    fun printTotalCount(fischal: MutableMap<Int, BigInteger>) {
        val finalCount = nullSafeGet(fischal, 0)
            .plus(nullSafeGet(fischal, 1))
            .plus(nullSafeGet(fischal, 2))
            .plus(nullSafeGet(fischal, 3))
            .plus(nullSafeGet(fischal, 4))
            .plus(nullSafeGet(fischal, 5))
            .plus(nullSafeGet(fischal, 6))
            .plus(nullSafeGet(fischal, 7))

        println(finalCount)
    }

    fun nullSafeGet(map : MutableMap<Int, BigInteger>, key: Int ) = map[key] ?: BigInteger.ZERO


    data class Fisch(var timer : Int)
}