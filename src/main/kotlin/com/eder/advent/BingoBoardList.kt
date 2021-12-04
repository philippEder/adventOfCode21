package com.eder.advent

class BingoBoardList(val boards: List<BingoBoard>) {

    fun markAllWithNumber(number: Int): List<BingoBoard> {
        val newlyCompleted = mutableListOf<BingoBoard>()
        boards.forEach {
            val wasCompleted = it.isCompleted()
            it.markFieldWithNumber(number)
            val isCompleted = it.isCompleted()

            if (!wasCompleted && isCompleted) {
                newlyCompleted.add(it)
            }
        }
        return newlyCompleted
    }

    fun getFirstCompletedBoard(): BingoBoard = boards.first { it.isCompleted() }

    fun hasCompletedBoard() = boards.filter { it.isCompleted() }.count() > 0

    fun get(index: Int) = boards[index]

}