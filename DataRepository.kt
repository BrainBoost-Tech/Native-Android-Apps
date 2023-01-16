package com.zat.lexikey.utils.sourse

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.zat.lexikey.models.ReminderMainModel
import com.zat.lexikey.models.ResultWrapper
import com.zat.lexikey.models.SimpleMessageModel
import com.zat.lexikey.models.authModels.loginModel.LoginMainModel
import com.zat.lexikey.models.authModels.registerModel.RegisterMainModel
import com.zat.lexikey.models.chatModels.saveMedialModel.SaveMediaMainModel
import com.zat.lexikey.models.profileModels.contactUsModel.ContactUsMainModel
import com.zat.lexikey.models.requestModels.*
import com.zat.lexikey.utils.prepareFilePart
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class DataRepository(private var context: Context) {

    private var apiCaller = RetrofitClient(context).getService()

    private var reminderDao = LexikeyDatabase.getDatabase(context).reminderDao()

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
        try {
            return ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            return when (throwable) {
                is IOException -> {
                    Log.d("laksjdcas", throwable.toString())
                    ResultWrapper.NetworkError
                }
                is HttpException -> {
                    Log.d("laksjdcas", throwable.toString())
                    val code = throwable.code()
                    val errorResponse = throwable.toString()

                    ResultWrapper.GenericError(code, errorResponse)
                }
                else -> {
                    Log.d("laksjdcas", throwable.toString())
                    ResultWrapper.GenericError(null, throwable.message)
                }
            }

        }
    }

    suspend fun register(
        email: String,
        name: String,
        password: String,
        password_confirmation: String,
        deviceToken: String
    ): ResultWrapper<Response<RegisterMainModel>> {
        var map = HashMap<String, String>()
        map["email"] = email
        map["name"] = name
        map["password"] = password
        map["password_confirmation"] = password_confirmation
        map["device_token"] = deviceToken

        return safeApiCall { apiCaller.register(map) }
    }

    suspend fun profileCompletion(profileCompletionRequestModel: ProfileCompletionRequestModel):
            ResultWrapper<Response<LoginMainModel>> {
        val d_o_b = profileCompletionRequestModel.d_o_b.toRequestBody(MultipartBody.FORM)
        val gender = profileCompletionRequestModel.gender.toRequestBody(MultipartBody.FORM)
        val country = profileCompletionRequestModel.country.toRequestBody(MultipartBody.FORM)
        val city =
            profileCompletionRequestModel.city.toRequestBody(MultipartBody.FORM)
        val profession = profileCompletionRequestModel.profession.toRequestBody(MultipartBody.FORM)
        val description =
            profileCompletionRequestModel.description.toRequestBody(MultipartBody.FORM)
        val interests = profileCompletionRequestModel.interests

        var imageRequest: MultipartBody.Part? = null
        profileCompletionRequestModel.image.let { img ->
            imageRequest = prepareFilePart(
                "image", img, 0
            )
        }

        var coverImageRequest: MultipartBody.Part? = null
        profileCompletionRequestModel.cover_image.let { img ->
            coverImageRequest = prepareFilePart(
                "cover_image", img, 0
            )
        }
        return safeApiCall {
            apiCaller.profileCompletion(
                d_o_b, gender, country, city, profession, description, interests,
                imageRequest, coverImageRequest
            )

        }

    }

    suspend fun profileUpdate(profileUpdateRequestModel: ProfileUpdateRequestModel):
            ResultWrapper<Response<LoginMainModel>> {
        val name = profileUpdateRequestModel.name.toRequestBody(MultipartBody.FORM)
        val profession = profileUpdateRequestModel.profession.toRequestBody(MultipartBody.FORM)
        val description =
            profileUpdateRequestModel.description.toRequestBody(MultipartBody.FORM)

        var imageRequest: MultipartBody.Part? = null
        profileUpdateRequestModel.image?.let { img ->
            imageRequest = prepareFilePart(
                "image", img!!, 0
            )
        }

        var coverImageRequest: MultipartBody.Part? = null
        profileUpdateRequestModel.cover_image?.let { img ->
            coverImageRequest = prepareFilePart(
                "cover_image", img!!, 0
            )
        }
        return safeApiCall {
            apiCaller.updateProfile(
                name, profession, description, imageRequest, coverImageRequest
            )

        }

    }

    suspend fun updateInterests(updateInterestsRequestModel: UpdateInterestsRequestModel) =
        safeApiCall { apiCaller.updateInterests(updateInterestsRequestModel) }


    suspend fun matchOTP(
        user_id: Int,
        otp: String

    ): ResultWrapper<Response<LoginMainModel>> {
        var map = HashMap<String, String>()
        map["otp"] = otp
        map["user_id"] = user_id.toString()

        return safeApiCall { apiCaller.matchOTP(map) }
    }

    suspend fun login(
        email: String,
        password: String,
        deviceToken: String
    ): ResultWrapper<Response<LoginMainModel>> {
        var map = HashMap<String, String>()
        map["email"] = email
        map["password"] = password
        map["device_token"] = deviceToken

        return safeApiCall { apiCaller.login(map) }
    }

    suspend fun logout() = safeApiCall { apiCaller.logout() }

    suspend fun verifyEmail(email: String): ResultWrapper<Response<LoginMainModel>> {

        var map = HashMap<String, String>()
        map["email"] = email

        return safeApiCall { apiCaller.verifyEmail(map) }
    }


    suspend fun matchPin(pin: String): ResultWrapper<Response<LoginMainModel>> {

        var map = HashMap<String, String>()
        map["pin"] = pin
        return safeApiCall { apiCaller.matchPin(map) }
    }

    suspend fun changePassword(
        userId: Int,
        password: String
    ): ResultWrapper<Response<SimpleMessageModel>> {
        var map = HashMap<String, String>()
        map["user_id"] = userId.toString()
        map["password"] = password
        return safeApiCall { apiCaller.changePassword(map) }
    }

    suspend fun privacyPolicy() = safeApiCall { apiCaller.privacyPolicy() }

    suspend fun termConditions() = safeApiCall { apiCaller.termConditions() }

    suspend fun contactUs(
        email: String,
        title: String,
        description: String
    ): ResultWrapper<Response<ContactUsMainModel>> {
        var map = HashMap<String, String>()
        map["email"] = email
        map["title"] = title
        map["description"] = description
        return safeApiCall { apiCaller.contactUs(map) }
    }

    suspend fun myProfile() = safeApiCall { apiCaller.myProfile() }

    suspend fun allPartners() = safeApiCall { apiCaller.allPartners() }

    suspend fun filterPartner(filterRequestModel: FilterRequestModel) =
        safeApiCall { apiCaller.filterPartner(filterRequestModel) }

    suspend fun partnerDetail(partner_id: Int) = safeApiCall { apiCaller.partnerDetail(partner_id) }

    suspend fun languages() = safeApiCall { apiCaller.languages() }

    suspend fun allInterests() = safeApiCall { apiCaller.allInterests() }

    suspend fun addInterest(interest: String): ResultWrapper<Response<SimpleMessageModel>> {

        var map = HashMap<String, String>()
        map["interest"] = interest
        return safeApiCall { apiCaller.addInterest(map) }
    }

    suspend fun clubCategories() = safeApiCall { apiCaller.clubCategories() }

    suspend fun myClubs() = safeApiCall { apiCaller.myClubs() }

    suspend fun getClubs() = safeApiCall { apiCaller.getClubs() }

    suspend fun getMyJoinedClubs() = safeApiCall { apiCaller.getMyJoinedClubs() }

    suspend fun joinClub(club_id: Int) = safeApiCall { apiCaller.joinClub(club_id) }

    suspend fun deleteClub(club_id: Int) = safeApiCall { apiCaller.deleteClub(club_id) }

    suspend fun fetchClub(club_id: Int) = safeApiCall { apiCaller.fetchClub(club_id) }

    suspend fun storeClub(storeClubRequestModel: StoreClubRequestModel):
            ResultWrapper<Response<SimpleMessageModel>> {
        val name = storeClubRequestModel.name.toRequestBody(MultipartBody.FORM)
        val category_id =
            storeClubRequestModel.category_id.toString().toRequestBody(MultipartBody.FORM)
        val description =
            storeClubRequestModel.description.toRequestBody(MultipartBody.FORM)
        val native_language_ids =
            storeClubRequestModel.native_language_ids.toString().toRequestBody(MultipartBody.FORM)
        val learning_language_ids =
            storeClubRequestModel.learning_language_ids.toString().toRequestBody(MultipartBody.FORM)

        var imageRequest: MultipartBody.Part? = null
        storeClubRequestModel.image_url.let { img ->
            imageRequest = prepareFilePart(
                "image_url", img!!, 0
            )
        }

        return safeApiCall {
            apiCaller.storeClub(
                name,
                description,
                category_id,
                native_language_ids,
                learning_language_ids,
                imageRequest
            )

        }

    }

    suspend fun updateClub(club_id: Int, storeClubRequestModel: StoreClubRequestModel):
            ResultWrapper<Response<SimpleMessageModel>> {
        val name = storeClubRequestModel.name.toRequestBody(MultipartBody.FORM)
        val category_id =
            storeClubRequestModel.category_id.toString().toRequestBody(MultipartBody.FORM)
        val description =
            storeClubRequestModel.description.toRequestBody(MultipartBody.FORM)
        val native_language_ids =
            storeClubRequestModel.native_language_ids.toString().toRequestBody(MultipartBody.FORM)
        val learning_language_ids =
            storeClubRequestModel.learning_language_ids.toString().toRequestBody(MultipartBody.FORM)

        var imageRequest: MultipartBody.Part? = null
        storeClubRequestModel.image_url?.let { img ->
            imageRequest = prepareFilePart(
                "image_url", img!!, 0
            )
        }

        return safeApiCall {
            apiCaller.updateClub(
                club_id,
                name,
                description,
                category_id,
                native_language_ids,
                learning_language_ids,
                imageRequest
            )

        }

    }

    suspend fun storeArticle(storeArticleRequestModel: StoreArticleRequestModel):
            ResultWrapper<Response<SimpleMessageModel>> {
        val name = storeArticleRequestModel.name.toRequestBody(MultipartBody.FORM)
        val club_id =
            storeArticleRequestModel.club_id.toString().toRequestBody(MultipartBody.FORM)
        val description =
            storeArticleRequestModel.description.toRequestBody(MultipartBody.FORM)

        var imageRequest: MultipartBody.Part? = null
        storeArticleRequestModel.img_url.let { img ->
            imageRequest = prepareFilePart(
                "img_url", img, 0
            )
        }

        return safeApiCall {
            apiCaller.storeArticle(
                name,
                description,
                club_id,
                imageRequest
            )

        }

    }

    suspend fun articlesByClub(club_id: Int) = safeApiCall { apiCaller.articlesByClub(club_id) }

    suspend fun fetchArticle(feed_id: Int) = safeApiCall { apiCaller.fetchArticle(feed_id) }

    suspend fun likeArticle(feed_id: Int) = safeApiCall { apiCaller.likeArticle(feed_id) }

    suspend fun getComments(feed_id: Int) = safeApiCall { apiCaller.getComments(feed_id) }

    suspend fun giveCommentArticle(feed_id: Int, commentRequestModel: CommentRequestModel) =
        safeApiCall { apiCaller.giveCommentArticle(feed_id, commentRequestModel) }

    suspend fun storeNativeLanguage(nativeLanguageRequestModel: NativeLanguageRequestModel) =
        safeApiCall { apiCaller.storeNativeLanguage(nativeLanguageRequestModel) }

    suspend fun deleteNativeLanguage(id:Int) =
        safeApiCall { apiCaller.deleteNativeLanguage(id) }

    suspend fun storeLearningLanguage(learningLanguageRequestModel: LearningLanguageRequestModel) =
        safeApiCall { apiCaller.storeLearningLanguage(learningLanguageRequestModel) }

    suspend fun reportUser(reportUserRequestModel: ReportUserRequestModel) =
        safeApiCall { apiCaller.reportUser(reportUserRequestModel) }

    suspend fun blockUser(userId: Int) = safeApiCall { apiCaller.blockUser(userId) }
    suspend fun allBlockedUser() = safeApiCall { apiCaller.allBlockedUser() }

    suspend fun muteUser(userId: Int) = safeApiCall { apiCaller.muteUser(userId) }

    suspend fun storeFlashCard(addFlashcardRequestModel: AddFlashcardRequestModel) =
        safeApiCall { apiCaller.storeFlashcard(addFlashcardRequestModel) }

    suspend fun allMutedUser() = safeApiCall { apiCaller.allMutedUser() }

    suspend fun flashcardForReview() = safeApiCall { apiCaller.flashcardForReview() }

    suspend fun addReviewFlashcard(card_id: Int, addReviewRequestModel: AddReviewRequestModel) =
        safeApiCall { apiCaller.addReviewFlashcard(card_id, addReviewRequestModel) }

    suspend fun flashcardProgress() = safeApiCall { apiCaller.flashcardProgress() }

    suspend fun flashcardStats(statsRequestModel: StatsRequestModel) =
        safeApiCall { apiCaller.flashcardStats(statsRequestModel) }

    suspend fun createQuiz(quizRequestModel: QuizRequestModel) =
        safeApiCall { apiCaller.createQuiz(quizRequestModel) }

    suspend fun fetchQuiz(quizId: Int) =
        safeApiCall { apiCaller.fetchQuiz(quizId) }

    suspend fun addResult(quiz_id: Int, addResultRequestModel: AddResultRequestModel) =
        safeApiCall { apiCaller.addResult(quiz_id, addResultRequestModel) }

    suspend fun storeAnswer(question_id: Int, storeAnswerRequestModel: StoreAnswerRequestModel) =
        safeApiCall { apiCaller.storeAnswer(question_id, storeAnswerRequestModel) }

    suspend fun addEvaluation(question_id: Int, evaluationRequestModel: EvaluationRequestModel) =
        safeApiCall { apiCaller.addEvaluation(question_id, evaluationRequestModel) }

    suspend fun saveMedia(saveMediaRequestModel: SaveMediaRequestModel):
            ResultWrapper<Response<SaveMediaMainModel>> {
        val type = saveMediaRequestModel.type

        var mediaRequest: MultipartBody.Part? = null
        if (type == 1) {
            saveMediaRequestModel.media_url.let { media ->
                mediaRequest = prepareFilePart(
                    "media_url", media!!, 2
                )
            }
        } else if (type == 3) {
            saveMediaRequestModel.media_url.let { media ->
                mediaRequest = prepareFilePart(
                    "media_url", media!!, 0
                )
            }
        }


        return safeApiCall {
            apiCaller.saveMedia(
                type!!,
                mediaRequest
            )

        }

    }

    suspend fun sendGameNotification(quizId: Int,userId: Int) =
        safeApiCall { apiCaller.sendGameNotification(quizId,userId) }


    suspend fun sendChatNotification(senderId: Int,receiverId: Int,chatRequestModel: ChatRequestModel) =
        safeApiCall { apiCaller.sendChatNotification(senderId , receiverId,chatRequestModel ) }

    suspend fun sendEvaluationNotification(quizId: Int,userId: Int) =
        safeApiCall { apiCaller.sendEvaluationNotification(quizId,userId) }

    suspend fun myAchievements() = safeApiCall { apiCaller.myAchievements() }

    suspend fun notificationHistory() = safeApiCall { apiCaller.notificationHistory() }



    // Reminder Local Database

    fun addReminder(reminderItem: ReminderMainModel): Long {
        return reminderDao.add(reminderItem)
    }

    fun getReminderItem(reminderId: Int) = reminderDao.getReminderItem(reminderId)

    fun getAll(): LiveData<List<ReminderMainModel>> {
        return reminderDao.getAll()
    }

    fun deleteReminder(reminderItem: ReminderMainModel): Int {
        return reminderDao.delete(reminderItem)
    }

    suspend fun updateReminder(reminderItem: ReminderMainModel): Int {
        return reminderDao.update(reminderItem)
    }

}


