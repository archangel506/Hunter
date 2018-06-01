package ru.nsk.dsushko.hunter.presentation.services

import android.app.IntentService
import android.content.Intent
import io.reactivex.disposables.Disposable
import ru.nsk.dsushko.hunter.domain.Interrupter
import ru.nsk.dsushko.hunter.presentation.models.Converter
import ru.nsk.dsushko.hunter.presentation.views.LoadEventActivity


class LoaderEventsService() : IntentService("ru.nsk.dsushko.hunter.presentation.services.LoaderEventsService") {
    private val interrupter = Interrupter(this)
    private lateinit var disposable: Disposable

    override fun onHandleIntent(intent: Intent?) {
        if(intent == null) return

        disposable = interrupter.getEvents()
                .map { value -> Converter.toEventsPresentList(value) }
                .subscribe(
                        { events ->
                            run {
                                sendBroadcast(
                                        LoadEventActivity.createIntentToBroadcastEvents(events)
                                )
                            }
                        },
                        { _ -> }
                )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}