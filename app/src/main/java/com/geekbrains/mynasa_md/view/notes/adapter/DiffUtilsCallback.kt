package com.geekbrains.mynasa_md.view.notes.adapter

import androidx.recyclerview.widget.DiffUtil
import com.geekbrains.mynasa_md.model.MyNote

class DiffUtilsCallback(
    val oldItemsList: List<Pair<MyNote, Boolean>>,
    val newItemsList: List<Pair<MyNote, Boolean>>
) : DiffUtil.Callback() {

    //todo Возвращаем количество элементов старого списка
    override fun getOldListSize(): Int = oldItemsList.size

    //todo Возвращаем количество элементов нового списка
    override fun getNewListSize(): Int = newItemsList.size

    //todo сравниваемм одинаковые элементы для поиска изменений в них
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItemsList[oldItemPosition].first.id == newItemsList[newItemPosition].first.id

    //todo сравнение элементов на совпадение всех возможных изменений в itemView двух списков
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemsList[oldItemPosition].first.text == newItemsList[newItemPosition].first.text
                && oldItemsList[oldItemPosition].first.date == newItemsList[newItemPosition].first.date
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val old = oldItemsList[oldItemPosition]
        val new = newItemsList[newItemPosition]
        return Change(old, new)
    }
}