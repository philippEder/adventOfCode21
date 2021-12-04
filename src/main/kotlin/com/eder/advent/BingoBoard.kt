package com.eder.advent

class BingoBoard(values: List<List<Int>>) {

    val board: List<List<BingoField>>
    var lastMarkedNumber: Int = -1

    init {
        board = mutableListOf()
        for (rowIndex in values.indices) {
            board.add(values[rowIndex].map { BingoField(it, false) })
        }
    }

    fun markFieldWithNumber(number: Int) {
        if (!isCompleted()) {
            interateFields {
                if (it.value == number) {
                    it.marked = true
                }
            }

            if (isCompleted()) {
                lastMarkedNumber = number
            }
        }
    }

    fun isCompleted(): Boolean {
        board.forEach { row ->
            if (row.all { it.marked })
                return true
        }

        getColums().forEach { col ->
            if (col.all { it.marked })
                return true
        }

        return false
    }

    fun getUnmarkedFields(): List<BingoField> {
        val unmarkedFields = mutableListOf<BingoField>()
        interateFields {
            if (!it.marked)
                unmarkedFields.add(it)
        }
        return unmarkedFields
    }

    private fun interateFields(doStuff: (BingoField) -> Unit) {
        for (row in board) {
            for (field in row) {
                doStuff(field)
            }
        }
    }

    private fun getColums() : List<List<BingoField>> {
        val columns = mutableListOf<List<BingoField>>()
        val columnCount = board[0].size
        for (colIdx in 0 until columnCount)
            columns.add(getColumn(colIdx))

        return columns
    }

    private fun getColumn(colIdx: Int): List<BingoField> {
       val column = mutableListOf<BingoField>()
        board.forEach {
            column.add(it[colIdx])
        }
        return column
    }


}