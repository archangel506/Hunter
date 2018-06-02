package ru.nsk.dsushko.hunter.presentation.presenters

import android.app.Activity
import ru.nsk.dsushko.hunter.presentation.models.EventInfo
import ru.nsk.dsushko.hunter.presentation.views.ChooseEventActivity

class ChooseEventPresenter(private val activity: Activity) {
    fun moveFormEvent(events: List<EventInfo>, position: Int){
        val intent =
                if(events[position].eventFormatEng == STUDENT_TYPE_ANKETA)
                    ChooseEventActivity.createIntentToStudentFormEvent(activity, events, position)
                else ChooseEventActivity.createIntentToStandartFormEvent(activity, events, position)
        activity.startActivity(intent)
    }

    private companion object {
        private const val STUDENT_TYPE_ANKETA = "student"
    }
}