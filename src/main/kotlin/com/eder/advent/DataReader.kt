package com.eder.advent

class DataReader(val data: String) {

    var index = 0

    fun getNextLine(): String {
        val line = getLines()[index]
        index++
        return line
    }

    fun getPreviousLine() = getLines()[index-1]
    fun getNextLineAsInt(): Int = getNextLine().toInt();
    fun getPreviousLineAsInt() = getPreviousLine().toInt()
    fun hasNext() = getLines().size > index
    fun isInRange(idx : Int) = getLines().size > idx
    fun hasPrevious() = index > 0
    fun getLines(): List<String> = data.split("\r\n")
    fun getLines(from: Int, to:Int): List<String> {
        val lines = mutableListOf<String>()
        var current = from
        while (current <= to) {
           lines.add(getLines()[current])
           current++
        }
        return lines
    }
    fun getLinesAsInt(from: Int, to:Int): List<Int> = getLines(from, to).map { it.toInt() }
    fun getFirstLine(): String = getLines().first()
    fun getLinesAsInt(): List<Int> = getLines().map { it.toInt() }
    fun getFirstLineAsInt(): Int = getLines().first().toInt()
    fun getColumn(columnIndex: Int): String {
        var column = ""
        getLines().forEach {
            column += it[columnIndex]
        }

        return column
    }

    fun getColumns(): List<String> {
        val columns = mutableListOf<String>()
        val lastColumnIndex = getFirstLine().length
        for (columnIndex in 0 until lastColumnIndex) {
            columns.add(getColumn(columnIndex))
        }
        return columns
    }

}

