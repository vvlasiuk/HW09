package net.ukr.vlsv.hw03_chat.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//@Inject constructor
class ViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(MessageViewModel::class.java) -> {
            MessageViewModel(application = application) as T
        }
        else -> throw IllegalArgumentException()
    }
}