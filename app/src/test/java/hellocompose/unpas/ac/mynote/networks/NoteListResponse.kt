package hellocompose.unpas.ac.mynote.networks
import hellocompose.unpas.ac.mynote.models.Note
data class NoteListResponse {
        val message: String, val data: List<Note>
}