package com.eziosoft.parisinnumbers.navigation

sealed class Action {
    data class Navigate(val destination: Destination) : Action()
    data class ToggleBottomSheet(val expanded: Boolean) : Action()
    data class ShowSnackbar(val text: String) : Action()
}
