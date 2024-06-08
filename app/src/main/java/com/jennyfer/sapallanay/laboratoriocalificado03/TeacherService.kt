package com.jennyfer.sapallanay.laboratoriocalificado03

import android.telecom.Call
import retrofit2.http.GET


interface TeacherService {
    @GET("list/teacher-b")
    suspend fun getTeachers(): TeacherResponse
}