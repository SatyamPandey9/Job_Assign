package com.satyam.job_assign.repository

import com.satyam.job_assign.api.JobApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://testapi.getlokalapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: JobApiService by lazy {
        retrofit.create(JobApiService::class.java)
    }
}
