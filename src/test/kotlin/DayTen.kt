import org.junit.jupiter.api.Test

class DayTen {

    var openings = listOf('(', '[', '{', '<')
    var closings = listOf(')', ']', '}', '>')

    val pointsTable = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    @Test
    fun partOne() {
        val lines = javaClass.getResource("dayTenInput.txt").readText().split("\r\n")


        val invalidCharacters = mutableListOf<Char>()


        lines.forEach { line ->

            val errorIndizes = getErrorIndizes(line)

            if (errorIndizes.isNotEmpty()) {
                val firstErrorIndex = getErrorIndizes(line).first()
                invalidCharacters.add(line[firstErrorIndex])
            }
        }

        var sum = 0
        invalidCharacters.forEach { sum += pointsTable[it]!! }

        println(sum)
    }

    @Test
    fun partTwo() {
        val lines = javaClass.getResource("dayTenExample.txt").readText().split("\r\n")

        val missingClosings = mutableListOf<String>()

        lines.forEach { line ->
            if (getErrorIndizes(line).isEmpty()) {
                swapOpeningAndClosings()
                val errorIndizes = getErrorIndizes(line.reversed())
                swapOpeningAndClosings()
            }
        }


    }

    fun swapOpeningAndClosings() {
        val closingsClone = closings.toList()
        closings = openings
        openings = closingsClone
    }


    fun getErrorIndizes(line: String): List<Int> {

        var lastOpeningKlammerIndex = 0
        val closedIndizes = mutableListOf<Int>()
        val errorIndizes = mutableListOf<Int>()
        val missingClosings = mutableListOf<Int>()

        if (isClosing(line[0])) {
            errorIndizes.add(0)
        } else {
            for (index in 1 until line.length) {
                val current = line[index]
                if (isOpening(current)) {
                    lastOpeningKlammerIndex = index
                } else {
                    if (lastOpeningKlammerIndex == -1) {
                        missingClosings.add(index)
                    } else {
                        if (!closes(line[lastOpeningKlammerIndex], current)) {
                            errorIndizes.add(index)
                        } else {
                            closedIndizes.add(lastOpeningKlammerIndex)
                            var newLastOpeningIndex = lastOpeningKlammerIndex - 1
                            while (newLastOpeningIndex != -1 && isClosing(line[newLastOpeningIndex]) || closedIndizes.contains(newLastOpeningIndex)) {
                                newLastOpeningIndex--
                            }
                            lastOpeningKlammerIndex = newLastOpeningIndex
                        }
                    }

                }
            }
        }

        return errorIndizes
    }

    fun isClosing(klammer: Char) = closings.contains(klammer)
    fun isOpening(klammer: Char) = openings.contains(klammer)

    fun closes(openingKlammer: Char, closingKlammer: Char): Boolean {
        when (openingKlammer) {
            openings[0] -> return closingKlammer == closings[0]
            openings[1] -> return closingKlammer == closings[1]
            openings[2] -> return closingKlammer == closings[2]
            openings[3] -> return closingKlammer == closings[3]
        }

        return false
    }

}