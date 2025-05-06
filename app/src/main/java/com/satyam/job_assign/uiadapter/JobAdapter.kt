package com.satyam.job_assign.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satyam.job_assign.databinding.ItemJobBinding
import com.satyam.job_assign.model.JobItem
import com.satyam.job_assign.ui.JobDetailActivity

class JobAdapter : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    private val jobs = mutableListOf<JobItem>()

    fun setJobs(newJobs: List<JobItem>) {
        jobs.clear()
        jobs.addAll(newJobs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = ItemJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(jobs[position])
    }

    override fun getItemCount(): Int = jobs.size

    inner class JobViewHolder(private val binding: ItemJobBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(job: JobItem) {
            binding.textViewTitle.text = job.title
            binding.textViewLocation.text = job.location
            binding.textViewSalary.text = job.salary

            // 🔁 On item click, navigate to JobDetailActivity
            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, JobDetailActivity::class.java).apply {
                    putExtra("title", job.title)
                    putExtra("location", job.location)
                    putExtra("salary", job.salary)
                    putExtra("description", job.description)
                    putExtra("phone", job.phone)
                }
                context.startActivity(intent)
            }
        }
    }
}
