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
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.rvc.notesapp.MainActivity
import com.rvc.notesapp.R
import com.rvc.notesapp.adapter.NoteAdapter
import com.rvc.notesapp.databinding.FragmentAddNoteBinding
import com.rvc.notesapp.databinding.FragmentHomeBinding
import com.rvc.notesapp.model.Note
import com.rvc.notesapp.viewmodel.NoteViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddNoteFragment : Fragment(R.layout.fragment_add_note),MenuProvider {

    private var addNoteBinding:FragmentAddNoteBinding?=null
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var addNoteView: View

    private val binding get() = addNoteBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)
        noteViewModel = (activity as MainActivity).noteViewModel
        addNoteView = view


    }

    fun saveNote(view: View)
    {
        val noteTitle = binding.addNoteTitle.text.toString().trim()
        val noteDesc = binding.addNoteDesc.text.toString().trim()
        if(noteTitle.isNotEmpty())
        {
            val note = Note(0,noteTitle,noteDesc)
            noteViewModel.addNote(note)
            Toast.makeText(addNoteView.context,"Note Saved", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment,false)
        }else
        {
            Toast.makeText(addNoteView.context,"Please enter note title" , Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_note,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId)
        {
            R.id.saveMenu ->
            {
                saveNote(addNoteView)
                true
            }
            else -> false

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding=null
    }
}