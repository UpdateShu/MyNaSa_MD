package com.geekbrains.mynasa_md.view.notes.touch.helper

interface OnTouchHelperAdapter {
    fun onItemUpdated(position: Int)
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}