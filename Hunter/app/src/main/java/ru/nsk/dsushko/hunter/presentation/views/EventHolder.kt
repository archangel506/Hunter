package ru.nsk.dsushko.hunter.presentation.views

import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import android.widget.TextView
import ru.nsk.dsushko.hunter.R
import ru.nsk.dsushko.hunter.presentation.models.EventInfo
import ru.nsk.dsushko.hunter.presentation.presenters.ChooseEventPresenter

class EventHolder(presenter: ChooseEventPresenter, view: View,
                  private val events: List<EventInfo>) : RecyclerView.ViewHolder(view) {
    private val title = view.findViewById<TextView>(R.id.title_event_card)
    private val city = view.findViewById<TextView>(R.id.city_event_card)
    private var positionInRecycler = 0

    init {
        view.setOnClickListener {
            presenter.moveFormEvent(events, positionInRecycler)
        }
    }

    @Suppress("DEPRECATION")
    fun changeEvent(position: Int){
        positionInRecycler = position
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            title.text = Html.fromHtml(events[position].title ?: "", Html.FROM_HTML_MODE_LEGACY)
        } else {
            title.text = Html.fromHtml(events[position].title ?: "")
        }

        city.text = events[position].cities?.get(0)?.nameRus ?: ""
    }
}