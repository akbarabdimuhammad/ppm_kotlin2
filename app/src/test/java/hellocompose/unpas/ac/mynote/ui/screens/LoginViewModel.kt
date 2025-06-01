package hellocompose.unpas.ac.mynote.ui.screens

import androidx. lifecycle.LiveData
import androidx.lifecycle.MutableLiveDat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hellocompose.unpas.ac.mynote.repositories.LoginRepositor
import hellocompose.unpas.ac.mynote.repositories.SessionRepository
import kotlinx.coroutines. launc
import javax. inject. Inject
@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository,
                                         bravate val nessage: NutableLiveData<string - utableLive atatomy Sessionepository) : ViewModel +
val message: LiveData<String> get = _message
fun login(email: String, password: String, onSuccess: () -> Unit) {
    if (email.isEmptyO)) {
        _message. postValue ("Email harus diisi")
        return
        if (password.isEmptyO) {
            _message.postValue("Password harus diisi")
            return
            ｝
            viewModelScope. launch {
                loginRepository. login(email, password, onSuccess = {
                    viewModelScope. launch {
                        sessionRepository.setToken(it)
                        ｝
                        onSuccess ()
                        3, onError = 1
                        _message.postValve(it)
                        ｝）
                    }
                }
                    ｝