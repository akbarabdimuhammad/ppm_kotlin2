package hellocompose.unpas.ac.mynote.ui.screens

import androidx. lifecycle.ViewModel import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ac.unpas.mynote.repositories.SessionRepository
import kotlinx.coroutines.flow.catch import kotlinx.coroutines.launch import javax.inject.Inject
@HiltViewModel
class SplashViewModel @Inject constructor(private val sessionRepository: SessionRepository) : ViewModel) {
    fun checkSession(onSuccess: 0 -> Unit, onError: • -> Unit) {
        viewModelScope. launch {
            sessionRepository.token.catch ‹
            onError)
        }. collect 1
        if (it.isNotEmptyO) {
            onSuccess
        } else {
            onError)
        }
    }
}
