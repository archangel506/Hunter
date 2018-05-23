package ru.nsk.dsushko.hunter.presentation.presenters

import android.content.Context
import ru.nsk.dsushko.hunter.presentation.views.FormActivity

class FormPresenter(private val context: Context){

    fun openSettings() =
        context.startActivity(FormActivity.createIntentToSettings(context))

}