package ru.nsk.dsushko.hunter.domain

import android.content.Context

class Interructer(context: Context){
    private val repository = FactoryRepository.create(context)
    fun getEvents() = repository.getEvents()
}
