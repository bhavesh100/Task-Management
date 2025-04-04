package com.bhavesh.taskmanager.di

import android.content.Context
import androidx.room.Room
import com.bhavesh.taskmanager.data.local.dao.TaskDao
import com.bhavesh.taskmanager.data.local.db.AppDatabase
import com.bhavesh.taskmanager.data.remote.api.TaskApi
import com.bhavesh.taskmanager.domain.repository.TaskRepository
import com.bhavesh.taskmanager.domain.usecase.AddTask
import com.bhavesh.taskmanager.domain.usecase.DeleteTask
import com.bhavesh.taskmanager.domain.usecase.GetTasks
import com.bhavesh.taskmanager.domain.usecase.TaskUseCases
import com.bhavesh.taskmanager.domain.usecase.UpdateTask
import com.bhavesh.taskmanager.repository.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskApi(): TaskApi {
        return Retrofit.Builder()
            .baseUrl("https://67efd88c2a80b06b8895fee2.mockapi.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaskApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "task_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(db: AppDatabase): TaskDao = db.taskDao()

    @Provides
    @Singleton
    fun provideRepository(api: TaskApi, dao: TaskDao): TaskRepository = TaskRepositoryImpl(api, dao)

    @Provides
    @Singleton
    fun provideTaskUseCases(repo: TaskRepository): TaskUseCases = TaskUseCases(
        getTasks = GetTasks(repo),
        addTask = AddTask(repo),
        updateTask = UpdateTask(repo),
        deleteTask = DeleteTask(repo)
    )
}