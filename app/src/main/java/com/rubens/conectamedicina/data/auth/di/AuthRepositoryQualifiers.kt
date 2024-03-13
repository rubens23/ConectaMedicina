package com.rubens.conectamedicina.data.auth.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UserAuthRepositoryQualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DoctorAuthRepositoryQualifier