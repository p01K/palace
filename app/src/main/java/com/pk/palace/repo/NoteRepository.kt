package com.pk.palace.repo

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.pk.palace.model.ContentType
import com.pk.palace.model.Note
import com.pk.palace.model.NoteCategory
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

interface NoteRepository {
    suspend fun getNotes(): List<Note>

    fun getNoteById(id: Int): Flow<Note?>
}

class FirebaseNoteRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : NoteRepository {

    override suspend fun getNotes(): List<Note> {
        return db.collection("notes").get().await().toObjects(Note::class.java);
    }

    override fun getNoteById(id: Int): Flow<Note?> = callbackFlow {
        Log.i("PalaceFirestore", "Trying to fetch note with id $id")
        val listener = db.collection("notes")
            .document(id.toString())
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(null)
                    return@addSnapshotListener
                }

                val note = snapshot?.toObject(Note::class.java)
                trySend(note)
            }

        // Clean up when flow is closed
        awaitClose { listener.remove() }
    }

    fun addNote(note: Note) {
        db.collection("notes").add(note)
    }
}


class InMemoryNoteRepository : NoteRepository {
    private val _notes = MutableStateFlow(listOf<Note>())
    val notes = _notes.asStateFlow()

    init {
        loadNotes()
    }

    override fun getNoteById(id: Int): Flow<Note?> = notes.map { notes ->  notes.find { n ->  n.id==id } }

    override suspend fun getNotes(): List<Note> {
        return notes.value
    }

    private fun loadNotes() {
        _notes.value = listOf(
            Note(1, "Mechanical Sympathy", "What is it really ?", ContentType.QUOTE, NoteCategory.APHORISM),
            Note(2, "Object Mother", "What is it really ?", ContentType.QUOTE, NoteCategory.APHORISM),
            Note(3, "Jean Michele Jar", "Name one song", ContentType.QUOTE, NoteCategory.APHORISM),
            Note(4, "Seneca", "One good quote ...", ContentType.QUOTE, NoteCategory.APHORISM)
        )
    }
}