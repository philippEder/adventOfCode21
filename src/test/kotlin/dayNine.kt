import org.junit.jupiter.api.Test

class dayNine {

    @Test
    fun partOne() {
        val heatMap = getHeatMap()
        val length = heatMap.size
        val width = heatMap[0].size
        val lowPoints = mutableListOf<Int>()

        for (xIndex in 0..length) {
            for (yIndex in 0..width) {
                if (isLowPoint(heatMap, xIndex, yIndex)) {
                    lowPoints.add(heatMap[xIndex][yIndex])
                }
            }
        }

        var sum = 0
        lowPoints.forEach {
           sum += (1+it)
        }

        println(sum)
    }

    @Test
    fun partTow() {
        val heatMap = getHeatMap()
        val length = heatMap.size
        val width = heatMap[0].size
        val basinSizes = mutableListOf<Int>()

        for (xIndex in 0..length) {
            for (yIndex in 0..width) {
                if (isLowPoint(heatMap, xIndex, yIndex)) {
                    basinSizes.add(getBasinSize(heatMap, xIndex, yIndex, mutableListOf(), Counter()))
                }
            }
        }

        basinSizes.sortDescending()

        val result = basinSizes[0] * basinSizes[1] * basinSizes[2]

        println(result)
    }


    private fun getHeatMap() : List<List<Int>> {
        val lines = javaClass.getResource("dayNineInput.txt").readText().split("\r\n")
        val outerList = mutableListOf<List<Int>>()

        lines.forEach { line ->
            val innerList = line.map { Character.getNumericValue(it) }
            outerList.add(innerList)
        }

        return outerList
    }

    private fun isLowPoint(heatMap: List<List<Int>>, xIndex: Int, yIndex: Int) : Boolean {

        val current = getOrElseMax(heatMap, xIndex, yIndex)
        val up = getOrElseMax(heatMap, xIndex-1, yIndex)
        val down = getOrElseMax(heatMap, xIndex+1, yIndex)
        val left = getOrElseMax(heatMap, xIndex, yIndex-1)
        val right = getOrElseMax(heatMap, xIndex, yIndex+1)

        return current < up && current < down && current < left && current < right
    }

    private fun getOrElseMax(heatMap: List<List<Int>>, xIndex: Int, yIndex: Int) = getOrElse(heatMap, xIndex, yIndex, Int.MAX_VALUE)

    private fun getOrElseMinusOne(heatMap: List<List<Int>>, xIndex: Int, yIndex: Int): Int  = getOrElse(heatMap, xIndex, yIndex, -1)


    private fun getOrElse(heatMap: List<List<Int>>, xIndex: Int, yIndex: Int, orElse: Int): Int {
        val x = heatMap.getOrNull(xIndex)

        if (x != null) {
            return x.getOrNull(yIndex) ?: orElse
        }

        return orElse
    }

    private fun getBasinSize(heatMap: List<List<Int>>,
                             xIndex: Int,
                             yIndex: Int,
                             alreadyVisited: MutableList<Pair<Int, Int>>,
                             basinSize: Counter): Int {

        if (!alreadyVisited.contains(Pair(xIndex, yIndex))) {
            basinSize.increase()
        }

        alreadyVisited.add(Pair(xIndex, yIndex))

        val current = getOrElseMinusOne(heatMap, xIndex, yIndex)
        val up = getOrElseMinusOne(heatMap, xIndex-1, yIndex)
        val down = getOrElseMinusOne(heatMap, xIndex+1, yIndex)
        val left = getOrElseMinusOne(heatMap, xIndex, yIndex-1)
        val right = getOrElseMinusOne(heatMap, xIndex, yIndex+1)


        if (up > current && up != 9) {
            getBasinSize(heatMap, xIndex-1, yIndex, alreadyVisited, basinSize)
        }

        if (down > current && down != 9) {
            getBasinSize(heatMap, xIndex+1, yIndex, alreadyVisited, basinSize)
        }

        if (left > current && left != 9) {
            getBasinSize(heatMap, xIndex, yIndex-1, alreadyVisited, basinSize)
        }

        if (right > current && right != 9) {
            getBasinSize(heatMap, xIndex, yIndex+1, alreadyVisited, basinSize)
        }

        return basinSize.counter
    }


    class Counter {
        var counter = 0

        fun increase() = counter++
    }

}