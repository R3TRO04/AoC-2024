package com.r3tro04.structure.fmt

interface Formatter<in T> {
    fun T.format(): String
}