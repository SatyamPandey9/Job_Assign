package com.satyam.job_assign.model

import com.google.gson.annotations.SerializedName

data class JobResponse(
    @SerializedName("results")
    val results: List<JobItem>
)

data class JobItem(
    val id: Int,
    val title: String,

    @SerializedName("place")
    val location: String,

    @SerializedName("Salary")
    val salary: String,

    @SerializedName("whatsapp_no")
    val phone: String,

    @SerializedName("content")
    val description: String,

    @SerializedName("company_name")
    val companyName: String
)
