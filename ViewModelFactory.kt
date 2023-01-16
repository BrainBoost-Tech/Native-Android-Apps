package com.zat.lexikey.viewModels

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zat.lexikey.utils.sourse.DataRepository


class ViewModelFactory(var dataRepository: DataRepository, application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    private var application: Application? = null

    init {
        this.application = application
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(AuthViewModel::class.java) ->
                    AuthViewModel(dataRepository, application!!)

                isAssignableFrom(StaticViewModel::class.java) ->
                    StaticViewModel(dataRepository, application!!)

                isAssignableFrom(ProfileViewModel::class.java) ->
                    ProfileViewModel(dataRepository, application!!)

                isAssignableFrom(PartnersViewModel::class.java) ->
                    PartnersViewModel(dataRepository, application!!)

                isAssignableFrom(ClubsViewModel::class.java) ->
                    ClubsViewModel(dataRepository, application!!)

                isAssignableFrom(LanguageViewModel::class.java) ->
                    LanguageViewModel(dataRepository, application!!)

                isAssignableFrom(FlashcardViewModel::class.java) ->
                    FlashcardViewModel(dataRepository, application!!)

                isAssignableFrom(ReminderViewModel::class.java) ->
                    ReminderViewModel(dataRepository, application!!)

                isAssignableFrom(GamesViewModel::class.java) ->
                    GamesViewModel(dataRepository, application!!)

                isAssignableFrom(AchievementsViewModel::class.java) ->
                    AchievementsViewModel(dataRepository, application!!)

                isAssignableFrom(NotificationViewModel::class.java) ->
                    NotificationViewModel(dataRepository, application!!)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T


    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            synchronized(ViewModelFactory::class.java) {
                ViewModelFactory(
                    DataRepository(
                        application.applicationContext
                    ), application
                )
                    .also { INSTANCE = it }
            }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
