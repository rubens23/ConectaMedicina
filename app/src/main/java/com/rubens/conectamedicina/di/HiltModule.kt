package com.rubens.conectamedicina.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.rubens.conectamedicina.data.auth.ClientAuthApi
import com.rubens.conectamedicina.data.auth.AuthRepository
import com.rubens.conectamedicina.data.auth.ClientAuthRepositoryImpl
import com.rubens.conectamedicina.data.auth.DoctorAuthApi
import com.rubens.conectamedicina.data.auth.DoctorAuthRepositoryImpl
import com.rubens.conectamedicina.data.auth.di.DoctorAuthRepositoryQualifier
import com.rubens.conectamedicina.data.auth.di.UserAuthRepositoryQualifier
import com.rubens.conectamedicina.data.appointments.ApiAppointments
import com.rubens.conectamedicina.data.appointments.AppointmentsDataSourceImpl
import com.rubens.conectamedicina.data.appointments.AppoitmentsDataSource
import com.rubens.conectamedicina.data.chat.ApiChat
import com.rubens.conectamedicina.data.chat.ChatDataSource
import com.rubens.conectamedicina.data.chat.ChatDataSourceImpl
import com.rubens.conectamedicina.data.doctors.ApiDoctors
import com.rubens.conectamedicina.data.doctors.DoctorDataSource
import com.rubens.conectamedicina.data.doctors.DoctorDataSourceImpl
import com.rubens.conectamedicina.data.notification.ApiNotifications
import com.rubens.conectamedicina.data.notification.ApiService
import com.rubens.conectamedicina.data.notification.ApiServiceImpl
import com.rubens.conectamedicina.data.notification.NotificationDataSource
import com.rubens.conectamedicina.data.notification.NotificationDataSourceImpl
import com.rubens.conectamedicina.data.reviews.ApiReviews
import com.rubens.conectamedicina.data.reviews.ReviewsAndFeedbacksDataSource
import com.rubens.conectamedicina.data.reviews.ReviewsAndFeedbacksDataSourceImpl
import com.rubens.conectamedicina.data.storage.FileStorage
import com.rubens.conectamedicina.data.storage.FileStorageImpl
import com.rubens.conectamedicina.data.user.ApiUsers
import com.rubens.conectamedicina.data.user.UserDataSource
import com.rubens.conectamedicina.data.user.UserDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun providesClientAuthApi(): ClientAuthApi {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.5:8081/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()

    }


    @Provides
    @Singleton
    fun providesDoctorAuthApi(): DoctorAuthApi {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.5:8083/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesDoctorsApi(): ApiDoctors{
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.5:8081/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()

    }

    @Provides
    @Singleton
    fun providesAppointmentsApi(): ApiAppointments{
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.5:8081/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()

    }

    @Provides
    @Singleton
    fun providesUserApi(): ApiUsers{
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.5:8081/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesNotificationApi(): ApiNotifications{
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.5:8081/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesChatApi(): ApiChat{
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.5:8081/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesReviewApi(): ApiReviews{
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.5:8081/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }


    @Provides
    @Singleton
    fun providesNotificationClient(): HttpClient{
        return HttpClient(Android){
            install(HttpTimeout){

                requestTimeoutMillis = 10000
            }

        }

    }


    @Provides
    @Singleton
    fun providesFileStorage(): FileStorage {
        return FileStorageImpl()

    }




    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences{
        return app.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesDoctorDataSource(apiDoctors: ApiDoctors, preferences: SharedPreferences): DoctorDataSource{
        return DoctorDataSourceImpl(apiDoctors, preferences)
    }

    @Provides
    @Singleton
    fun providesReviewDataSource(apiReviews: ApiReviews, preferences: SharedPreferences): ReviewsAndFeedbacksDataSource{
        return ReviewsAndFeedbacksDataSourceImpl(apiReviews, preferences)
    }

    @Provides
    @Singleton
    fun providesAppointmentDataSource(apiAppointments: ApiAppointments, preferences: SharedPreferences): AppoitmentsDataSource {
        return AppointmentsDataSourceImpl(apiAppointments, preferences)
    }


    @Provides
    @Singleton
    fun providesUserDataSource(apiUsers: ApiUsers, preferences: SharedPreferences): UserDataSource{
        return UserDataSourceImpl(apiUsers, preferences)
    }

    @Provides
    @Singleton
    fun providesChatDataSource(apiChat: ApiChat, preferences: SharedPreferences): ChatDataSource{
        return ChatDataSourceImpl(apiChat, preferences)
    }

    @Provides
    @Singleton
    fun providesNotificationDataSource(apiNotifications: ApiNotifications, preferences: SharedPreferences, httpClient: HttpClient): NotificationDataSource{
        return NotificationDataSourceImpl(apiNotifications, preferences, httpClient)
    }

    @Provides
    @Singleton
    @UserAuthRepositoryQualifier
    fun providesClientAuthRepository(api: ClientAuthApi, sharedPreferences: SharedPreferences): AuthRepository {
        return ClientAuthRepositoryImpl(api,
        sharedPreferences)
    }

    @Provides
    @Singleton
    @DoctorAuthRepositoryQualifier
    fun providesDoctorAuthRepository(api: DoctorAuthApi, sharedPreferences: SharedPreferences): AuthRepository {
        return DoctorAuthRepositoryImpl(api, sharedPreferences)
    }



    @Provides
    @Singleton
    fun providesApiServiceImpl(client: HttpClient): ApiService{
        return ApiServiceImpl(client)
    }



}