package com.satyam.job_assign.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.satyam.job_assign.model.JobResponse
import com.satyam.job_assign.repository.JobRepository
import kotlinx.coroutines.launch

class JobViewModel(private val repository: JobRepository) : ViewModel() {

    private val _jobs = MutableLiveData<JobResponse>()
    val jobs: LiveData<JobResponse> = _jobs

    fun fetchJobs(page: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getJobs(page)
                Log.d("JobViewModel", "Calling: https://testapi.getlokalapp.com/common/jobs?page=$page")
                if (response.isSuccessful) {
                    response.body()?.let {
                        _jobs.postValue(it)
                    } ?: run {
                        Log.e("JobViewModel", "Response body is null")
                    }
                } else {
                    Log.e("JobViewModel", "API Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
