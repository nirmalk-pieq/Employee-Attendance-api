package com.pieq.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

data class Attendance(
    val attendanceId: UUID = UUID.randomUUID(),
    val employeeId: String,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val checkInDateTime: LocalDateTime,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var checkOutDateTime: LocalDateTime? = null,

    var hoursWorked: Double = 0.0
)

 {
     fun checkOutAndCalculateHours(checkOutTime: LocalDateTime): Boolean {
        if (checkOutDateTime != null)
        {
            return false
        }
        else
        {
            checkOutDateTime = checkOutTime
            hoursWorked = Duration.between(checkInDateTime, checkOutDateTime).toMinutes() / 60.0
            return true
        }
    }


    override fun toString(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        return "Employee ID: $employeeId, Check-in: ${checkInDateTime.format(formatter)}, " +
                "Check-out: ${checkOutDateTime?.format(formatter) ?: "Not checked out"}, " +
                "Hours: ${String.format("%.2f", hoursWorked)}"
    }
}