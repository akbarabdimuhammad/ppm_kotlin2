package hellocompose.unpas.ac.mynote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hellocompose.unpas.ac.mynote.models.Note
import hellocompose.unpas.ac.mynote.repositories.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {

    private val _loadList: MutableLiveData<Boolean> = MutableLiveData(false)

    val list: LiveData<List<Note>> = _loadList.switchMap {
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(
                noteRepository.loadItems(
                    onSuccess = {
                        Log.d("NoteViewModel", "load list success")
                    },
                    onError = {
                        Log.d("NoteViewModel", it)
                    }
                ).asLiveData()
            )
        }
    }

    fun refreshList() {
        _loadList.value = _loadList.value != true
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            noteRepository.insert(note, onSuccess = {
                Log.d("NoteViewModel", "insert note success")
                refreshList()
            }, onError = {
                Log.d("NoteViewModel", "insert note error: $it")
            })
        }
    }

    fun updateNote(note: Note, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            repository.updateNote(note, onSuccess, onError) {
                Log.d("NoteViewModel", "update note error: $it")
            })
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.delete(note.id, onSuccess = {
                Log.d("NoteViewModel", "delete note success")
                refreshList()
            }, onError = {
                Log.d("NoteViewModel", "delete note error: $it")
            })
        }
    }
}
