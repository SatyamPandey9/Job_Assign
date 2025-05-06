package com.satyam.job_assign.api

import com.satyam.job_assign.model.JobResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JobApiService {
    @GET("common/jobs")
    suspend fun getJobs(@Query("page") page: Int): Response<JobResponse>
}
