package ru.nsk.dsushko.hunter.presentation.services

import android.app.IntentService
import android.content.Intent
import ru.nsk.dsushko.hunter.domain.Interrupter
import ru.nsk.dsushko.hunter.presentation.views.LoadEventActivity

class GetterCountResendAnketsService
    : IntentService("ru.nsk.dsushko.hunter.presentation.services.GetterCountResendAnketsService") {
    private val interrupter = Interrupter(this)

    override fun onHandleIntent(intent: Intent?) {
        if(intent == null) return
        val count = interrupter.getCountAnkets()
        val newIntent = LoadEventActivity.createIntentResponseGetCountResendAnkets(count)
        sendBroadcast(newIntent)
    }

}