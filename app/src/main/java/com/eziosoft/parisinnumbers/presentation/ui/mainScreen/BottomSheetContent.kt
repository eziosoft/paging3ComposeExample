package com.eziosoft.parisinnumbers.presentation.ui.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun BottomSheetContent() {
    Box(
        modifier = Modifier.background(Color.LightGray)
    ) {
        Text("Sheet Content", fontSize = 80.sp)
    }
}
