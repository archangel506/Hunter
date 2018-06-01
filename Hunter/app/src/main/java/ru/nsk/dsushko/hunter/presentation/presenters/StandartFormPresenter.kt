package ru.nsk.dsushko.hunter.presentation.presenters

import android.app.Activity
import android.os.Build
import android.text.Html
import android.widget.*
import com.androidbuts.multispinnerfilter.MultiSpinner
import ru.nsk.dsushko.hunter.R
import ru.nsk.dsushko.hunter.domain.Interrupter
import ru.nsk.dsushko.hunter.presentation.models.*
import ru.nsk.dsushko.hunter.presentation.views.StandartFormActivity

class StandartFormPresenter(private val interrupter: Interrupter,
                            private val positionSelectedEvent: Int,
                            private val activity: Activity) : FormPresenter{

    private val name = activity.findViewById<EditText>(R.id.name_edittext)
    private val phone = activity.findViewById<EditText>(R.id.phone_edittext)
    private val email = activity.findViewById<EditText>(R.id.email_candidate)
    private val cityResidence = activity.findViewById<EditText>(R.id.city_residence)
    private val companyCandidate = activity.findViewById<EditText>(R.id.company_candidate)
    private val position = activity.findViewById<EditText>(R.id.position)
    private val likedReport = activity.findViewById<EditText>(R.id.liked_report)
    private val interestingWorkFields = activity.findViewById<Spinner>(R.id.interestingWorkfield)
    private val technologies = activity.findViewById<MultiSpinner>(R.id.technologies_spinner)
    private val anotherTechnologies = activity.findViewById<EditText>(R.id.another_technologies)
    private val interestingEvents = activity.findViewById<MultiSpinner>(R.id.interestingEvents)
    private val suggestion = activity.findViewById<EditText>(R.id.suggestions_and_сomments)
    private val subscribeNews = activity.findViewById<CheckBox>(R.id.subscribe_news)
    private val checkSpeaker = activity.findViewById<CheckBox>(R.id.check_speaker)
    private val agree = activity.findViewById<CheckBox>(R.id.agree_personal_data)

    private lateinit var workFieldsInfo: List<WorkFieldsInfo>
    private lateinit var technologiesInfo: List<TechnologiesInfo>
    private lateinit var events: List<EventInfo>
    private var selectedTech = BooleanArray(0)
    private var selectedEvents = BooleanArray(0)

    override fun openSettings() =
        activity.startActivity(StandartFormActivity.createIntentToSettings(activity))

    override fun sendForm(){
        if(!agree.isChecked){
            Toast.makeText(activity, R.string.need_agree, Toast.LENGTH_SHORT)
                    .show()
            return
        }
        val anketa = packAnketa()
        interrupter.sendAnketa(Converter.toStandartAnketa(anketa))
        clearForm()
        Toast.makeText(activity, R.string.success_send, Toast.LENGTH_SHORT)
                .show()
    }

    override fun updateWorkFields(workFieldsInfo: List<WorkFieldsInfo>) {
        this.workFieldsInfo = workFieldsInfo
        val titles = mutableListOf<String>()
        for(workField in workFieldsInfo){
            titles.add(workField.title ?: "")
        }
        val adapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, titles.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        interestingWorkFields.adapter = adapter
        interestingWorkFields.setSelection(0)
    }

    override fun updateTechnologies(technologiesInfo: List<TechnologiesInfo>) {
        this.technologiesInfo = technologiesInfo
        initTechnologiesSpinner()
    }

    override fun updateEvents(events: List<EventInfo>) {
        this.events = events
        initEventsSpinner()
    }

    private fun packAnketa() : StandartAnketaInfo {
        val workFieldId = workFieldsInfo[interestingWorkFields.selectedItemPosition].id
        val techsId = mutableListOf<Int>()
        for((index, chooseTech) in selectedTech.withIndex()){
            if(chooseTech){
                techsId.add(technologiesInfo[index].id ?: 0)
            }

        }
        val eventsId = mutableListOf<Int>()
        for((index, chooseTech) in selectedEvents.withIndex()){
            if(chooseTech){
                eventsId.add(events[index].id ?: 0)
            }

        }

        return StandartAnketaInfo(
                companyCandidate.text.toString(),
                position.text.toString(),
                workFieldId,
                likedReport.text.toString(),
                suggestion.text.toString(),
                techsId,
                anotherTechnologies.text.toString(),
                eventsId,
                checkSpeaker.isChecked,
                events[positionSelectedEvent].id,
                cityResidence.text.toString(),
                subscribeNews.isChecked,
                name.text.toString(),
                phone.text.toString(),
                email.text.toString(),
                agree.isChecked
        )
    }

    private fun clearForm(){
        interestingWorkFields.setSelection(0)
        initTechnologiesSpinner()
        initEventsSpinner()
    }

    private fun initTechnologiesSpinner(){
        val map = LinkedHashMap<String, Boolean>()
        for(technology in technologiesInfo){
            map[technology.title ?: ""] = false
        }

        technologies.setItems(map,{ array -> selectedTech = array})
    }

    @Suppress("DEPRECATION")
    private fun initEventsSpinner(){
        val map = LinkedHashMap<String, Boolean>()
        for(event in events){
            val title =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        Html.fromHtml(event.title ?: "", Html.FROM_HTML_MODE_LEGACY)
                    else
                        Html.fromHtml(event.title ?: "")

            map[title.toString()] = false
        }

        interestingEvents.setItems(map, { array -> selectedEvents = array})
    }

}