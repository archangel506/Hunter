package ru.nsk.dsushko.hunter.presentation.views

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import ru.nsk.dsushko.hunter.R
import ru.nsk.dsushko.hunter.domain.Interrupter
import ru.nsk.dsushko.hunter.presentation.models.*
import ru.nsk.dsushko.hunter.presentation.presenters.FormPresenter
import ru.nsk.dsushko.hunter.presentation.presenters.StandartFormPresenter
import ru.nsk.dsushko.hunter.presentation.receivers.TechnologiesBroadcastReceiver
import ru.nsk.dsushko.hunter.presentation.receivers.WorkFieldsBroadcastReceiver
import ru.nsk.dsushko.hunter.presentation.services.LoaderTechnologyService
import ru.nsk.dsushko.hunter.presentation.services.LoaderWorkFieldsService

class StandartFormActivity : AppCompatActivity() {
    private lateinit var presenter: StandartFormPresenter
    private lateinit var broadcastWorkFields: WorkFieldsBroadcastReceiver
    private lateinit var broadcastTechnology: TechnologiesBroadcastReceiver
    private lateinit var events: List<EventInfo>
    private lateinit var interrupter: Interrupter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standart_form)
        val toolbar = findViewById<Toolbar>(R.id.form_toolbar)
        setSupportActionBar(toolbar)
        interrupter = Interrupter(this)
        presenter = StandartFormPresenter(
                interrupter,
                ChooseEventActivity.getPositionSelectedEvent(intent),
                this
        )
        findViewById<Button>(R.id.send_form).setOnClickListener {
            presenter.sendForm()
        }

        events = ChooseEventActivity.getEvents(intent)
        presenter.updateEvents(events)
        startService(createIntentLoadTechologies(this))
        startService(createIntentLoadWorkFields(this))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.form_menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) {
            return super.onOptionsItemSelected(item)
        }

        if (item.itemId == R.id.action_settings_form) {
            presenter.openSettings()
        }

        if(item.itemId == R.id.action_clean_form){
            presenter.clearForm()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if(savedInstanceState != null){
            presenter.loadState(savedInstanceState)
        }
    }

    override fun onResume() {
        super.onResume()
        broadcastTechnology = TechnologiesBroadcastReceiver(presenter)
        broadcastWorkFields = WorkFieldsBroadcastReceiver(presenter)
        registerReceiver(broadcastTechnology, createIntentFilterForTechnologies())
        registerReceiver(broadcastWorkFields, createIntentFilterForWorkFields())
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if(outState != null){
            presenter.saveState(outState)
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastTechnology)
        unregisterReceiver(broadcastWorkFields)
    }


    companion object {
        private const val KEY_WORKFIELDS = "ru.nsk.dsushko.hunter.presentation.views.StandartFormActivity.KEY_WORKFIELDS"
        private const val KEY_TECHNOLOGIES = "ru.nsk.dsushko.hunter.presentation.views.StandartFormActivity.KEY_TECHNOLOGIES"
        private const val KEY_BROADCAST_TECHNOLOGIES = "ru.nsk.dsushko.hunter.presentation.views.StandartFormActivity.KEY_BROADCAST_TECHNOLOGIES"
        private const val KEY_BROADCAST_WORKFIELDS = "ru.nsk.dsushko.hunter.presentation.views.StandartFormActivity.KEY_BROADCAST_WORKFIELDS"

        fun createIntentToSettings(context: Context): Intent {
            val intent = Intent(context, LoadEventActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }

        fun createIntentLoadWorkFields(context: Context) = Intent(context, LoaderWorkFieldsService::class.java)

        fun createIntentLoadTechologies(context: Context) = Intent(context, LoaderTechnologyService::class.java)

        fun createIntentToBroadcastTechnologies(technologiesInfo: List<TechnologiesInfo>): Intent {
            val intent = Intent(KEY_BROADCAST_TECHNOLOGIES)
            intent.putExtra(KEY_TECHNOLOGIES, BoxTechnologies(technologiesInfo))
            return intent
        }

        fun createIntentToBroadcastWorkFields(workFieldsInfo: List<WorkFieldsInfo>): Intent {
            val intent = Intent(KEY_BROADCAST_WORKFIELDS)
            intent.putExtra(KEY_WORKFIELDS, BoxWorkFields(workFieldsInfo))
            return intent
        }

        fun getTechnologies(intent: Intent) = (intent.getSerializableExtra(KEY_TECHNOLOGIES) as BoxTechnologies)
                .technologies

        fun getWorkFields(intent: Intent) = (intent.getSerializableExtra(KEY_WORKFIELDS) as BoxWorkFields)
                .workFieldsInfo

        fun createIntentFilterForWorkFields() = IntentFilter(KEY_BROADCAST_WORKFIELDS)

        fun createIntentFilterForTechnologies() = IntentFilter(KEY_BROADCAST_TECHNOLOGIES)
    }
}
