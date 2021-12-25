import org.junit.jupiter.api.Test

class DayTwelve {

    val START = "start"

    val paths = mutableListOf<Path>()
    val edges = javaClass.getResource("dayTwelveInput.txt").readText().split("\r\n")
        .map { Pair(it.split("-")[0], it.split("-")[1]) }


    @Test
    fun partOne() {
        val startingEdges = edges.filter { it.first == START || it.second == START }
        startingEdges.forEach {
            val nextCave = if (START == it.first) {
                it.second
            } else {
                it.first
            }

            val path = Path(START)
            path.add(nextCave)
            traverse(nextCave, path)
        }

        println(paths.size)
    }

    private fun traverse(currentCave: String, path: Path) {
        if (path.isCompleted()) {
            paths.add(path)
        } else {
            val adjacent = getAdjacentEdges(currentCave)
            adjacent.forEach {
                val newPath = Path(path.caves, path.hasVisitedSmallCaveTwice)
                val nextCave = if (currentCave == it.first) {
                    it.second
                } else {
                    it.first
                }

                val shouldVisit = nextCave != "start" &&  (isBigCave(nextCave) || !newPath.hasAlreadyVisited(nextCave) || !newPath.hasVisitedSmallCaveTwice)

                if (shouldVisit) {
                    newPath.add(nextCave)
                    traverse(nextCave, newPath)
                }
            }
        }
    }

    private fun isBigCave(caveName: String) = caveName.all { it.isUpperCase() }
    private fun getAdjacentEdges(currentCave: String) = edges.filter { it.first != START && (it.first == currentCave || it.second == currentCave)}

    class Path() {

        val caves = mutableListOf<String>()
        var hasVisitedSmallCaveTwice = false

        constructor(startingCave: String) : this() {
            caves.add(startingCave)
        }

        constructor(caves: MutableList<String>, hasVisitedSmallCaveTwice: Boolean) : this() {
            this.caves.addAll(caves)
            this.hasVisitedSmallCaveTwice = hasVisitedSmallCaveTwice
        }

        fun add(cave: String) {
            val isSmallCave = cave != "start" && cave != "end" && cave.all { it.isLowerCase() }

            if (isSmallCave && caves.contains(cave)) {
                hasVisitedSmallCaveTwice = true
            }

            caves.add(cave)
        }

        fun hasAlreadyVisited(cave: String) = caves.contains(cave)

        fun isCompleted() = caves.first() == "start" && caves.last() == "end"

    }

}