package org.example.edits

import java.util.*

class MyStack<T> {
    private val stack: Stack<T> = Stack()

    init {
        stack.clear()
    }

    fun push(item: T) {
        stack.push(item)
    }

    fun pop(): T? {
        return if (stack.isEmpty()) null
        else stack.pop()
    }

    fun isEmpty(): Boolean {
        return stack.isEmpty()
    }

    fun size() = stack.size
}