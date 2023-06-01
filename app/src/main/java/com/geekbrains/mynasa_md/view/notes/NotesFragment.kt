package com.geekbrains.mynasa_md.view.notes

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.databinding.FragmentNotesBinding
import com.geekbrains.mynasa_md.model.MyNote
import com.geekbrains.mynasa_md.model.Repository
import com.geekbrains.mynasa_md.model.RepositoryImpl
import com.geekbrains.mynasa_md.view.notes.adapter.NotesAdapter
import com.geekbrains.mynasa_md.view.notes.adapter.RecyclerItemDecoration
import com.geekbrains.mynasa_md.view.notes.editor.EditNoteFragment
import com.geekbrains.mynasa_md.view.notes.touch.helper.OnTouchHelperCallback

class NotesFragment : Fragment() {

    companion object {
        fun newInstance() = NotesFragment()
    }

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val repository: Repository by lazy { RepositoryImpl() }
    private val itemTouchHelper: ItemTouchHelper by lazy {
        ItemTouchHelper(OnTouchHelperCallback(adapter))
    }

    private val adapter: NotesAdapter = NotesAdapter(object : OnNoteActionListener {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun onNoteEdited(note: MyNote) {

            parentFragmentManager?.let { manager ->
                manager.beginTransaction()
                    .replace(
                        R.id.lessons_view_container,
                        EditNoteFragment.newInstance(Bundle().also { bundle ->
                            bundle.putParcelable(EditNoteFragment.MY_NOTE, note)
                        })
                    )
                    .addToBackStack("")
                    .commit()
            }
        }

        override fun onNoteClick(note: MyNote) {
            Toast.makeText(requireContext(), note.text, Toast.LENGTH_SHORT).show()
        }

        override fun onNoteDrag(viewHolder: NotesAdapter.BaseViewHolder) {
            itemTouchHelper.startDrag(viewHolder)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.notesView
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(recyclerView)

        val data = RepositoryImpl().getDataRepos()
        binding.fragmentRecyclerViewFab.setOnClickListener {
            recyclerView.scrollToPosition(adapter.addNote(null))
        }
        val sectionCallback = object : RecyclerItemDecoration.SectionCallback {
            override fun isSectionHeader(position: Int): Boolean {
                return position == 0// || data.get(position).first.text != data.get(position-1).first.text
            }

            override fun getSectionHeaderName(position: Int): String {
                val headerName = if (!data.isEmpty()) data.get(0).first.text
                else getString(R.string.default_header)
                return headerName//data.get(position).first.text
            }
        }
        val recyclerItemDecoration = RecyclerItemDecoration(
            requireContext(),
            resources.getDimensionPixelSize(R.dimen.header_height), true, sectionCallback
        )
        recyclerView.addItemDecoration(recyclerItemDecoration)
        adapter.setNotes(data)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}