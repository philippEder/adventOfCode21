import org.junit.jupiter.api.Test

class DayEleven {


    @Test
    fun partOne() {
        val octopusField = getOctopusField()

        var flashes = 0

        for (step in 1..100) {
            increaseAll(octopusField)
            flashes += letEmFlash(octopusField)
            resetFlashed(octopusField)
        }

        println(flashes)
    }

    @Test
    fun partTwo() {
        val octopusField = getOctopusField()

        for (step in 1..Int.MAX_VALUE) {
            increaseAll(octopusField)
            val flashes = letEmFlash(octopusField)
            if (flashes == 100) {
                println(step)
                break
            }
            resetFlashed(octopusField)
        }

    }


    private fun increaseAll(octopusField: List<MutableList<Int>>) {
        for (x in octopusField.indices) {
            for (y in octopusField[x].indices) {
                octopusField[x][y] = octopusField[x][y] + 1
            }
        }
    }

    private fun resetFlashed(octopusField: List<MutableList<Int>>) {
        for (x in octopusField.indices) {
            for (y in octopusField[x].indices) {
                if (octopusField[x][y] > 9) {
                    octopusField[x][y] = 0
                }
            }
        }
    }

    private fun letEmFlash(octopusField: List<MutableList<Int>>): Int {
        val flashes = mutableListOf<Pair<Int,Int>>()
        for (x in octopusField.indices) {
            for (y in octopusField[x].indices) {
                if (octopusField[x][y] > 9) {
                    flash(octopusField, x, y, flashes)
                }
            }
        }
        return flashes.size
    }

    private fun flash(octopusField: List<MutableList<Int>>, xIndex: Int, yIndex: Int, alreadyFlashed: MutableList<Pair<Int,Int>>) {

        if (!alreadyFlashed.contains(Pair(xIndex, yIndex))) {
            alreadyFlashed.add(Pair(xIndex, yIndex))

            val downFlashes = evaluateFlashCandidate(octopusField, xIndex+1, yIndex)
            if (downFlashes) {
                flash(octopusField, xIndex+1, yIndex, alreadyFlashed)
            }

            val upFlashes = evaluateFlashCandidate(octopusField, xIndex-1, yIndex)
            if (upFlashes) {
                flash(octopusField, xIndex-1, yIndex, alreadyFlashed)
            }

            val leftFlashes = evaluateFlashCandidate(octopusField, xIndex, yIndex-1)
            if (leftFlashes) {
                flash(octopusField, xIndex, yIndex-1, alreadyFlashed)
            }

            val rightFlashes = evaluateFlashCandidate(octopusField, xIndex, yIndex+1)
            if (rightFlashes) {
                flash(octopusField, xIndex, yIndex+1, alreadyFlashed)
            }

            val upLeftFlashes = evaluateFlashCandidate(octopusField, xIndex-1, yIndex-1)
            if (upLeftFlashes) {
                flash(octopusField, xIndex-1, yIndex-1, alreadyFlashed)
            }

            val upRightFlashes = evaluateFlashCandidate(octopusField, xIndex-1, yIndex+1)
            if (upRightFlashes) {
                flash(octopusField, xIndex-1, yIndex+1, alreadyFlashed)
            }

            val downLeftFlashes = evaluateFlashCandidate(octopusField, xIndex+1, yIndex-1)
            if (downLeftFlashes) {
                flash(octopusField, xIndex+1, yIndex-1, alreadyFlashed)
            }

            val downRightFlashes = evaluateFlashCandidate(octopusField, xIndex+1, yIndex+1)
            if (downRightFlashes) {
                flash(octopusField, xIndex+1, yIndex+1, alreadyFlashed)
            }
        }

    }

    private fun evaluateFlashCandidate(octopusField: List<MutableList<Int>>, xIndex: Int, yIndex: Int) : Boolean {
        var candidate = getOrElseMinusOne(octopusField, xIndex, yIndex)
        if (candidate != -1) {
            candidate += 1
            octopusField[xIndex][yIndex] = candidate
            return candidate > 9
        }

        return false
    }

    private fun getOctopusField() : List<MutableList<Int>> {
        val lines = javaClass.getResource("dayElevenInput.txt").readText().split("\r\n")
        val outerList = mutableListOf<MutableList<Int>>()

        lines.forEach { line ->
            val innerList = line.map { Character.getNumericValue(it) }.toMutableList()
            outerList.add(innerList)
        }

        return outerList
    }


    private fun getOrElseMinusOne(heatMap: List<List<Int>>, xIndex: Int, yIndex: Int): Int  = getOrElse(heatMap, xIndex, yIndex, -1)


    private fun getOrElse(heatMap: List<List<Int>>, xIndex: Int, yIndex: Int, orElse: Int): Int {
        val x = heatMap.getOrNull(xIndex)

        if (x != null) {
            return x.getOrNull(yIndex) ?: orElse
        }

        return orElse
    }


}