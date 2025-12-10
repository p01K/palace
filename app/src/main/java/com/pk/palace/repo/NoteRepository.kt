package com.pk.palace.repo

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.pk.palace.dto.NoteDTO
import com.pk.palace.model.Bite
import com.pk.palace.model.Note
import com.pk.palace.model.NoteCategory
import com.pk.palace.model.toDomain
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

interface NoteRepository {
    suspend fun getBites(): List<Bite>

    fun getBite(id: Int): Flow<Bite?>
}

class FirebaseNoteRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : NoteRepository {

    override suspend fun getBites(): List<Bite> {
        return db.collection("notes").get().await()
            .toObjects(NoteDTO::class.java)
            .map{ it.toDomain() };
    }

    override fun getBite(id: Int): Flow<Bite?> = callbackFlow {
        Log.i("PalaceFirestore", "Trying to fetch note with id $id")
        val listener = db.collection("notes")
            .document(id.toString())
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(null)
                    return@addSnapshotListener
                }

                val bite = snapshot?.toObject(NoteDTO::class.java)?.toDomain()
                trySend(bite)
            }

        // Clean up when flow is closed
        awaitClose { listener.remove() }
    }

    fun addNote(note: NoteDTO) {
        db.collection("notes").add(note)
    }
}


class InMemoryNoteRepository : NoteRepository {
    private val _notes = MutableStateFlow(listOf<Bite>())
    val notes = _notes.asStateFlow()

    init {
        loadNotes()
    }

    override fun getBite(id: Int): Flow<Bite?> = notes.map { notes ->  notes.find { n ->  n.id==id } }

    override suspend fun getBites(): List<Bite> {
        return notes.value
    }

    private fun loadNotes() {
        _notes.value = listOf(
            Note(1, "Mechanical Sympathy", "What is it really ?", NoteCategory.APHORISM),
            Note(2, "Object Mother", "What is it really ?", NoteCategory.APHORISM),
            Note(3, "Jean Michele Jar", "Name one song", NoteCategory.APHORISM),
            Note(4, "Seneca", "One good quote ...", NoteCategory.APHORISM)
        )
    }
}