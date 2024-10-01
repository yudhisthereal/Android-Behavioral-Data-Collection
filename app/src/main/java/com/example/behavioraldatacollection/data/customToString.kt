package com.example.behavioraldatacollection.data

fun <T> MutableList<T>.toRowsString(): String {
    return this.joinToString(separator = "\n")
}
