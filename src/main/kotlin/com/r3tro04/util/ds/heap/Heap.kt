package com.r3tro04.util.ds.heap

interface Heap<E> : Collection<E> {
    fun minOrNull(): E?
    fun min(): E = minOrNull() ?: throw NoSuchElementException("Heap is empty")
    operator fun get(pos: Int): E
}