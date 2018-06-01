package ru.nsk.dsushko.hunter.presentation.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.nsk.dsushko.hunter.presentation.presenters.LoadEventsPresenter
import ru.nsk.dsushko.hunter.presentation.views.LoadEventActivity

class CountResendAnketsBroadcastReceiver(private val presenter: LoadEventsPresenter): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent == null) return
        val count = LoadEventActivity.getCountResendAnkets(intent)
        presenter.statusResender(count)
    }
}