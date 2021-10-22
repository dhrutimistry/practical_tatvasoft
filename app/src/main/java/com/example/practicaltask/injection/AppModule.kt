package com.example.practicaltask.injection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.practicaltask.R
import com.example.practicaltask.database.AppDatabase
import com.example.practicaltask.utils.*
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Reusable
    internal fun providerContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Reusable
    internal fun providesSharedPreferences(@ApplicationContext context: Context) =
        context.getSharedPreferences(
            context.getString(R.string.app_name),
            0
        )


    @Provides
    @Reusable
    internal fun providesSharedPreferencesEditor(
        sharedPreferences: SharedPreferences
    ) = sharedPreferences.edit()


    @Provides
    @Reusable
    internal fun providerPreferences(@ApplicationContext context: Context): AppPreferences {
        return AppPreferences.newInstance(context as Application)
    }

    @Provides
    @Reusable
    internal fun providerDialogUtils(@ApplicationContext context: Context): AppDialogUtils {
        return AppDialogUtils.newInstance(context)
    }




    @Provides
    @Singleton
    internal fun providerAPPDatabase(@ApplicationContext context: Context):AppDatabase =
        Room.databaseBuilder(
            context, AppDatabase::class.java, "Kotlin-MVVM-Structure"
        ).allowMainThreadQueries().build()

}