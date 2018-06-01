package ru.nsk.dsushko.hunter.presentation.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.nsk.dsushko.hunter.R
import ru.nsk.dsushko.hunter.presentation.models.EventInfo
import ru.nsk.dsushko.hunter.presentation.presenters.ChooseEventPresenter

class EventAdapter(private val inflater : LayoutInflater,
                   private val presenter: ChooseEventPresenter,
                   private val events: List<EventInfo>) : RecyclerView.Adapter<EventHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val view = inflater.inflate(R.layout.item_event_in_recycler_view, parent,false)
        return EventHolder(presenter, view, events)
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.changeEvent(position)
    }
}