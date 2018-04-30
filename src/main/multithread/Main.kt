package multithread

import kotlinx.coroutines.experimental.*

fun isIntersect(arr1: Collection<*>, arr2: Collection<*>, arr3: Collection<*>): Boolean = runBlocking {
    if (arr1.isEmpty() || arr2.isEmpty() || arr3.isEmpty()) return@runBlocking false

    val intersectionOfFirstAndSecond = async { arr1.intersect(arr2) }
    val intersectionOfSecondAndThird = async { arr2.intersect(arr3) }
    val intersectionOfThirdAndFirst = async { arr3.intersect(arr1) }

    val inter1 = async { intersectionOfFirstAndSecond.await().intersect(intersectionOfSecondAndThird.await()) }
    val inter2 = intersectionOfThirdAndFirst.await().intersect(inter1.await())

    return@runBlocking !inter2.isEmpty()
}