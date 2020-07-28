package com.emerson.bankapp

class ArrayEmptyException : RuntimeException {
    override var message: String
        private set

    constructor() {
        message = "ArrayEmptyException"
    }

    constructor(exceptionMessage: String) {
        message = exceptionMessage
    }

    override fun toString(): String {
        return message
    }
}