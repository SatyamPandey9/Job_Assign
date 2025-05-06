package com.satyam.job_assign.repository

import com.satyam.job_assign.api.JobApiService // ✅ Correct import
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://testapi.getlokalapp.com/") // ✅ baseUrl must end with "/"
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: JobApiService by lazy {
        retrofit.create(JobApiService::class.java) // ✅ Correct Kotlin syntax
    }
}
