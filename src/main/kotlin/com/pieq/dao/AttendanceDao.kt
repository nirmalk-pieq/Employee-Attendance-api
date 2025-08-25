package com.pieq.dao

import com.pieq.model.Attendance
import org.jdbi.v3.core.Jdbi
import java.time.LocalDateTime
import java.util.UUID

class AttendanceDao(private val jdbi: Jdbi) {

    fun checkIn(att: Attendance): Boolean {
        return jdbi.withHandle<Boolean, Exception> { h ->
            h.createUpdate(
                """
                INSERT INTO attendance (attendanceId, employeeId, checkInDateTime)
                VALUES (:attendanceId, :employeeId, :checkInDateTime)
                """
            )
                .bind("attendanceId", att.attendanceId)
                .bind("employeeId", att.employeeId)
                .bind("checkInDateTime", att.checkInDateTime)
                .execute() > 0
        }
    }

    fun checkOut(attendanceId: UUID, checkOutDateTime: LocalDateTime): Boolean {
        return jdbi.withHandle<Boolean, Exception> { h ->
            h.createUpdate(
                """
                UPDATE attendance
                SET checkOutDateTime = :checkOutDateTime
                WHERE attendanceId = :attendanceId
                """
            )
                .bind("attendanceId", attendanceId)
                .bind("checkOutDateTime", checkOutDateTime)
                .execute() > 0
        }
    }

    fun findByAttendanceId(attendanceId: UUID): Attendance? =
        jdbi.withHandle<Attendance?, Exception> { h ->
            h.createQuery(
                """
                SELECT attendanceId, employeeId, checkInDateTime, checkOutDateTime
                FROM attendance WHERE attendanceId = :attendanceId
                """
            )
                .bind("attendanceId", attendanceId)
                .mapTo(Attendance::class.java)
                .findFirst()
                .orElse(null)
        }

    fun findAll(): List<Attendance> =
        jdbi.withHandle<List<Attendance>, Exception> { h ->
            h.createQuery(
                """
            SELECT 
                attendanceId,
                employeeId,
                checkInDateTime,
                checkOutDateTime,
                EXTRACT(EPOCH FROM (checkOutDateTime - checkInDateTime)) / 3600 AS hoursWorked
            FROM attendance
            """
            )
                .mapTo(Attendance::class.java)
                .list()
        }


    fun findByEmployee(employeeId: String): List<Attendance> =
        jdbi.withHandle<List<Attendance>, Exception> { h ->
            h.createQuery(
                """
                SELECT attendanceId, employeeId, checkInDateTime, checkOutDateTime
                FROM attendance WHERE employeeId = :employeeId
                ORDER BY checkInDateTime DESC
                """
            )
                .bind("employeeId", employeeId)
                .mapTo(Attendance::class.java)
                .list()
        }


    fun findOpenByEmployeeAndDate(employeeId: String, dateStart: LocalDateTime, dateEnd: LocalDateTime): Attendance? =
        jdbi.withHandle<Attendance?, Exception> { h ->
            h.createQuery(
                """
                SELECT attendanceId, employeeId, checkInDateTime, checkOutDateTime
                FROM attendance
                WHERE employeeId = :employeeId
                  AND checkInDateTime >= :dateStart
                  AND checkInDateTime <= :dateEnd
                  AND checkOutDateTime IS NULL
                LIMIT 1
                """
            )
                .bind("employeeId", employeeId)
                .bind("dateStart", dateStart)
                .bind("dateEnd", dateEnd)
                .mapTo(Attendance::class.java)
                .findFirst()
                .orElse(null)
        }

    fun dateReport(fromDateTime: LocalDateTime, toDateTime: LocalDateTime): List<Attendance> =
        jdbi.withHandle<List<Attendance>, Exception> { h ->
            h.createQuery(
                """
            SELECT attendanceId, employeeId, checkInDateTime, checkOutDateTime, 
                   EXTRACT(EPOCH FROM (checkOutDateTime - checkInDateTime)) / 3600 AS hoursWorked
            FROM attendance
            WHERE checkInDateTime >= :fromDateTime
              AND checkOutDateTime <= :toDateTime
              AND checkOutDateTime IS NOT NULL
            """
            )
                .bind("startDateTime", fromDateTime)
                .bind("endDateTime", toDateTime)
                .mapTo(Attendance::class.java)
                .list()
        }

}
