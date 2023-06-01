package com.geekbrains.mynasa_md.view.notes.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.databinding.ItemHeaderBinding
import com.geekbrains.mynasa_md.databinding.ItemNoteBinding
import com.geekbrains.mynasa_md.model.MyNote
import com.geekbrains.mynasa_md.utils.Constants.TYPE_HEADER
import com.geekbrains.mynasa_md.utils.Constants.TYPE_NOTE
import com.geekbrains.mynasa_md.utils.getDate
import com.geekbrains.mynasa_md.view.notes.OnNoteActionListener
import com.geekbrains.mynasa_md.view.notes.touch.helper.OnTouchHelperAdapter
import com.geekbrains.mynasa_md.view.notes.touch.helper.OnTouchHelperViewHolder

class NotesAdapter(val actionNoteListener: OnNoteActionListener) :
    RecyclerView.Adapter<NotesAdapter.BaseViewHolder>(), OnTouchHelperAdapter {
    private val myNotes: MutableList<Pair<MyNote, Boolean>> = mutableListOf()
    private var selected: Int = RecyclerView.NO_POSITION

    @SuppressLint("NotifyDataSetChanged")
    fun setNotes(notes: List<Pair<MyNote, Boolean>>) {
        if (myNotes.isEmpty()) {
            notes.isNotEmpty().let {
                val g = DiffUtil.calculateDiff(DiffUtilsCallback(myNotes, notes))
                    .dispatchUpdatesTo(this)
            }
            myNotes.clear()
            myNotes.addAll(notes.filter { x -> x.first.type == TYPE_NOTE })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNote(adapterPosition: Int?) : Int {
        myNotes.add(myNotes.size, generationNotes())
        notifyItemInserted(myNotes.size)
        adapterPosition?.let { notifyItemChanged(it) }
        return myNotes.size-1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun generationNotes() =
        Pair(
            MyNote(
                id = myNotes.size + 1,
                text = " ",
                type = TYPE_NOTE,
                date = getDate(0)
            ),
            false
        )

    override fun getItemCount(): Int = myNotes.size

    override fun getItemViewType(position: Int): Int {
        return myNotes[position].first.type
    }

    override fun onItemUpdated(position: Int) {
        if (myNotes[position].first.type != TYPE_HEADER) {
            notifyItemChanged(position)
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (myNotes[toPosition].first.type != TYPE_HEADER) {
            myNotes.removeAt(fromPosition).apply {
                myNotes.add(toPosition, this)
            }
            notifyItemMoved(fromPosition, toPosition)
        }
    }

    override fun onItemDismiss(position: Int) {
        if (myNotes[position].first.type != TYPE_HEADER) {
            myNotes.removeAt(position)
            notifyItemRemoved(position)
        } else {
            notifyItemChanged(position)
        }
    }

    //Внешний вид и отображение

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int
    ) {
        holder.bind(myNotes[position])
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (!payloads.isEmpty()) {
            val newData =
                createPayloads(payloads as List<Change<Pair<MyNote, Boolean>>>).newData
            val oldData =
                createPayloads(payloads as List<Change<Pair<MyNote, Boolean>>>).oldData
            when (holder) {
                is NoteViewHolder -> {
                    with(ItemNoteBinding.bind(holder.itemView)) {
                        if (newData.first.text != oldData.first.text) {
                            noteText.text = newData.first.text
                            //noteText.typeface = Typeface.createFromAsset(it.assets, "az_Eret1.ttf")
                        }
                    }
                }

                is HeaderViewHolder -> {
                    ItemHeaderBinding.bind(holder.itemView)
                        .headerTitleTextView.text = newData.first.text
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view),
        OnTouchHelperViewHolder {
        abstract fun bind(data: Pair<MyNote, Boolean>)

        override fun onItemSelected() {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context,
                R.color.material_amber_300))
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    inner class NoteViewHolder(itemView: View) : BaseViewHolder(itemView),
        OnTouchHelperViewHolder {
        @SuppressLint("ClickableViewAccessibility")
        override fun bind(notes: Pair<MyNote, Boolean>) {
            ItemNoteBinding.bind(itemView).apply {
                val note = notes.first
                cardView.setOnClickListener {
                    actionNoteListener.onNoteClick(note)
                }
                noteText.text = notes.first.text
                noteEdit.setOnClickListener {
                    actionNoteListener.onNoteEdited(note)
                }
                noteDelete.setOnClickListener {
                    myNotes.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
                up.setOnClickListener {
                    if (layoutPosition > 0
                        && myNotes[layoutPosition - 1].first.type != TYPE_HEADER) {
                        myNotes.removeAt(layoutPosition).apply {
                            myNotes.add(layoutPosition - 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition - 1)
                    }
                }
                down.setOnClickListener {
                    if (myNotes.size - 1 > layoutPosition
                        && myNotes[layoutPosition + 1].first.type != TYPE_HEADER) {
                        myNotes.removeAt(layoutPosition).apply {
                            myNotes.add(layoutPosition + 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition + 1)
                    }
                }
            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bind(notes: Pair<MyNote, Boolean>) {
            ItemHeaderBinding.bind(itemView).apply {
                headerTitleTextView.text = notes.first.text
            }
        }
    }

    inner class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bind(data: Pair<MyNote, Boolean>) {
            return
        }
    }
}