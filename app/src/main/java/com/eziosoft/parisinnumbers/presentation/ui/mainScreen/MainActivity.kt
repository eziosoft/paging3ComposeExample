package com.eziosoft.parisinnumbers.presentation.ui.mainScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.eziosoft.parisinnumbers.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.presentation.ui.theme.ParisInNumbersTheme
import com.eziosoft.parisinnumbers.presentation.ui.theme.PrimaryDark
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val actionDispatcher: ActionDispatcher by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ParisInNumbersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PrimaryDark
                ) {
                    NavigationComponent(actionDispatcher = actionDispatcher)
                }
            }
        }
    }
}
