package com.eziosoft.parisinnumbers.presentation.ui.mainScreen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.eziosoft.parisinnumbers.domain.repository.DatabaseRepository
import com.eziosoft.parisinnumbers.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.presentation.ui.theme.ParisInNumbersTheme
import com.eziosoft.parisinnumbers.presentation.ui.theme.PrimaryDark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val actionDispatcher: ActionDispatcher by inject()
    private val dbRepository: DatabaseRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        downloadDatabase()

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

    private fun downloadDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            Log.d("aaa", "downloadDatabase: ")
            dbRepository.fillDb()
            Log.d("aaa", "downloadDatabase: end ")
        }
    }
}
