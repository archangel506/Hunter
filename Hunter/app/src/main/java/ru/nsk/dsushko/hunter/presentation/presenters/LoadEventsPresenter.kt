package ru.nsk.dsushko.hunter.presentation.presenters

import android.content.Context
import ru.nsk.dsushko.hunter.domain.Interructer
import ru.nsk.dsushko.hunter.presentation.models.Convector
import ru.nsk.dsushko.hunter.presentation.views.LoadEventsActivity


class LoadEventsPresenter(private val context: Context) {
    private val interructer = Interructer(context)
    fun loadEvents(){
        interructer.getEvents()
                .map { value -> Convector.toEventsPresentList(value) }
                .subscribe(
                { _ -> },
                { _ -> }
        )

    }

    fun openChooseEvents() =
            context.startActivity(LoadEventsActivity.createIntentToChooseEvents(context))
}