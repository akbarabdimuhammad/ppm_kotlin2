package hellocompose.unpas.ac.mynote.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx. compose.material3.TextField
import androidx. compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableState0f
import androidx.compose.runtime.remember
import androidx. compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
@Destination<RootGraph>
@Composable
fun LoginScreen(navigator: DestinationsNavigator) {
    val viewModel = hiltViewModel<LoginViewModel>()
    var email by remember { mutableState0f("") }
    var password by remember { mutableState0f("") }
    Column (modifier = Modifier padding (16.dp)) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
                    Spacer (Modifier.height(8.dp))
                    TextField(
                    value = password,
            onValueChange = f password = it },
    label = f Text("Password") },
modifier = Modifier.fillMaxWidthO,
visualTransformation = PasswordVisualTransformation),
Spacer (Modifier.height(8.dp))
if (message.isNotEmpty()) {
    Text(message, modifier = Modifier.fillMaxWidth())
    Spacer (Modifier.height(8.dp))
    Button (
        onClick = {
            viewModel. login (email, password) f navigator.navigate(NoteScreenDestination)
        }
                modifier = Modifier.fillMaxWidth()
                Text ("Login")
}
}
viewModel.message.observe(LocalLifecycle0wner.current) {
    message = it
}
}
