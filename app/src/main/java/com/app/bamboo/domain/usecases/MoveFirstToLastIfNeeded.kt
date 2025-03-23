package com.app.bamboo.domain.usecases

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MoveFirstMedicationIfNeededUseCase @Inject constructor() {
    private val formatter = DateTimeFormatter.ofPattern("HH:mm")
    private val _medications = MutableLiveData<List<String>>(emptyList())

    val medications: LiveData<List<String>> = _medications

    private val movedItems = mutableSetOf<String>()

    fun updateList(medications: List<String>) {
        _medications.value = medications
        movedItems.clear()
    }

    fun moveFirstToLastIfNeeded() {
        val currentList = _medications.value ?: return
        val now = LocalTime.now()

        if (currentList.isNotEmpty()) {
            val firstTime = LocalTime.parse(currentList.first(), formatter)

            // Se o primeiro item já passou do horário e ainda não foi movido, move ele
            if (firstTime.isBefore(now) && !movedItems.contains(currentList.first())) {
                val newList = currentList.toMutableList()
                val first = newList.removeAt(0)
                newList.add(first)

                _medications.value = newList  // Atualiza a lista no LiveData
                movedItems.add(first) // Marca que esse item já foi movido hoje

                Log.d("LISTA", "Lista atualizada: $newList e Hora Atual: ${now.format(formatter)}")
            }
        }
    }

}
