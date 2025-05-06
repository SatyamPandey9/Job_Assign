package com.satyam.job_assign

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.satyam.job_assign.databinding.ActivityMainBinding
import com.satyam.job_assign.repository.JobRepository
import com.satyam.job_assign.repository.RetrofitInstance
import com.satyam.job_assign.ui.adapter.JobAdapter
import com.satyam.job_assign.ui.viewmodel.JobViewModel
import com.satyam.job_assign.ui.viewmodel.JobViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var jobAdapter: JobAdapter

    private val jobViewModel: JobViewModel by viewModels {
        JobViewModelFactory(JobRepository(RetrofitInstance.api))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        // Observe LiveData
        jobViewModel.jobs.observe(this, Observer { response ->
            Log.d("MainActivity", "API response: $response") // 👈 Always log the response for debugging

            response?.let {
                binding.textViewJobs.text = "Total jobs: ${it.results.size}"
                jobAdapter.setJobs(it.results)

            } ?: run {
                Log.e("MainActivity", "Received null response from API") // 👈 Helps identify issues
            }
        })

        // Initial API call
        jobViewModel.fetchJobs(1)
    }

    private fun setupRecyclerView() {
        jobAdapter = JobAdapter()
        binding.recyclerViewJobs.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = jobAdapter
        }
    }
}
