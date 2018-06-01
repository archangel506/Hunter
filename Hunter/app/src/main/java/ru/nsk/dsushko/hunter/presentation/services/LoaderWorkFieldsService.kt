package ru.nsk.dsushko.hunter.presentation.services

import android.app.IntentService
import android.content.Intent
import io.reactivex.disposables.Disposable
import ru.nsk.dsushko.hunter.domain.Interrupter
import ru.nsk.dsushko.hunter.presentation.models.Converter
import ru.nsk.dsushko.hunter.presentation.views.StandartFormActivity


class LoaderWorkFieldsService: IntentService("ru.nsk.dsushko.hunter.presentation.services.LoaderWorkFieldsService") {
    private val interrupter = Interrupter(this)
    private lateinit var disposable: Disposable

    override fun onHandleIntent(intent: Intent?) {
        disposable = interrupter.getWorkFields()
                .map { Converter.toWorkFieldsInfoList(it) }
                .subscribe(
                        {
                            workFields ->
                                sendBroadcast(StandartFormActivity
                                        .createIntentToBroadcastWorkFields(workFields))


                        }, { _ -> }
                )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}