package com.app.bamboo.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel
import javax.inject.Inject

class UpdateNotificationName @Inject constructor() {
    private val _title = MutableLiveData("Lembrete Diário")
    val title: LiveData<String> = _title

    private val _message = MutableLiveData("Está na hora de conferir seu app!")
    val message: LiveData<String> = _message

    fun updateNotificationTexts(newTitle: String, newMessage: String) {
        _title.value = newTitle
        _message.value = newMessage
    }
}