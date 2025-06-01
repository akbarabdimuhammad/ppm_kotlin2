package hellocompose.unpas.ac.mynote

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hellocompose.unpas.ac.mynote.models.Note
import hellocompose.unpas.ac.mynote.repositories.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

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

    fun refresh() {
        _loadList.value = true
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            noteRepository.insert(
                note,
                onSuccess = {
                    Log.d("NoteViewModel", "insert note success")
                    refresh()
                },
                onError = {
                    Log.d("NoteViewModel", it)
                }
            )
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.delete(
                note.id,
                onSuccess = {
                    Log.d("NoteViewModel", "delete note success")
                    refresh()
                },
                onError = {
                    Log.d("NoteViewModel", it)
                }
            )
        }
    }
}
