package com.example.lab7appium

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.lab7appium.ui.theme.Lab7AppiumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab7AppiumTheme {
                NotesApp()
            }
        }
    }
}

@Composable
fun NotesApp() {
    var noteText by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Поле ввода заметки
        TextField(
            value = noteText,
            onValueChange = { noteText = it },
            label = { Text("Введите заметку") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("noteInput")
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ Кнопка добавления — добавляем contentDescription
        Button(
            onClick = {
                if (noteText.isNotBlank()) {
                    notes = notes + noteText
                    noteText = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("addNoteButton")
                .semantics { contentDescription = "addNoteButton" } // <-- добавлено
        ) {
            Text("Добавить заметку")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Список заметок
        LazyColumn {
            itemsIndexed(notes) { index, note ->
                NoteItem(note, index) { idx ->
                    notes = notes.toMutableList().also { it.removeAt(idx) }
                }
            }
        }
    }
}

@Composable
fun NoteItem(note: String, index: Int, onDelete: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = note,
            modifier = Modifier
                .weight(1f)
                .testTag("note_$index")
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = { onDelete(index) },
            modifier = Modifier.testTag("deleteNote_$index")
        ) {
            Text("Удалить")
        }
    }
}
