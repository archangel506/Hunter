package ru.nsk.dsushko.hunter.presentation.presenters

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import ru.nsk.dsushko.hunter.R
import ru.nsk.dsushko.hunter.presentation.models.EventInfo
import ru.nsk.dsushko.hunter.presentation.views.LoadEventActivity


class LoadEventsPresenter(private val activity: Activity) {
    private val progress = activity.findViewById<ProgressBar>(R.id.load_events_progress_bar)
    private val buttonLoader = activity.findViewById<Button>(R.id.load_events_button)
    private val moveChoose = activity.findViewById<Button>(R.id.moving_choose_events_button)
    private val resendAnkets = activity.findViewById<Button>(R.id.resend_ankets_button)
    private var events = listOf<EventInfo>()
    private var countAnkets = 0

    fun statusResender(count: Int){
        countAnkets = count
        if(count == 0) {
            resendAnkets.visibility = View.GONE
        } else{
            resendAnkets.visibility = View.VISIBLE
            resendAnkets.text = String.format(
                    activity.resources.getString(R.string.resend_ankets),
                    count
            )
        }


    }

    fun loadEvents(){
        if(progress.visibility == View.VISIBLE) return
        buttonLoader.visibility = View.GONE
        moveChoose.visibility = View.GONE
        resendAnkets.visibility = View.GONE
        progress.visibility = View.VISIBLE

        activity.startService(
                LoadEventActivity.createIntentLoaderEventsService(activity)
        )
    }

    fun loadActualEvents(){
        if(progress.visibility == View.VISIBLE) return
        buttonLoader.visibility = View.GONE
        moveChoose.visibility = View.GONE
        resendAnkets.visibility = View.GONE
        progress.visibility = View.VISIBLE

        activity.startService(
                LoadEventActivity.createIntentLoaderActualEventsService(activity)
        )
    }

    fun endLoading(events: List<EventInfo>){
        this.events = events
        buttonLoader.visibility = View.VISIBLE
        progress.visibility = View.GONE
        moveChoose.visibility = View.VISIBLE
        statusResender(countAnkets)
    }

    fun resendAnkets() {
        resendAnkets.visibility = View.INVISIBLE
        activity.startService(LoadEventActivity.createIntentResendAnkets(activity))
    }


    fun openChooseEvents() =
            activity.startActivity(LoadEventActivity.createIntentToChooseEvents(activity, events))


}