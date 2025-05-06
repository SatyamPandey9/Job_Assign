package com.satyam.job_assign.repository

import com.satyam.job_assign.api.JobApiService
import com.satyam.job_assign.model.JobResponse

class JobRepository(private val apiService: JobApiService) {
    suspend fun getJobs(page: Int) = apiService.getJobs(page)
}