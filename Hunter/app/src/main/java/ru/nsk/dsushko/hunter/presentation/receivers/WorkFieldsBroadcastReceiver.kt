package ru.nsk.dsushko.hunter.presentation.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.nsk.dsushko.hunter.presentation.presenters.FormPresenter
import ru.nsk.dsushko.hunter.presentation.views.StandartFormActivity


class WorkFieldsBroadcastReceiver(private val presenter: FormPresenter): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent == null) return
        val workFields = StandartFormActivity.getWorkFields(intent)
        presenter.updateWorkFields(workFields)
    }
}