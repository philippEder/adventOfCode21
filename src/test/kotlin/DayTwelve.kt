import org.junit.jupiter.api.Test

class DayTwelve {

    val START = "start"
    val END = "end"

    val edges = javaClass.getResource("dayTwelveInput.txt").readText().split("\r\n")
        .map { Pair(it.split("-")[0], it.split("-")[1]) }
    val visitedCaves = mutableListOf<String>()
    val paths = mutableListOf<Path>()

    @Test
    fun partOne() {
        visitedCaves.add(START)
        val startingEdges = edges.filter { it.first == START || it.second == START }
        startingEdges.forEach {
            val nextCave: String
            if (START == it.first) {
                nextCave = it.second
            } else {
                nextCave = it.first
            }

            val path = Path(START)
            path.add(nextCave)
            traverse(nextCave, path)
        }

        println(paths.size)
    }

    private fun traverse(edge: Pair<String, String>, path: Path) {
        if (path.isCompleted()) {
            paths.add(path)
        } else {
            if (isSmallCave(edge.second)) {
                markAsVisited(edge.second)
            }
            val adjacent = getAdjacentEdges(edge)
            adjacent.filter { !isAlreadyVisited(it.second) }.forEach {
                path.add(edge.second)
                val newPath = Path(path.caves)
                traverse(it, newPath)
            }
        }
    }

    private fun traverse(currentCave: String, path: Path) {
        if (path.isCompleted()) {
            paths.add(path)
        } else {
            val adjacent = getAdjacentEdges(currentCave)
            adjacent.forEach {
                val newPath = Path(path.caves)
                val nextCave: String
                if (currentCave == it.first) {
                    nextCave = it.second
                } else {
                    nextCave = it.first
                }

                val shouldVisit = isBigCave(nextCave) || !newPath.hasAlreadyVisited(nextCave)

                if (shouldVisit) {
                    newPath.add(nextCave)
                    traverse(nextCave, newPath)
                }
            }
        }
    }

    private fun isBigCave(caveName: String) = caveName.all { it.isUpperCase() }
    private fun isSmallCave(caveName: String) = caveName.all { it.isLowerCase() } && caveName != END
    private fun getAdjacentEdges(currentEdge: Pair<String, String>) = edges.filter { it.first != START && it.first == currentEdge.second  || it.second == currentEdge.first}
    private fun getAdjacentEdges(currentCave: String) = edges.filter { it.first != START && (it.first == currentCave || it.second == currentCave)}
    private fun markAsVisited(caveName: String) = visitedCaves.add(caveName)
    private fun isAlreadyVisited(caveName: String) = visitedCaves.contains(caveName)

    class Path() {

        val caves = mutableListOf<String>()

        constructor(startingCave: String) : this() {
            caves.add(startingCave)
        }

        constructor(caves: MutableList<String>) : this() {
            this.caves.addAll(caves)
        }

        fun add(cave: String) = caves.add(cave)

        fun hasAlreadyVisited(cave: String) = caves.contains(cave)

        fun isCompleted() = caves.first() == "start" && caves.last() == "end"

    }

}