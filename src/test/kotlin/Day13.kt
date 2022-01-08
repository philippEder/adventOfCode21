import org.junit.jupiter.api.Test

class Day13 {

    var coordinates = listOf<Coordinate>()

    @Test
    fun partOne() {
        val lines = javaClass.getResource("day13Input.txt").readText().split("\r\n")
        val instructions = getInstructions(lines)
        coordinates = getCoordinates(lines)

        val firstInstruction = instructions[0]

        if (firstInstruction.direction == FoldDirection.UP) {
            foldUp(firstInstruction.line)
        }

        if (firstInstruction.direction == FoldDirection.LEFT) {
            foldLeft(firstInstruction.line)
        }

        println(coordinates.distinct().size)

    }

    @Test
    fun partTwo() {
        val lines = javaClass.getResource("day13Input.txt").readText().split("\r\n")
        val instructions = getInstructions(lines)
        coordinates = getCoordinates(lines)

        instructions.forEach {
            if (it.direction == FoldDirection.UP) {
                foldUp(it.line)
            }

            if (it.direction == FoldDirection.LEFT) {
                foldLeft(it.line)
            }
        }

        printAsGrid(coordinates)
    }

    private fun printAsGrid(coordinates: List<Coordinate>) {
        val maxX = coordinates.maxOf { it.x }
        val maxY = coordinates.maxOf { it.y }

        for (y in 0..maxY) {
            for (x in 0..maxX) {
                if (coordinates.any { it.x == x && it.y == y }) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    private fun foldUp(line: Int) {
        coordinates.filter { it.y != line }
        coordinates.forEach {
            if (it.y > line) {
                it.y = it.y - (it.y - line) * 2
            }
        }
    }

    private fun foldLeft(line: Int) {
        coordinates.filter { it.x != line }
        coordinates.forEach {
            if (it.x > line) {
                it.x = it.x - (it.x - line) * 2
            }
        }
    }


    private fun getCoordinates(lines: List<String>): List<Coordinate> {
        return lines.filter { it.contains(',') }.map { Coordinate(it.split(',')[0].toInt(), it.split(',')[1].toInt()) }
    }

    private fun getInstructions(lines: List<String>): List<Instruction> {
        val instructions = lines.filter { it.contains('=') }.map {
            val foldDirection: FoldDirection = if (it.split(' ')[2].split('=')[0] == "y") {
                FoldDirection.UP
            } else {
                FoldDirection.LEFT
            }
            val line = it.split(' ')[2].split('=')[1].toInt()

            Instruction(line, foldDirection)
        }

        return instructions
    }


    data class Coordinate(var x: Int, var y: Int)
    data class Instruction(val line: Int, val direction: FoldDirection)
    enum class FoldDirection {
        UP, LEFT
    }

}