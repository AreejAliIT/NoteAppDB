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
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappdb.adapters.RVA

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class FragmentHome : Fragment() {


    private lateinit var rv: RecyclerView
    private lateinit var rvAdapter : RVA

    val myViewModel by lazy { ViewModelProvider(this).get( ViewModel::class.java) }

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_home, container, false)

        // We use 'requireActivity()' to access MainActivity here
        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        // use live data and update rv in RVA
        myViewModel.getItemsList().observe(viewLifecycleOwner, { // get data from view model
                notes -> rvAdapter.update(notes)
        })

        // save btn clicked
        val etNote = view.findViewById<EditText>(R.id.etNote)
        val btn = view.findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            var noteToSave = etNote.text.toString()
            myViewModel.postNote(noteToSave) // view model to post note
            etNote.text.clear()
            etNote.clearFocus()
        }
        // set and update RV
        rv = view.findViewById(R.id.rv)
        rvAdapter = RVA(this)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        myViewModel.getItemsList()
        return view
    }
}
