package com.example.registrationapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistrationForm()
        }
    }
}

@Composable
fun RegistrationForm() {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        // Form fields
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Nomor Telepon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            keyboardActions = KeyboardActions(onDone = { /* Handle done */ }),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(onDone = { /* Handle done */ }),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Alamat Rumah") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        // Buttons
        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
            Button(
                onClick = {
                    if (name.isNotEmpty() && username.isNotEmpty() && phoneNumber.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty()) {
                        Toast.makeText(context, "Halo, $name", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Semua inputan harus diisi!", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.weight(1f).padding(4.dp)
            ) {
                Text("Simpan")
            }

            Button(
                onClick = {
                    name = ""
                    username = ""
                    phoneNumber = ""
                    email = ""
                    address = ""
                },
                modifier = Modifier.weight(1f).padding(4.dp)
            ) {
                Text("Reset")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RegistrationForm()
}
