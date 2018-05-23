package ru.nsk.dsushko.hunter.presentation.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import ru.nsk.dsushko.hunter.R
import ru.nsk.dsushko.hunter.presentation.presenters.LoadEventsPresenter

class LoadEventsActivity : AppCompatActivity() {
    private lateinit var presenter: LoadEventsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_events)
        val button = findViewById<Button>(R.id.load_events_button)
        button.setOnClickListener { _ ->
            presenter.loadEvents()
            presenter.openChooseEvents()
        }
    }

    companion object {
        fun createIntentToChooseEvents(context: Context)
                = Intent(context, ChooseEventsActivity::class.java)
    }
}