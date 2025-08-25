package com.pieq.model

data class CreateEmployeeRequest(
    val firstName: String,
    val lastName: String,
    val role: String,
    val department: String,
    val reportingTo: String?
)
