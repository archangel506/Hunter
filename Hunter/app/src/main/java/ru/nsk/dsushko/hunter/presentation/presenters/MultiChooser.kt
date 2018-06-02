package ru.nsk.dsushko.hunter.presentation.presenters

import android.app.AlertDialog
import android.content.Context
import ru.nsk.dsushko.hunter.R

class MultiChooser(private val context: Context,
                   private val title: String,
                   private val values: Array<String?>) {

    constructor(context: Context, title: String, values: Array<String?>, initCheckedItems: BooleanArray)
            : this(context, title, values){
        this.selectedItems = initCheckedItems.clone()
    }

    var selectedItems = BooleanArray(values.size)

    fun chooseItems(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMultiChoiceItems(values, selectedItems,
                {
                    dialogInterface, position, isChecked ->
                    selectedItems[position] = isChecked
                }
        )
        builder.setPositiveButton(R.string.close, {
            dialog, _ ->  dialog.dismiss()
        })
        builder.create().show()
    }
}