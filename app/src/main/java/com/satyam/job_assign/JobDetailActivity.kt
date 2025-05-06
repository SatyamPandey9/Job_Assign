package com.satyam.job_assign.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.satyam.job_assign.databinding.ActivityJobDetailBinding

class JobDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Show back button in ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Job Details"

        // Get data from intent
        val title = intent.getStringExtra("title")
        val location = intent.getStringExtra("location")
        val salary = intent.getStringExtra("salary")
        val description = intent.getStringExtra("description")
        val phone = intent.getStringExtra("phone")

        // Bind data to views
        binding.textViewTitle.text = title
        binding.textViewLocation.text = location
        binding.textViewSalary.text = salary
        binding.textViewDescription.text = description
        binding.textViewPhone.text = phone

        // Click to dial phone number
        binding.textViewPhone.setOnClickListener {
            phone?.let {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$it")
                }
                startActivity(intent)
            }
        }
    }

    // Handle ActionBar back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
