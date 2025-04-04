package com.bhavesh.taskmanager.data.remote.api

import com.bhavesh.taskmanager.data.remote.dto.TaskDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApi {
    @GET("task")
    suspend fun getTasks(): List<TaskDto>

    @POST("task")
    suspend fun addTask(@Body task: TaskDto): TaskDto

    @PUT("task/{id}")
    suspend fun updateTask(@Path("id") id: Int, @Body task: TaskDto): TaskDto

    @DELETE("task/{id}")
    suspend fun deleteTask(@Path("id") id: Int)
}