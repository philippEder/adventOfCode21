import com.eder.advent.four.BingoBoard
import com.eder.advent.four.BingoBoardList
import com.eder.advent.DataReader
import org.junit.jupiter.api.Test

class DayFour {

    @Test
    fun dayFour_partOne() {
        val reader = DataReader(javaClass.getResource("dayFourInput.txt").readText())

        val drawnNumbers = reader.getNextLine().split(",").map { it.toInt() }
        val boards = setUpBoards(reader)

        drawnNumbers.forEach { drawnNumber ->
            boards.markAllWithNumber(drawnNumber)
            if (boards.hasCompletedBoard()) {
                val winningBoard = boards.getFirstCompletedBoard()
                val unmarkedFields = winningBoard.getUnmarkedFields()
                val sumUnmarked = unmarkedFields.map { it.value }.sum()

                println(sumUnmarked * drawnNumber)
            }
        }

    }

    @Test
    fun dayFour_partTwo() {
        val reader = DataReader(javaClass.getResource("dayFourInput.txt").readText())

        val drawnNumbers = reader.getNextLine().split(",").map { it.toInt() }
        val boards = setUpBoards(reader)

        var lastCompleted: BingoBoard = boards.get(0)
        drawnNumbers.forEach { drawnNumber ->
            val completed = boards.markAllWithNumber(drawnNumber)
            if (completed.isNotEmpty())
                lastCompleted = completed.last()
        }


        val unmarkedFields = lastCompleted.getUnmarkedFields()
        val sumUnmarked = unmarkedFields.map { it.value }.sum()

        println(sumUnmarked * lastCompleted.lastMarkedNumber)
    }



    fun setUpBoards(reader: DataReader): BingoBoardList {
        val boards = mutableListOf<BingoBoard>()
        var board: MutableList<List<Int>> = mutableListOf()

        reader.getRemainingLines().forEach {
            if (it.isBlank()) {
                if (board.isNotEmpty())
                    boards.add(BingoBoard(board))
                board = mutableListOf()
            } else {
                val rowValues: List<Int> = it.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
                board.add(rowValues)
            }
        }

        boards.add(BingoBoard(board))
        return BingoBoardList(boards)
    }

}