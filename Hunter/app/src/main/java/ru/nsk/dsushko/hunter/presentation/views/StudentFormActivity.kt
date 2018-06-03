package ru.nsk.dsushko.hunter.presentation.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import ru.nsk.dsushko.hunter.R
import ru.nsk.dsushko.hunter.domain.Interrupter
import ru.nsk.dsushko.hunter.presentation.models.EventInfo
import ru.nsk.dsushko.hunter.presentation.presenters.FormPresenter
import ru.nsk.dsushko.hunter.presentation.presenters.StudentFormPresenter
import ru.nsk.dsushko.hunter.presentation.receivers.TechnologiesBroadcastReceiver
import ru.nsk.dsushko.hunter.presentation.receivers.WorkFieldsBroadcastReceiver


class StudentFormActivity : AppCompatActivity() {
    private lateinit var presenter: StudentFormPresenter
    private lateinit var broadcastWorkFields: WorkFieldsBroadcastReceiver
    private lateinit var broadcastTechnology: TechnologiesBroadcastReceiver
    private lateinit var events: List<EventInfo>
    private lateinit var interrupter: Interrupter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_form)
        val toolbar = findViewById<Toolbar>(R.id.form_toolbar)
        setSupportActionBar(toolbar)
        interrupter = Interrupter(this)
        presenter = StudentFormPresenter(
                interrupter,
                ChooseEventActivity.getPositionSelectedEvent(intent),
                this)
        findViewById<Button>(R.id.send_form).setOnClickListener{
            presenter.sendForm()
        }
        events = ChooseEventActivity.getEvents(intent)
        presenter.updateEvents(events)

        startService(StandartFormActivity.createIntentLoadTechologies(this))
        startService(StandartFormActivity.createIntentLoadWorkFields(this))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if(savedInstanceState != null){
            presenter.loadState(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if(outState != null){
            presenter.saveState(outState)
        }
    }

    override fun onResume() {
        super.onResume()
        broadcastTechnology = TechnologiesBroadcastReceiver(presenter)
        broadcastWorkFields = WorkFieldsBroadcastReceiver(presenter)
        registerReceiver(broadcastTechnology, StandartFormActivity.createIntentFilterForTechnologies())
        registerReceiver(broadcastWorkFields, StandartFormActivity.createIntentFilterForWorkFields())
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastTechnology)
        unregisterReceiver(broadcastWorkFields)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.form_menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item == null) {
            return super.onOptionsItemSelected(item)
        }

        if(item.itemId == R.id.action_settings_form){
            presenter.openSettings()
        }

        if(item.itemId == R.id.action_clean_form){
            presenter.clearForm()
        }

        return super.onOptionsItemSelected(item)
    }
}