package com.satyam.job_assign.ui.jobs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.satyam.job_assign.databinding.FragmentJobsBinding
import com.satyam.job_assign.repository.JobRepository
import com.satyam.job_assign.repository.RetrofitInstance
import com.satyam.job_assign.ui.adapter.JobAdapter
import com.satyam.job_assign.ui.viewmodel.JobViewModel
import com.satyam.job_assign.ui.viewmodel.JobViewModelFactory

class JobsFragment : Fragment() {

    private var _binding: FragmentJobsBinding? = null
    private val binding get() = _binding!!

    private lateinit var jobAdapter: JobAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var currentPage = 1
    private var isLoading = false

    private val jobViewModel: JobViewModel by viewModels {
        JobViewModelFactory(JobRepository(RetrofitInstance.api))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        jobViewModel.jobs.observe(viewLifecycleOwner, Observer { response ->
            Log.d("JobsFragment", "API response: $response")

            response?.let {
                binding.textViewJobs.text = "Total jobs: ${it.results.size}"
                jobAdapter.addJobs(it.results)
                isLoading = false
            } ?: run {
                Log.e("JobsFragment", "Received null response from API")
            }
        })

        jobViewModel.fetchJobs(currentPage)
    }

    private fun setupRecyclerView() {
        jobAdapter = JobAdapter()
        linearLayoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewJobs.apply {
            layoutManager = linearLayoutManager
            adapter = jobAdapter

            // 🔁 Infinite scroll listener
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val totalItemCount = linearLayoutManager.itemCount
                    val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

                    if (!isLoading && lastVisibleItem + 5 >= totalItemCount) {
                        isLoading = true
                        currentPage++
                        jobViewModel.fetchJobs(currentPage)
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
