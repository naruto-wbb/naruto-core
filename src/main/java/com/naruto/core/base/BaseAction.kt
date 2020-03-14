package com.naruto.core.base

data class BaseAction(var action: Int) {

    fun addAction(action: Int): BaseAction {
        this.action = this.action or action // and
        return this
    }
}