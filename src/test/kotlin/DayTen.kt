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

    val pointsTable_partTwo = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )

    //75193672
    //)]}]]])>>)>]

    @Test
    fun partOne() {
        val lines = javaClass.getResource("dayTenInput.txt").readText().split("\r\n")
        val invalidCharacters = mutableListOf<Char>()

        lines.forEach { line ->
            val errorIndizes = getErrorIndizes(line, mutableListOf())
            if (errorIndizes.isNotEmpty()) {
                val firstErrorIndex = errorIndizes.first()
                invalidCharacters.add(line[firstErrorIndex])
            }
        }

        var sum = 0
        invalidCharacters.forEach { sum += pointsTable[it]!! }

        println(sum)
    }

    @Test
    fun partTwo() {
        val lines = javaClass.getResource("dayTenInput.txt").readText().split("\r\n")

        val missingClosings = mutableListOf<String>()

        lines.forEach { line ->
            if (getErrorIndizes(line, mutableListOf()).isEmpty()) {
                swapOpeningAndClosings()
                val missingClosingsList = mutableListOf<Char>()
                getErrorIndizes(line.reversed(), missingClosingsList)
                missingClosings.add(missingClosingsList.joinToString(""))
                swapOpeningAndClosings()
            }
        }

        val scores = missingClosings.map { closing ->
                var score = 0L
                closing.forEach {
                    score *= 5
                    score += pointsTable_partTwo[it]!!
                }
                score
        }

        println(scores.sorted()[scores.size/2])
    }







    fun swapOpeningAndClosings() {
        val closingsClone = closings.toList()
        closings = openings
        openings = closingsClone
    }


    fun getErrorIndizes(line: String, missingClosings: MutableList<Char>): List<Int> {

        var lastOpeningKlammerIndex = 0
        val closedIndizes = mutableListOf<Int>()
        val errorIndizes = mutableListOf<Int>()

        if (isClosing(line[0])) {
            missingClosings.add(getOposite(line[0]))
        }

        for (index in 1 until line.length) {
            val current = line[index]
            if (isOpening(current)) {
                lastOpeningKlammerIndex = index
            } else {
                if (lastOpeningKlammerIndex == -1) {
                    missingClosings.add(getOposite(line[index]))
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


        return errorIndizes
    }

    fun isClosing(klammer: Char) = closings.contains(klammer)
    fun isOpening(klammer: Char) = openings.contains(klammer)

    fun getOposite(klammer: Char): Char {
        when (klammer) {
            openings[0] -> return closings[0]
            openings[1] -> return closings[1]
            openings[2] -> return closings[2]
            openings[3] -> return closings[3]
            closings[0] -> return openings[0]
            closings[1] -> return openings[1]
            closings[2] -> return openings[2]
            closings[3] -> return openings[3]
        }

        return '('
    }

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