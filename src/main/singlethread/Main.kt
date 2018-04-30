package singlethread

fun isIntersect(arr1: Collection<*>, arr2: Collection<*>, arr3: Collection<*>): Boolean {
    if (arr1.isEmpty() || arr2.isEmpty() || arr3.isEmpty()) return false

    val intersectionOfFirstAndSecond = arr1.intersect(arr2)
    val intersectionOfSecondAndThird = arr2.intersect(arr3)
    val intersectionOfThirdAndFirst = arr3.intersect(arr1)

    return !intersectionOfFirstAndSecond
        .intersect(intersectionOfSecondAndThird)
        .intersect(intersectionOfThirdAndFirst)
        .isEmpty()
}