package com.satyam.job_assign.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.satyam.job_assign.model.JobItem
import com.satyam.job_assign.model.JobResponse
import com.satyam.job_assign.repository.JobRepository
import kotlinx.coroutines.launch

class JobViewModel(private val repository: JobRepository) : ViewModel() {

    private val _jobs = MutableLiveData<JobResponse>()
    val jobs: LiveData<JobResponse> = _jobs

    private var currentPage = 1
    private val allJobs = mutableListOf<JobItem>()
    private var isLoading = false

    fun fetchJobs(page: Int) {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                Log.d("JobViewModel", "Calling: https://testapi.getlokalapp.com/common/jobs?page=$page")
                val response = repository.getJobs(page)
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (page == 1) allJobs.clear() // reset on page 1
                        allJobs.addAll(it.results)
                        _jobs.postValue(JobResponse(results = allJobs))
                        currentPage = page
                    } ?: run {
                        Log.e("JobViewModel", "Response body is null")
                    }
                } else {
                    Log.e("JobViewModel", "API Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun loadMoreJobs() {
        fetchJobs(currentPage + 1)
    }

    fun refreshJobs() {
        currentPage = 1
        fetchJobs(currentPage)
    }
}
