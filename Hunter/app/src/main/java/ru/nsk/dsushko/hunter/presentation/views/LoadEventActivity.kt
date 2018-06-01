package ru.nsk.dsushko.hunter.presentation.views

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import ru.nsk.dsushko.hunter.R
import ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.EventsDatabase
import ru.nsk.dsushko.hunter.presentation.receivers.CountResendAnketsBroadcastReceiver
import ru.nsk.dsushko.hunter.presentation.receivers.EventsBroadcastReceiver
import ru.nsk.dsushko.hunter.presentation.services.LoaderEventsService
import ru.nsk.dsushko.hunter.presentation.models.BoxEvents
import ru.nsk.dsushko.hunter.presentation.models.EventInfo
import ru.nsk.dsushko.hunter.presentation.presenters.LoadEventsPresenter
import ru.nsk.dsushko.hunter.presentation.services.GetterCountResendAnketsService
import ru.nsk.dsushko.hunter.presentation.services.LoaderActualEventsService
import ru.nsk.dsushko.hunter.presentation.services.ResenderAnketsService

class LoadEventActivity : AppCompatActivity() {
    private lateinit var presenter: LoadEventsPresenter
    private lateinit var eventsBroadcastReceiver: EventsBroadcastReceiver
    private lateinit var countResendAnketsBroadcastReceiver: CountResendAnketsBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_events)
        val toolbar = findViewById<Toolbar>(R.id.load_events_toolbar)
        setSupportActionBar(toolbar)
        EventsDatabase.getInstance(this)
        val buttonLoader = findViewById<Button>(R.id.load_events_button)
        presenter = LoadEventsPresenter(this)
        buttonLoader.setOnClickListener { _ ->
            presenter.loadActualEvents()
        }
        val moveChoose = findViewById<Button>(R.id.moving_choose_events_button)
        moveChoose.setOnClickListener { _ ->
            presenter.openChooseEvents()
        }
        val resendAnkets = findViewById<Button>(R.id.resend_ankets_button)
        resendAnkets.setOnClickListener { _ ->
            presenter.resendAnkets()
        }
        
        startService(LoadEventActivity.createIntentRequestGetCountResendAnkets(this))
        presenter.loadEvents()
    }

    override fun onResume() {
        super.onResume()
        eventsBroadcastReceiver = EventsBroadcastReceiver(presenter)
        countResendAnketsBroadcastReceiver = CountResendAnketsBroadcastReceiver(presenter)
        registerReceiver(eventsBroadcastReceiver, IntentFilter(KEY_BROADCAST_EVENTS))
        registerReceiver(countResendAnketsBroadcastReceiver, IntentFilter(KEY_BROADCAST_COUNT_ANKETS))

    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(eventsBroadcastReceiver)
        unregisterReceiver(countResendAnketsBroadcastReceiver)
    }

    companion object {
        private const val KEY_EVENTS = "ru.nsk.dsushko.hunter.presentation.views.LoadEventActivity.KEY_EVENTS"
        private const val KEY_BROADCAST_EVENTS = "ru.nsk.dsushko.hunter.presentation.views.LoadEventActivity.KEY_BROADCAST_EVENTS"
        private const val KEY_COUNT_ANKETS = "ru.nsk.dsushko.hunter.presentation.views.LoadEventActivity.KEY_COUNT_ANKETS"
        private const val KEY_BROADCAST_COUNT_ANKETS = "ru.nsk.dsushko.hunter.presentation.views.LoadEventActivity.KEY_BROADCAST_COUNT_ANKETS"

        private const val DEFAULT_VALUE = 0

        fun createIntentToChooseEvents(context: Context, events: List<EventInfo>): Intent{
            val intent = Intent(context, ChooseEventActivity::class.java)
            intent.putExtra(KEY_EVENTS, BoxEvents(events))
            return intent
        }

        fun createIntentToBroadcastEvents(events: List<EventInfo>): Intent{
            val intent = Intent(KEY_BROADCAST_EVENTS)
            intent.putExtra(KEY_EVENTS, BoxEvents(events))
            return intent
        }

        fun createIntentRequestGetCountResendAnkets(context: Context)
                = Intent(context, GetterCountResendAnketsService::class.java)

        fun createIntentResponseGetCountResendAnkets(count: Int) : Intent{
            val intent = Intent(KEY_BROADCAST_COUNT_ANKETS)
            intent.putExtra(KEY_COUNT_ANKETS, count)
            return intent
        }

        fun createIntentResendAnkets(context: Context)
                = Intent(context, ResenderAnketsService::class.java)

        fun getCountResendAnkets(intent: Intent) = intent.getIntExtra(KEY_COUNT_ANKETS, DEFAULT_VALUE)

        fun getEvents(intent: Intent) : List<EventInfo>{
            val boxEvents = intent.getSerializableExtra(KEY_EVENTS)
            return if(boxEvents is BoxEvents)
                boxEvents.events
            else listOf()
        }

        fun createIntentLoaderEventsService(context: Context)
                = Intent(context, LoaderEventsService::class.java)

        fun createIntentLoaderActualEventsService(context: Context)
                = Intent(context, LoaderActualEventsService::class.java)
    }
}