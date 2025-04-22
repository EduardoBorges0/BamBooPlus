package com.app.bamboo.presentation.view.insertMedications.medicationTime

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.bamboo.R
import com.app.bamboo.presentation.view.ui.theme.MainColor
import com.app.bamboo.presentation.view.ui.theme.SecondaryColor

@Composable
fun HoursOrDays(
    hoursOrDays: String,
    onHoursOrDaysChange: (String) -> Unit,
    isError: Boolean
) {
    val widthSize = LocalConfiguration.current.screenWidthDp.dp
    val heigthSize = LocalConfiguration.current.screenHeightDp.dp

    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .border(2.dp, color = if(isError) Color.Red else MainColor, RoundedCornerShape(16.dp))
            .padding(10.dp)
            .width(widthSize / 1.56f)
    ) {
        Text(
            stringResource(R.string.You_take_medication_in_days_or_hours),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp),
            color = if(isError) Color.Red else Color.Black,
            fontSize = 15.sp
        )
        Row(modifier = Modifier.padding(top = 5.dp)) {
            Button(
                onClick = {
                    onHoursOrDaysChange("Hours")
                },
                modifier = Modifier
                    .size(width = widthSize / 3, height = heigthSize / 15)
                    .padding(horizontal = 5.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (hoursOrDays == "Hours") SecondaryColor else MainColor
                )
            ) {
                Text(stringResource(R.string.Hours))
            }
            Button(
                onClick = {
                    onHoursOrDaysChange("Days")
                },
                modifier = Modifier
                    .size(width = widthSize / 3, height = heigthSize / 15)
                    .padding(horizontal = 5.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (hoursOrDays == "Days") SecondaryColor else MainColor
                )
            ) {
                Text(stringResource(R.string.Days))
            }
        }
    }

}