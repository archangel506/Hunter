package ru.nsk.dsushko.hunter.presentation.services

import android.app.IntentService
import android.content.Intent
import ru.nsk.dsushko.hunter.domain.Interrupter

class ResenderAnketsService: IntentService("ru.nsk.dsushko.hunter.presentation.services.ResenderAnketsService") {
    private val interructer = Interrupter(this)

    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) return
        interructer.resendAnkets()
    }

}