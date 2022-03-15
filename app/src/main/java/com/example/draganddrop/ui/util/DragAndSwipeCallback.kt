package com.example.draganddrop.ui.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class DragAndSwipeCallback(
    var isEnabled: Boolean = true
) : ItemTouchHelper.SimpleCallback(
    NO_DRAG,
    NO_SWIPE // ItemTouchHelper.START or ItemTouchHelper.END
) {

    /* Store the initial position of the item is dragging or swiping */
    private var initialPosition = NO_POSITION

    private var fromPosition: Int = NO_POSITION
    private var toPosition: Int = NO_POSITION

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {

        val dragFlags = if (viewHolder is ItemDragListener) {
            ItemTouchHelper.UP or ItemTouchHelper.DOWN
        } else NO_DRAG

        val swipeFrags = if (viewHolder is ItemSwipeListener) {
            ItemTouchHelper.START or ItemTouchHelper.END
        } else NO_SWIPE

        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFrags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return isEnabled
    }

    override fun isLongPressDragEnabled(): Boolean {
        return isEnabled
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return isEnabled
    }

    override fun onMoved(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        fromPos: Int,
        target: RecyclerView.ViewHolder,
        toPos: Int,
        x: Int,
        y: Int
    ) {
        fromPosition = fromPos
        toPosition = toPos

        if (initialPosition == NO_POSITION) {
            initialPosition = viewHolder.adapterPosition
        }

        if (viewHolder is ItemDragListener) {
            viewHolder.onItemMoved(fromPos, toPos)
        }

    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (viewHolder is ItemSwipeListener) {
            viewHolder.onItemSwiped()
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is ItemDragListener) {
            // If the drag was abandoned, or if the item was dragged back to the original position,
            // do nothing.
            if (initialPosition != NO_POSITION && viewHolder.adapterPosition != initialPosition) {
                viewHolder.onItemMoveCompleted(fromPosition, toPosition)
            }
        }
        initialPosition = NO_POSITION
    }


    interface ItemDragListener {
        fun onItemMoved(fromPosition: Int, toPosition: Int)
        fun onItemMoveCompleted(fromPosition: Int, toPosition: Int)
    }

    interface ItemSwipeListener {
        fun onItemSwiped()
    }


    companion object {
        const val NO_DRAG = 0
        const val NO_SWIPE = 0
        const val NO_POSITION = -1
    }
}