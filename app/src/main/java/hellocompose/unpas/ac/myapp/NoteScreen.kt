package hellocompose.unpas.ac.mynote
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.benasher44.uuid.uuid4
import id.ac.unpas.mynote.models.Note
import kotlinx.coroutines.launch

@Composable
fun NoteScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val dao = NoteDatabase.getDatabase(context).noteDao()
    val list: LiveData<List<Note>> = dao.getAllNotes()
    val notes: List<Note> by list.observeAsState(initial = listOf())

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }
    var editingNoteId by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()) {
                        scope.launch {
                            if (isEditing && editingNoteId != null) {
                                // Update note
                                dao.insertNote(Note(editingNoteId!!, title, description))
                                Toast.makeText(context, "Note updated", Toast.LENGTH_SHORT).show()
                            } else {
                                // Insert new note
                                dao.insertNote(Note(uuid4().toString(), title, description))
                                Toast.makeText(context, "Note saved", Toast.LENGTH_SHORT).show()
                            }
                            title = ""
                            description = ""
                            isEditing = false
                            editingNoteId = null
                        }
                    } else {
                        Toast.makeText(context, "Semua input harus diisi", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isEditing) "Update Note" else "Save Note")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    title = ""
                    description = ""
                    isEditing = false
                    editingNoteId = null
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Reset")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(note.title, style = MaterialTheme.typography.titleMedium)
                        Text(note.description, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Button(
                                onClick = {
                                    // Edit
                                    title = note.title
                                    description = note.description
                                    editingNoteId = note.id
                                    isEditing = true
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Edit")
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    scope.launch {
                                        dao.deleteNote(note)
                                        Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
                                        if (isEditing && editingNoteId == note.id) {
                                            // Batal edit kalau note yang diedit dihapus
                                            title = ""
                                            description = ""
                                            isEditing = false
                                            editingNoteId = null
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}
