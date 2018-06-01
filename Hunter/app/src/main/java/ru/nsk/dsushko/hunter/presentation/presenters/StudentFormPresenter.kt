package ru.nsk.dsushko.hunter.presentation.presenters

import android.app.Activity
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.androidbuts.multispinnerfilter.MultiSpinner
import ru.nsk.dsushko.hunter.R
import ru.nsk.dsushko.hunter.domain.Interrupter
import ru.nsk.dsushko.hunter.presentation.models.*
import ru.nsk.dsushko.hunter.presentation.views.StandartFormActivity


class StudentFormPresenter(private val interrupter: Interrupter,
                           private val positionSelectedEvent: Int,
                           private val activity: Activity) : FormPresenter {

    private val interestingWorkFields = activity.findViewById<MultiSpinner>(R.id.interestingWorkfield)
    private val interestingEvents = activity.findViewById<MultiSpinner>(R.id.interestingEvents)
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

    private lateinit var workFieldsInfo: List<WorkFieldsInfo>
    private lateinit var events: List<EventInfo>
    private var selectedWorkFields = BooleanArray(0)
    private var selectedEvents = BooleanArray(0)


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

    private fun clearForm() {
        interestingWorkFields.setSelection(0)
        initWorkFields()
        initEvents()
    }

    override fun updateWorkFields(workFieldsInfo: List<WorkFieldsInfo>) {
        this.workFieldsInfo = workFieldsInfo
        initWorkFields()
    }

    override fun updateTechnologies(technologiesInfo: List<TechnologiesInfo>) {}

    override fun updateEvents(events: List<EventInfo>) {
        this.events = events
        initEvents()
    }

    private fun packAnketa(): StudentAnketaInfo {
        val workFieldsId = mutableListOf<Int>()
        for ((index, chooseWorkField) in selectedWorkFields.withIndex()) {
            if (chooseWorkField) {
                workFieldsId.add(workFieldsInfo[index].id ?: 0)
            }

        }
        val eventsId = mutableListOf<Int>()
        for ((index, chooseTech) in selectedEvents.withIndex()) {
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
        val map = LinkedHashMap<String, Boolean>()
        for (workField in workFieldsInfo) {
            map[workField.title ?: ""] = false
        }

        interestingWorkFields.setItems(map, { array -> selectedWorkFields = array })
    }

    private fun initEvents() {
        val map = LinkedHashMap<String, Boolean>()
        for (event in events) {
            map[event.title ?: ""] = false
        }

        interestingEvents.setItems(map, { array -> selectedEvents = array })
    }
}