package ru.nsk.dsushko.hunter.presentation.services

import android.app.IntentService
import android.content.Intent
import io.reactivex.disposables.Disposable
import ru.nsk.dsushko.hunter.domain.Interrupter
import ru.nsk.dsushko.hunter.presentation.models.Converter
import ru.nsk.dsushko.hunter.presentation.views.StandartFormActivity


class LoaderTechnologyService: IntentService("ru.nsk.dsushko.hunter.presentation.services.LoaderTechnologyService") {
    private val interrupter = Interrupter(this)
    private lateinit var disposable: Disposable

    override fun onHandleIntent(intent: Intent?) {
        disposable = interrupter.getTechnologies()
                .map { Converter.toTechnologiesInfoList(it) }
                .subscribe(
                        {
                            technologies -> sendBroadcast(StandartFormActivity
                                .createIntentToBroadcastTechnologies(technologies))
                        }, {_ -> }
                )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}