package ru.nsk.dsushko.hunter.presentation.presenters

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import ru.nsk.dsushko.hunter.R
import ru.nsk.dsushko.hunter.domain.Interrupter
import ru.nsk.dsushko.hunter.presentation.models.*
import ru.nsk.dsushko.hunter.presentation.views.StandartFormActivity


class StudentFormPresenter(private val interrupter: Interrupter,
                           private val positionSelectedEvent: Int,
                           private val activity: Activity) : FormPresenter {

    private val interestingWorkFields = activity.findViewById<Button>(R.id.interestingWorkfield)
    private val interestingEvents = activity.findViewById<Button>(R.id.interesting_events_button)
    private val name = activity.findViewById<EditText>(R.id.name_edittext)
    private val phone = activity.findViewById<EditText>(R.id.phone_edittext)
    private val email = activity.findViewById<EditText>(R.id.email_candidate)
    private val city = activity.findViewById<EditText>(R.id.city_residence)
    private val school = activity.findViewById<EditText>(R.id.school_edittext)
    private val faculty = activity.findViewById<EditText>(R.id.faculty_edittext)
    private val speciality = activity.findViewById<EditText>(R.id.speciality_edittext)
    private val graduate = activity.findViewById<EditText>(R.id.graduate_year_edittext)
    private val comment = activity.findViewById<EditText>(R.id.comment)
    private val subscribeNews = activity.findViewById<CheckBox>(R.id.subscribe_news)
    private val agree = activity.findViewById<CheckBox>(R.id.agree_personal_data)

    private lateinit var workFieldsChooser : MultiChooser
    private lateinit var eventsChooser : MultiChooser
    private lateinit var workFieldsInfo: List<WorkFieldsInfo>
    private lateinit var events: List<EventInfo>

    private var savedSelectedEvents : BooleanArray? = null
    private var savedSelectedWorkFields : BooleanArray? = null

    init{
        interestingWorkFields.setOnClickListener({
            workFieldsChooser.chooseItems()
        })
        interestingEvents.setOnClickListener({
            eventsChooser.chooseItems()
        })
    }

    override fun openSettings() =
            activity.startActivity(StandartFormActivity.createIntentToSettings(activity))

    override fun sendForm() {
        if (!agree.isChecked) {
            Toast.makeText(activity, R.string.need_agree, Toast.LENGTH_SHORT)
                    .show()
            return
        }

        val anketa = packAnketa()
        interrupter.sendAnketa(Converter.toStudentAnketa(anketa))
        clearForm()
        Toast.makeText(activity, R.string.success_send, Toast.LENGTH_SHORT)
                .show()
    }

    override fun updateWorkFields(workFieldsInfo: List<WorkFieldsInfo>) {
        this.workFieldsInfo = workFieldsInfo
        initWorkFields()
        if(savedSelectedWorkFields != null){
            workFieldsChooser.selectedItems = savedSelectedWorkFields?.clone() ?: BooleanArray(workFieldsInfo.size)
        }
    }

    override fun updateTechnologies(technologiesInfo: List<TechnologiesInfo>) {}

    override fun updateEvents(events: List<EventInfo>) {
        this.events = events
        initEventsChooser()
        if(savedSelectedEvents != null){
            eventsChooser.selectedItems = savedSelectedEvents?.clone() ?: BooleanArray(events.size)
        }
    }

    fun saveState(outState: Bundle){
        outState.putBooleanArray(KEY_EVENTS_CHOOSE, eventsChooser.selectedItems)
        outState.putBooleanArray(KEY_WORK_FIELDS_CHOOSE, workFieldsChooser.selectedItems)
    }

    fun loadState(savedInstanceState: Bundle){
        savedSelectedEvents = savedInstanceState.getBooleanArray(KEY_EVENTS_CHOOSE)
        savedSelectedWorkFields = savedInstanceState.getBooleanArray(KEY_WORK_FIELDS_CHOOSE)
    }

    private fun clearForm() {
        initWorkFields()
        initEventsChooser()
    }

    private fun packAnketa(): StudentAnketaInfo {
        val workFieldsId = mutableListOf<Int>()
        for ((index, chooseWorkField) in workFieldsChooser.selectedItems.withIndex()) {
            if (chooseWorkField) {
                workFieldsId.add(workFieldsInfo[index].id ?: 0)
            }

        }
        val eventsId = mutableListOf<Int>()
        for ((index, chooseTech) in eventsChooser.selectedItems.withIndex()) {
            if (chooseTech) {
                eventsId.add(events[index].id ?: 0)
            }

        }

        return StudentAnketaInfo(
                school.text.toString(),
                faculty.text.toString(),
                speciality.text.toString(),
                graduate.text.toString(),
                workFieldsId,
                eventsId,
                comment.text.toString(),
                events[positionSelectedEvent].id,
                city.text.toString(),
                subscribeNews.isChecked,
                name.text.toString(),
                phone.text.toString(),
                email.text.toString(),
                agree.isChecked
        )
    }

    private fun initWorkFields() {
        val titles = getTitlesWorkFields()

        workFieldsChooser = MultiChooser(activity,
                activity.resources.getString(R.string.technologies_canditate), titles)
    }

    private fun initEventsChooser() {
        val titles = getTitlesEvents()
        eventsChooser = MultiChooser(activity,
                activity.resources.getString(R.string.interesting_events), titles)
    }

    @Suppress("DEPRECATION")
    private fun getTitlesEvents() : Array<String?>{
        val results = mutableListOf<String?>()
        for(event in events){
            val title =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        Html.fromHtml(event.title ?: "", Html.FROM_HTML_MODE_LEGACY)
                    else
                        Html.fromHtml(event.title ?: "")
            results.add(title.toString())
        }
        return results.toTypedArray()
    }

    private fun getTitlesWorkFields() : Array<String?>{
        val results = mutableListOf<String?>()
        for(workField in workFieldsInfo){
            results.add(workField.title)
        }
        return results.toTypedArray()
    }

    private companion object {
        private const val KEY_WORK_FIELDS_CHOOSE = "ru.nsk.dsushko.hunter.presentation.presenters.StudentFormPresenter.KEY_WORK_FIELDS"
        private const val KEY_EVENTS_CHOOSE = "ru.nsk.dsushko.hunter.presentation.presenters.StudentFormPresenter.KEY_EVENTS"
    }
}