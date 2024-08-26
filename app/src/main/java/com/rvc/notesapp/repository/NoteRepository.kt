package com.rvc.notesapp.repository

import com.rvc.notesapp.database.NoteDataBase
import com.rvc.notesapp.model.Note

class NoteRepository(private val db:NoteDataBase) {

    suspend fun insertNote(note: Note) = db.getNoteDao().insertNote(note)

    suspend fun updateNote(note: Note)=db.getNoteDao().updateNote(note)

    suspend fun deleteNote(note: Note)=db.getNoteDao().deleteNote(note)

    fun getAllNotes()= db.getNoteDao().getAllNotes()

    fun searchNotes(query: String?)= db.getNoteDao().searchNotes(query)

}