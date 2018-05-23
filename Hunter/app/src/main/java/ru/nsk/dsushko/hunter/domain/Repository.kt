package ru.nsk.dsushko.hunter.domain

import io.reactivex.Single
import ru.nsk.dsushko.hunter.domain.models.Event


interface Repository {
    fun getEvents() : Single<List<Event>>
}