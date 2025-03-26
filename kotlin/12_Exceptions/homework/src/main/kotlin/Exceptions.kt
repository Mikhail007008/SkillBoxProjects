package org.example

class Exceptions {
    class TooHighPressure(message: String) : Exception(message)
    class TooLowPressure(message: String) : Exception(message)
    class IncorrectPressure(message: String) : Exception(message)
}