package com.example.noteappdb

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController


class FragmentNew : Fragment() {

    lateinit var sharedPreferences : SharedPreferences
    val updateViewModel by lazy { ViewModelProvider(this).get( UpdateViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_new, container, false)

        // We use 'requireActivity()' to access MainActivity here
         sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        var etNote = view.findViewById<EditText>(R.id.etUpdate)
        var btUpdate = view.findViewById<Button>(R.id.btnUpdate)



        btUpdate.setOnClickListener {
            var noteId = sharedPreferences.getString("NoteId", "")!!.toString()
            updateViewModel.updateNote(noteId.toInt(), etNote.text.toString())
            with(sharedPreferences.edit()) {
            putString("NoteId", etNote.text.toString())
            apply()
        }
           this.findNavController().navigate(R.id.action_fragmentNew_to_fragmentHome)
        }

        return view
    }

}