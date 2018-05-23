package ru.nsk.dsushko.hunter.domain

import android.content.Context
import ru.nsk.dsushko.hunter.data.repository.RepositoryEvents


object FactoryRepository {
    fun create(context: Context) : Repository = RepositoryEvents(context)
}