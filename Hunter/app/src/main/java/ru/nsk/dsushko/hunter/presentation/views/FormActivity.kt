package ru.nsk.dsushko.hunter.presentation.views

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import ru.nsk.dsushko.hunter.R
import ru.nsk.dsushko.hunter.presentation.presenters.FormPresenter

class FormActivity : AppCompatActivity() {
    private lateinit var presenter: FormPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        val toolbar = findViewById<Toolbar>(R.id.form_toolbar)
        setSupportActionBar(toolbar)
        presenter = FormPresenter(this)
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

        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun createIntentToSettings(context: Context)
                = Intent(context, LoadEventsActivity::class.java)
    }




}
