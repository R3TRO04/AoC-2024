package com.r3tro04.structure.fmt

object GenericFormatter : Formatter<Any?> {
    override fun Any?.format(): String = toString()
}