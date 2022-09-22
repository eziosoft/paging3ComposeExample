package com.eziosoft.parisinnumbers.navigation

import org.koin.dsl.module

val navigationModule = module {
    single { SharedParameters() }
    single { ActionDispatcher(get()) }
}
