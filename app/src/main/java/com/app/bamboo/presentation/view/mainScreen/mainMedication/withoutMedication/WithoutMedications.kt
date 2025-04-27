package com.app.bamboo.presentation.view.mainScreen.mainMedication.withoutMedication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.bamboo.R
import com.app.bamboo.presentation.view.ui.theme.MainColor
import com.app.bamboo.presentation.view.ui.theme.textColor
import com.app.bamboo.presentation.view.usefulCompounds.TypeWriterText
import kotlinx.coroutines.delay

@Composable
fun WithoutMedications(heightSize: Dp, widthSize: Dp, modifier: Modifier, title: String) {
    var text = TypeWriterText(title)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(31.dp))
            .size(height = heightSize / 3, width = widthSize / 1.3f)
            .background(
                MaterialTheme.colorScheme.primary
            )
    ) {
        Column(modifier = Modifier.align(Alignment.Center)){
            Text(
                text,
                color = textColor,
                modifier = modifier.padding(horizontal = 40.dp).align(Alignment.CenterHorizontally),
                fontSize = 15.sp
            )
            AsyncImage(
                model = R.drawable.boo_phone,
                contentDescription = "boo_phone",
                modifier = Modifier.size(heightSize / 7).align(Alignment.CenterHorizontally)
            )

        }

    }
}