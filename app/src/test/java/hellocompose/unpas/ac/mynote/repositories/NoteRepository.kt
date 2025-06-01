package hellocompose.unpas.ac.mynote.repositories


class NoteRepository {
    import androidx.annotation.WorkerThread
    import com.skydoves.sandwich.message
    import com.skydoves.sandwich.suspendOnError
    import com.skydoves.sandwich.suspendOnException
    import com.skydoves.sandwich.suspendOnSuccess
    import com.skydoves.whatif.whatIfNotNull
    import hellocompose.unpas.ac.mynote.dao.NoteDao
    import hellocompose.unpas.ac.mynote.models.Note
    import hellocompose.unpas.ac.mynote.networks.NoteApi
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.flow.flow
    import kotlinx.coroutines.flow.flowOn
    import javax.inject.Inject

    class NoteRepository @Inject constructor(
        private val api: NoteApi,
        private val dao: NoteDao
    ) {
        try {
            val response = api.update(note.id, note)
            dao.update(response)
            onSuccess()
        } catch (e: Exception) {
            onError(e.message ?: "Unknown error")
        }
        @WorkerThread
        fun loadItems(
            onSuccess: () -> Unit,
            onError: (String) -> Unit
        ) = flow {
            val list: List<Note> = dao.getAllNotes()

            val response = api.all()

            response.suspendOnSuccess {
                data.whatIfNotNull {
                    dao.insertNotes(it)
                    emit(it)
                    onSuccess()
                }
            }

            response.suspendOnError {
                onError(message())
                emit(list)
            }

            response.suspendOnException {
                onError(message())
                emit(list)
            }
        }.flowOn(Dispatchers.IO)

        suspend fun insert(
            item: Note,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
        ) {
            val response = api.insert(item)

            response.suspendOnSuccess {
                dao.insertNote(item)
                onSuccess()
            }

            response.suspendOnError {
                onError(message())
            }

            response.suspendOnException {
                onError(message())
            }
        }

        suspend fun update(
            id: String,
            item: Note,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
        ) {
            val response = api.update(id, item)

            response.suspendOnSuccess {
                dao.insertNote(item)
                onSuccess()
            }

            response.suspendOnError {
                onError(message())
            }

            response.suspendOnException {
                onError(message())
            }
        }

        suspend fun delete(
            id: String,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
        ) {
            val response = api.delete(id)

            response.suspendOnSuccess {
                dao.deleteNote(id)
                onSuccess()
            }

            response.suspendOnError {
                onError(message())
            }

            response.suspendOnException {
                onError(message())
            }
        }
    }