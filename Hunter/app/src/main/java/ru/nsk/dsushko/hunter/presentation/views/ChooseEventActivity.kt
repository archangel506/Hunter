package ru.nsk.dsushko.hunter.presentation.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import ru.nsk.dsushko.hunter.R
import ru.nsk.dsushko.hunter.presentation.models.BoxEvents
import ru.nsk.dsushko.hunter.presentation.models.EventInfo
import ru.nsk.dsushko.hunter.presentation.presenters.ChooseEventPresenter

class ChooseEventActivity : AppCompatActivity(){
    private lateinit var presenter: ChooseEventPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_events)
        val toolbar = findViewById<Toolbar>(R.id.choose_toolbar)
        setSupportActionBar(toolbar)
        presenter = ChooseEventPresenter(this)
        val events = LoadEventActivity.getEvents(intent)
        findViewById<RecyclerView>(R.id.recycler_view_events).apply {
            layoutManager = LinearLayoutManager(this@ChooseEventActivity)
            this.adapter = EventAdapter(LayoutInflater.from(this@ChooseEventActivity), presenter, events)
        }
    }


    companion object {
        private const val KEY_EVENT = "ru.nsk.dsushko.hunter.presentation.views.ChooseEventActivity.KEY_EVENT"
        private const val KEY_POSITION = "ru.nsk.dsushko.hunter.presentation.views.ChooseEventActivity.KEY_POSITION"

        fun createIntentToStandartFormEvent(context: Context, events: List<EventInfo>, position: Int) : Intent{
            val intent = Intent(context, StandartFormActivity::class.java)
            intent.putExtra(KEY_EVENT, BoxEvents(events))
            intent.putExtra(KEY_POSITION, position)
            return intent
        }

        fun createIntentToStudentFormEvent(context: Context, events: List<EventInfo>, position: Int) : Intent{
            val intent = Intent(context, StudentFormActivity::class.java)
            intent.putExtra(KEY_EVENT, BoxEvents(events))
            intent.putExtra(KEY_POSITION, position)
            return intent
        }

        fun getEvents(intent : Intent)
                = (intent.getSerializableExtra(KEY_EVENT) as BoxEvents).events


        fun getPositionSelectedEvent(intent : Intent)
                = intent.getIntExtra(KEY_POSITION, 0)

    }


}