package com.eziosoft.parisinnumbers.presentation.ui.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eziosoft.parisinnumbers.presentation.ui.theme.PrimaryLight

@Composable
fun BottomSheetContent() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .background(PrimaryLight)
    ) {
        Text("Sheet Content", fontSize = 80.sp)
    }
}
