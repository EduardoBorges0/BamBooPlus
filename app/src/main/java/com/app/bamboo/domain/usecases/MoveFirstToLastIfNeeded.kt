package com.app.bamboo.domain.usecases

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.bamboo.utils.TimeUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MoveFirstMedicationIfNeededUseCase @Inject constructor() {
    private val _medications = MutableStateFlow<List<String>>(emptyList())
    val medications: StateFlow<List<String>> = _medications

    private val movedItems = mutableSetOf<String>()

    fun updateList(medications: List<String>) {
        _medications.value = medications
        movedItems.clear()
    }
    fun moveFirstToLastIfNeeded() {
        val currentList = _medications.value
        val now = LocalTime.now()
        Log.d("NotificationAction", "${medications.value}")

        if (currentList.isNotEmpty()) {
            val firstTime = TimeUtils.formattedLocalDate(currentList[0])

            if (firstTime.isBefore(now) && !movedItems.contains(currentList.first())) {
                val newList = currentList.toMutableList()
                val first = newList.removeAt(0)
                newList.add(first)

                _medications.value = newList
                movedItems.add(first)

                Log.d("LISTA", "Lista atualizada: $newList")
            }
        }
    }
}
