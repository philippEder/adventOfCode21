package com.eder.advent.five

data class Line(
    val x1 : Int,
    val y1 : Int,
    val x2 : Int,
    val y2 : Int
) {
    fun usesField(field: Field): Boolean {
        if (isVertical() && field.x == x1)
            return field.y in y1..y2 || field.y in y2..y1

        if (isHorizontal() && field.y == y1)
            return field.x in x1..x2 || field.x in x2..x1

        if (isDiagonal())  {
            if (x1 == field.x && y1 == field.y || x2 == field.x && y2 == field.y)
                return true

            var tempX = x1
            var tempY = y1
            while (tempX != x2 || tempY != y2) {
                if (tempX == field.x && tempY == field.y)
                    return true

                if (isAscending())
                    tempY--
                else
                    tempY++

                if (isLeftToRight())
                    tempX++
                else
                    tempX--
            }
        }


        return false
    }

    fun isVertical(): Boolean {
        return x1 == x2
    }

    fun isHorizontal(): Boolean {
        return y1 == y2
    }

    fun isDiagonal(): Boolean {
        return !isVertical() && !isHorizontal()
    }

    fun isAscending() = y2 < y1
    fun isLeftToRight() = x1 < x2

}