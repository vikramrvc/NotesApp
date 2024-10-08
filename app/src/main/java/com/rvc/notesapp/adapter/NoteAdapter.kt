package com.rvc.notesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rvc.notesapp.databinding.NoteLayoutBinding
import com.rvc.notesapp.fragments.HomeFragmentDirections
import com.rvc.notesapp.model.Note
import org.jetbrains.annotations.Async

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(val itemBinding: NoteLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    }

    private val diffUtilCallback =object :DiffUtil.ItemCallback<Note>()
    {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id==newItem.id && oldItem.desc==newItem.desc && oldItem.title==newItem.title
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {

            return oldItem==newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(NoteLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote  = differ.currentList[position]

        holder.itemBinding.noteTitle.text = currentNote.title
        holder.itemBinding.noteDesc.text = currentNote.desc

        holder.itemView.setOnClickListener{
            val directions = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote)
            it.findNavController().navigate(directions)
            
        }
    }
}