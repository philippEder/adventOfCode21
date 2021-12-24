import org.junit.jupiter.api.Test

class DayEight {

    val segtmentsToDigits = mapOf(
        "abcefg" to 0,
        "cf" to 1,
        "acdeg" to 2,
        "acdfg" to 3,
        "bcdf" to 4,
        "abdfg" to 5,
        "abdefg" to 6,
        "acf" to 7,
        "abcdefg" to 8,
        "abcdfg" to 9
    )

    var translatedChars = mapOf<Char, Char>()


    @Test
    fun dayEight_partTwo() {
        val lines = javaClass.getResource("dayEightInput.txt").readText().split("\r\n")
                             .map { Pair(it.split("|")[0].split(" ").filter { it.isNotBlank() },
                                         it.split("|")[1].split(" ").filter { it.isNotBlank() }) }

        var allTheSum = 0


        lines.forEach { pair ->

            val left = pair.first.toMutableList()
            val right = pair.second

            val one = left.first { it.length == 2 }
            val four = left.first { it.length == 4 }
            val seven = left.first { it.length == 3 }
            val eight = left.first { it.length == 7 }

            left.remove(one)
            left.remove(four)
            left.remove(seven)
            left.remove(eight)

            val nine = filterForSegments(left, listOf(seven, four)).first()

            left.remove(nine)

            val a = substract(seven, one).first().toString()
            val g = substract(substract(nine, four), a).first().toString()
            val e = substract(substract(substract(eight, four), a), g).first().toString()

            val almostZero = plus(plus(seven, g), e)
            val zero = filterForSegments(left, listOf(almostZero)).first().toString()

            left.remove(zero)

            val d = substract(eight, zero).first().toString()
            val six = left.filter { it.length == 6 }.first().toString()

            left.remove(six)

            val c = substract(eight, six)
            val two = filterForSegments(left, listOf(a+c+d+e+g)).first().toString()

            left.remove(two)

            val b = substract(substract(six, two), one).first().toString()
            val f = substract(eight, a+b+c+d+e+g).first().toString()

            translatedChars = mapOf(
                 a.first() to 'a',
                 b.first() to 'b',
                 c.first() to 'c',
                 d.first() to 'd',
                 e.first() to 'e',
                 f.first() to 'f',
                 g.first() to 'g'
            )

            var output = ""

            right.forEach{
                output += translate(it, translatedChars)
            }

            allTheSum += output.toInt()
        }

        println(allTheSum)

    }

    fun filterForSegments(line: List<String>,segments: List<String>) : List<String> {
        val distinctCharacters = segments.flatMap { it.split("") }.toSet()

        return line.toMutableList().filter { segment ->
            segment.split("").containsAll(distinctCharacters)
        }
    }

    fun substract(segment: String, otherSegment: String) : String {
        val intersection = segment.split("").intersect(otherSegment.split("")).toMutableSet()

        val result = segment.split("").toMutableSet()
        result.removeAll(intersection)

        return result.joinToString(separator = "")
    }

    fun plus(segment: String, otherSegment: String): String {
        return listOf(segment, otherSegment).flatMap { it.split("") }.joinToString(separator = "")
    }

    fun translate(segment: String, translationMap: Map<Char, Char>) : Int {
        var correctSegment = segment.map { translationMap[it] }.sortedBy { it }.joinToString("")
        return segtmentsToDigits[correctSegment]!!
    }
}