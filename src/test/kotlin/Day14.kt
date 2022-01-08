import org.junit.jupiter.api.Test

class Day14 {


    var pairCountMap = mutableMapOf<Pair<Char, Char>, Long>()
    var numberCountMap = mutableMapOf<Char, Long>()

    @Test
    fun partOneAndTwo() {
        val lines = javaClass.getResource("day14Input.txt").readText().split("\r\n")
        val template = lines[0]
        val instructions = getInstructions(lines)
        initCountMaps(template)

        for (step in 0..39) {
            val newPairCountMap = mutableMapOf<Pair<Char, Char>, Long>()
            instructions.forEach { instruction ->
                val oldPairCount = pairCountMap[instruction.from]
                if (oldPairCount != null) {
                    putOrIncreaseNumberCount(instruction.to, oldPairCount)

                    val firstNewPair = Pair(instruction.from.first, instruction.to)
                    val secondNewPair = Pair(instruction.to, instruction.from.second)

                    putOrIncrease(newPairCountMap, firstNewPair, oldPairCount)
                    putOrIncrease(newPairCountMap, secondNewPair, oldPairCount)
                }
            }
            pairCountMap = newPairCountMap
        }

        val maxElement = numberCountMap.maxOf { it.value }
        val minElement = numberCountMap.minOf { it.value }

        println(maxElement - minElement)
    }

    private fun initCountMaps(template: String) {
        putOrIncreaseNumberCount(template[0], 1)
        template.indices.forEach {
            if (it > 0) {
                val previous = template[it - 1]
                val current = template[it]
                putOrIncrease(pairCountMap, Pair(previous, current), 1)
                putOrIncreaseNumberCount(current, 1)
            }
        }
    }

    private fun putOrIncrease(map: MutableMap<Pair<Char, Char>, Long>, key: Pair<Char, Char>, increaseAmount: Long) {
        val pairCount = map[key]
        if (pairCount == null) {
            map[key] = increaseAmount
        } else {
            map[key] = pairCount + increaseAmount
        }
    }

    private fun putOrIncreaseNumberCount(number: Char, increaseAmount: Long) {
        val numberCount = numberCountMap[number]
        if (numberCount == null) {
            numberCountMap[number] = increaseAmount
        } else {
            numberCountMap[number] = numberCount + increaseAmount
        }
    }

    private fun getInstructions(lines: List<String>): List<Instruction> {
        return lines.filter { it.contains(" -> ") }.map {
            val splitLine = it.split(" -> ")
            val from = Pair(splitLine[0][0], splitLine[0][1])
            val to = splitLine[1][0]
            Instruction(from, to)
        }
    }

    data class Instruction(val from: Pair<Char, Char>, val to: Char)

}