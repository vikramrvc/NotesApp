package com.rvc.notesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.rvc.notesapp.MainActivity
import com.rvc.notesapp.R
import com.rvc.notesapp.databinding.FragmentAddNoteBinding
import com.rvc.notesapp.databinding.FragmentEditNoteBinding
import com.rvc.notesapp.model.Note
import com.rvc.notesapp.viewmodel.NoteViewModel


class EditNoteFragment : Fragment(R.layout.fragment_edit_note),MenuProvider {

     var editNoteBinding: FragmentEditNoteBinding?=null
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var currentNote: Note
    private val binding get() = editNoteBinding!!

    private val args : EditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editNoteBinding = FragmentEditNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)
        noteViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.editNoteTitle.setText(currentNote.title)
        binding.editNoteDesc.setText(currentNote.desc)
        binding.editNoteFab.setOnClickListener{
            val noteTitle = binding.editNoteTitle.text.toString()
            val noteDesc = binding.editNoteDesc.text.toString()

            if(noteTitle.isNotEmpty())
            {
                val note = Note(currentNote.id,noteTitle,noteDesc)
                noteViewModel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment,false)
            }
            else
            {
                Toast.makeText(context,"Please enter note title" , Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deleteNote()
    {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this Note?")
            setPositiveButton("Delete"){_,_,->
                noteViewModel.deleteNote(currentNote)
                Toast.makeText(context,"Note Deleted",Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)
            }
            setNegativeButton("Cancel" , null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
       return  when (menuItem.itemId)
       {
           R.id.deleteMenu ->
           {
               deleteNote()
               true
           }
           else -> false
       }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding =null
    }
}