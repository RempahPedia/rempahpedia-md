package com.rempahpedia.rempahpedia.di

import android.content.Context
import com.rempahpedia.rempahpedia.data.UserRepository
import com.rempahpedia.rempahpedia.data.pref.UserPreference
import com.rempahpedia.rempahpedia.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}