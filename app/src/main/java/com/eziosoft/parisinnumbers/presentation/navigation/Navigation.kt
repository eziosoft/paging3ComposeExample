package com.eziosoft.parisinnumbers.presentation.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ActionDispatcher {
    private val _actionFlow = MutableSharedFlow<Action>()
    val actionFlow = _actionFlow.asSharedFlow()

    var recordId: String = ""

    suspend fun dispatchAction(action: Action) {
        _actionFlow.emit(action)
    }
}
