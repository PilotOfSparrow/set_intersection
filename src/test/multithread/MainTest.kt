package multithread

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.ThreadLocalRandom
import kotlin.system.measureTimeMillis

class MainTest {
    @Test
    fun isIntersectTest1() {
        val arr1 = emptyList<Int>()
        val arr2 = emptyList<Int>()
        val arr3 = emptyList<Int>()

        assertEquals(false, isIntersect(arr1, arr2, arr3))
    }

    @Test
    fun isIntersectTest2() {
        val arr1 = emptyList<Int>()
        val arr2 = emptyList<Int>()
        val arr3 = listOf(2, 3, 4)

        assertEquals(false, isIntersect(arr1, arr2, arr3))
    }

    @Test
    fun isIntersectTest3() {
        val arr1 = emptyList<Int>()
        val arr2 = listOf(2, 3, 4)
        val arr3 = emptyList<Int>()

        assertEquals(false, isIntersect(arr1, arr2, arr3))
    }

    @Test
    fun isIntersectTest4() {
        val arr1 = listOf(2, 3, 4)
        val arr2 = emptyList<Int>()
        val arr3 = emptyList<Int>()

        assertEquals(false, isIntersect(arr1, arr2, arr3))
    }

    @Test
    fun isIntersectTest5() {
        val arr1 = listOf(1, 2, 3, 4, 5)
        val arr2 = listOf(3, 4, 5)
        val arr3 = listOf(7, 8, 9, 1, 3, 2)

        assertEquals(true, isIntersect(arr1, arr2, arr3))
    }

    @Test
    fun isIntersectTest6() {
        val arr1 = listOf(1, 2, 3, 4, 5)
        val arr2 = listOf(3, 4, 5)
        val arr3 = listOf("one", "two", "three")

        assertEquals(false, isIntersect(arr1, arr2, arr3))
    }

    @Test
    fun isIntersectTest7() {
        val arr1 = listOf(1, 2, 3, 4, 5)
        val arr2 = listOf(3, 4, 5)
        val arr3 = listOf(1, 2, 8, 9, 12, 13)

        assertEquals(false, isIntersect(arr1, arr2, arr3))
    }

    @Test
    fun isIntersectTest8() {
        val times: MutableList<Long> = mutableListOf()

        for (i in 1..100) {
            val arr1 = IntArray(100000) { ThreadLocalRandom.current().nextInt(-1000000, 1000000) }.asList()
            val arr2 = IntArray(100000) { ThreadLocalRandom.current().nextInt(-1000000, 1000000) }.asList()
            val arr3 = IntArray(100000) { ThreadLocalRandom.current().nextInt(-1000000, 1000000) }.asList()

            var isIntersect = false

            val time = measureTimeMillis {
                isIntersect = isIntersect(arr1, arr2, arr3)
            }

            println("$i. $isIntersect")

            times.add(time)
        }

        println("Average ms: ${times.average()}\n")
    }
}