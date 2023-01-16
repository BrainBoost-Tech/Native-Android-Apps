package com.zat.lexikey.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zat.lexikey.models.ResultWrapper
import com.zat.lexikey.models.SimpleMessageModel
import com.zat.lexikey.models.allInterestsModel.AllInterestsMainModel
import com.zat.lexikey.models.authModels.loginModel.LoginMainModel
import com.zat.lexikey.models.languageModels.LanguagesMainModel
import com.zat.lexikey.models.profileModels.contactUsModel.ContactUsMainModel
import com.zat.lexikey.models.profileModels.profileModel.ProfileMainModel
import com.zat.lexikey.models.requestModels.ProfileCompletionRequestModel
import com.zat.lexikey.models.requestModels.ProfileUpdateRequestModel
import com.zat.lexikey.models.requestModels.UpdateInterestsRequestModel
import com.zat.lexikey.utils.sourse.DataRepository
import kotlinx.coroutines.launch

class ProfileViewModel(var dataRepository: DataRepository, application: Application) :
    BaseViewModel(application) {
    var contactUsLiveData = MutableLiveData<ContactUsMainModel>()
    var profileLiveData = MutableLiveData<LoginMainModel>()
    var myProfileLiveData = MutableLiveData<LoginMainModel>()
    var languagesLiveData = MutableLiveData<LanguagesMainModel>()
    var addInterestLiveData = MutableLiveData<SimpleMessageModel>()
    var allInterestsLiveData = MutableLiveData<AllInterestsMainModel>()


    fun contactUs(email: String, title: String, description: String) {
        viewModelScope.launch {
            when (val apiResponse =
                dataRepository.contactUs(email, title, description)) {
                is ResultWrapper.NetworkError -> errorLiveData.postValue("Network Error")
                is ResultWrapper.GenericError -> errorLiveData.postValue(apiResponse.error)
                is ResultWrapper.Success -> {
                    if (apiResponse.value.code() == 401) {
                        errorLiveData.postValue("Unauthenticated User")
                    } else if (apiResponse.value.code() == 500) {
                        errorLiveData.postValue(null)
                    } else {
                        if (apiResponse.value.body()?.status != 200) {
                            errorLiveData.postValue(apiResponse.value.body()?.message)
                        } else {
                            contactUsLiveData.postValue(apiResponse.value.body())
                        }
                    }
                }
            }
        }
    }

    fun myProfile() {
        viewModelScope.launch {

            when (val apiResponse =
                dataRepository.myProfile()) {
                is ResultWrapper.NetworkError -> errorLiveData.postValue("Network Error")
                is ResultWrapper.GenericError -> errorLiveData.postValue(apiResponse.error)
                is ResultWrapper.Success -> {
                    if (apiResponse.value.code() == 401) {
                        errorLiveData.postValue("Unauthenticated User")
                    } else if (apiResponse.value.code() == 500) {
                        errorLiveData.postValue(null)
                    } else {
                        if (apiResponse.value.body()?.status == 200) {
                            myProfileLiveData.postValue(apiResponse.value.body())
                        } else {
                            errorLiveData.postValue(apiResponse.value.body()?.message)
                        }
                    }
                }
            }
        }
    }

    fun profileCompletion(profileCompletionRequestModel: ProfileCompletionRequestModel) {
        viewModelScope.launch {

            when (val apiResponse =
                dataRepository.profileCompletion(profileCompletionRequestModel)) {
                is ResultWrapper.NetworkError -> errorLiveData.postValue("Network Error")
                is ResultWrapper.GenericError -> errorLiveData.postValue(apiResponse.error)
                is ResultWrapper.Success -> {
                    if (apiResponse.value.code() == 401) {
                        errorLiveData.postValue("Unauthenticated User")
                    } else if (apiResponse.value.code() == 500) {
                        errorLiveData.postValue(null)
                    } else {
                        if (apiResponse.value.body()?.status == 200) {
                            profileLiveData.postValue(apiResponse.value.body())
                        } else {
                            errorLiveData.postValue(apiResponse.value.body()?.message)
                        }
                    }
                }
            }
        }
    }

    fun profileUpdate(profileUpdateRequestModel: ProfileUpdateRequestModel) {
        viewModelScope.launch {

            when (val apiResponse =
                dataRepository.profileUpdate(profileUpdateRequestModel)) {
                is ResultWrapper.NetworkError -> errorLiveData.postValue("Network Error")
                is ResultWrapper.GenericError -> errorLiveData.postValue(apiResponse.error)
                is ResultWrapper.Success -> {
                    if (apiResponse.value.code() == 401) {
                        errorLiveData.postValue("Unauthenticated User")
                    } else if (apiResponse.value.code() == 500) {
                        errorLiveData.postValue(null)
                    } else {
                        if (apiResponse.value.body()?.status == 200) {
                            profileLiveData.postValue(apiResponse.value.body())
                        } else {
                            errorLiveData.postValue(apiResponse.value.body()?.message)
                        }
                    }
                }
            }
        }
    }

    fun updateInterests(updateInterestsRequestModel: UpdateInterestsRequestModel) {
        viewModelScope.launch {

            when (val apiResponse =
                dataRepository.updateInterests(updateInterestsRequestModel)) {
                is ResultWrapper.NetworkError -> errorLiveData.postValue("Network Error")
                is ResultWrapper.GenericError -> errorLiveData.postValue(apiResponse.error)
                is ResultWrapper.Success -> {
                    if (apiResponse.value.code() == 401) {
                        errorLiveData.postValue("Unauthenticated User")
                    } else if (apiResponse.value.code() == 500) {
                        errorLiveData.postValue(null)
                    } else {
                        if (apiResponse.value.body()?.status == 200) {
                            profileLiveData.postValue(apiResponse.value.body())
                        } else {
                            errorLiveData.postValue(apiResponse.value.body()?.message)
                        }
                    }
                }
            }
        }
    }

    fun languages() {
        viewModelScope.launch {

            when (val apiResponse =
                dataRepository.languages()) {
                is ResultWrapper.NetworkError -> errorLiveData.postValue("Network Error")
                is ResultWrapper.GenericError -> errorLiveData.postValue(apiResponse.error)
                is ResultWrapper.Success -> {
                    if (apiResponse.value.code() == 401) {
                        errorLiveData.postValue("Unauthenticated User")
                    } else if (apiResponse.value.code() == 500) {
                        errorLiveData.postValue(null)
                    } else {
                        if (apiResponse.value.body()?.status == 200) {
                            languagesLiveData.postValue(apiResponse.value.body())
                        } else {
                            errorLiveData.postValue(apiResponse.value.body()?.message)
                        }
                    }
                }
            }
        }
    }

    fun addInterest(interest: String) {
        viewModelScope.launch {

            when (val apiResponse =
                dataRepository.addInterest(interest)) {
                is ResultWrapper.NetworkError -> errorLiveData.postValue("Network Error")
                is ResultWrapper.GenericError -> errorLiveData.postValue(apiResponse.error)
                is ResultWrapper.Success -> {
                    if (apiResponse.value.code() == 401) {
                        errorLiveData.postValue("Unauthenticated User")
                    } else if (apiResponse.value.code() == 500) {
                        errorLiveData.postValue(null)
                    } else {
                        if (apiResponse.value.body()?.status == 200) {
                            addInterestLiveData.postValue(apiResponse.value.body())
                        } else {
                            errorLiveData.postValue(apiResponse.value.body()?.message)
                        }
                    }
                }
            }
        }
    }

    fun allInterests() {
        viewModelScope.launch {

            when (val apiResponse =
                dataRepository.allInterests()) {
                is ResultWrapper.NetworkError -> errorLiveData.postValue("Network Error")
                is ResultWrapper.GenericError -> errorLiveData.postValue(apiResponse.error)
                is ResultWrapper.Success -> {
                    if (apiResponse.value.code() == 401) {
                        errorLiveData.postValue("Unauthenticated User")
                    } else if (apiResponse.value.code() == 500) {
                        errorLiveData.postValue(null)
                    } else {
                        if (apiResponse.value.body()?.status == 200) {
                            allInterestsLiveData.postValue(apiResponse.value.body())
                        } else {
                            errorLiveData.postValue(apiResponse.value.body()?.message)
                        }
                    }
                }
            }
        }
    }

}