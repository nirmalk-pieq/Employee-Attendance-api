package com.pieq.model

import java.util.UUID

data class Employee(
    val id: String = UUID.randomUUID().toString(),
    val firstName: String,
    val lastName: String,
    val role: String,
    val department: String,
    val reportingTo: String?
) {
//    val id: String = generateId()
//
//    companion object {
//        private var idCounter = 1
//
//        private fun generateId(): String {
//            return "EMP-${idCounter++}"
//        }
//    }

    fun validate(): Boolean {
        val validRole = Role.entries.any { it.id == role }
        val validDepartment = Department.entries.any { it.id == department }
        return firstName.isNotBlank() && lastName.isNotBlank() && validRole && validDepartment
    }

//    override fun toString(): String {
//        return "ID: $id, Name: $firstName $lastName, Role: $role, " +
//                "Department: $department, Reporting To: $reportingTo"
//    }
}