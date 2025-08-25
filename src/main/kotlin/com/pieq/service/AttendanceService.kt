package com.pieq.service

import com.pieq.dao.AttendanceDao
import com.pieq.dao.EmployeeDao
import com.pieq.model.Attendance
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class AttendanceService(
    private val dao: AttendanceDao,
    private val employeeDao: EmployeeDao
) {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")


    fun addCheckIn(employeeId: String, checkInTimeStr: String?): Attendance {
        if (employeeDao.findById(employeeId) == null) {
            throw IllegalArgumentException("Employee ID not found")
        }

        val checkIn = parseOrNow(checkInTimeStr)

        if (checkIn.isAfter(LocalDateTime.now())) {
            throw IllegalArgumentException("Check-in time cannot be in the future")
        }


        val dayStart = checkIn.toLocalDate().atStartOfDay()
        val dayEnd = dayStart.plusDays(1).minusNanos(1)

        // if open check-in for that day exists -> conflict
        val open = dao.findOpenByEmployeeAndDate(employeeId, dayStart, dayEnd)
        if (open != null) {
            throw IllegalStateException("Already checked in for the day (attendanceId=${open.attendanceId})")
        }

        val attendance = Attendance(
            employeeId = employeeId,
            checkInDateTime = checkIn
        )
        val ok = dao.checkIn(attendance)
        if (!ok) throw RuntimeException("Failed to check-in")
        return attendance
    }


    fun addCheckOut(employeeId: String, checkOutTimeStr: String?): Attendance {
        if (employeeDao.findById(employeeId) == null) {
            throw IllegalArgumentException("Employee ID not found")
        }

        val checkOut = parseOrNow(checkOutTimeStr)

        if (checkOut.isAfter(LocalDateTime.now())) {
            throw IllegalArgumentException("Check-out time cannot be in the future")
        }

        // find the open attendance for the same day
        val dayStart = checkOut.toLocalDate().atStartOfDay()
        val dayEnd = dayStart.plusDays(1).minusNanos(1)
        val open = dao.findOpenByEmployeeAndDate(employeeId, dayStart, dayEnd)
            ?: throw IllegalStateException("No check-in found for the day")

        if (checkOut.isBefore(open.checkInDateTime)) {
            throw IllegalArgumentException("Check-out time cannot be before check-in time")
        }

        val ok = dao.checkOut(open.attendanceId, checkOut)
        if (!ok)
        {
            throw RuntimeException("Failed to check-out")
        }

        // return updated record
        return dao.findByAttendanceId(open.attendanceId) ?: throw RuntimeException("attendance not found")
    }

    fun listByEmployee(employeeId: String): List<Attendance> = dao.findByEmployee(employeeId)

    private fun parseOrNow(timeStr: String?): LocalDateTime {
        return if (timeStr.isNullOrBlank()) LocalDateTime.now()
        else LocalDateTime.parse(timeStr, formatter)
    }

    fun listAttendance(): List<Attendance> = dao.findAll()

    fun dateReport(fromDate: String, toDate: String)
    {
        try {
            val fromDateTime = LocalDateTime.parse(fromDate, formatter)
            val toDateTime = LocalDateTime.parse(toDate, formatter)
            val reportData = dao.dateReport(fromDateTime, toDateTime)
        }
        catch (e: DateTimeParseException){
            throw IllegalArgumentException("Oops! The input date time is not in the proper format. Please enter in yyyy-MM-dd HH:mm:ss")
        }
    }
}
