import java.time.LocalDateTime

class AttendanceList {
    private val attendances = mutableListOf<Attendance>()

    fun addCheckIn(employeeId: String, checkInTime: LocalDateTime): Boolean {

        if (checkInTime.isAfter(LocalDateTime.now())) {
            throw IllegalArgumentException("Check-in time cannot be in the future")
        }

        if (hasAlreadyCheckedIn(employeeId, checkInTime)) return false
        return attendances.add(Attendance(employeeId, checkInTime))
    }

    fun hasAlreadyCheckedIn(employeeId: String, checkInTime: LocalDateTime): Boolean {
        return attendances.any {
            it.employeeId == employeeId &&
                    it.checkIn.toLocalDate() == checkInTime.toLocalDate()
        }
    }

    fun hasAlreadyCheckedOut(employeeId: String, checkOutTime: LocalDateTime): Boolean {
        return attendances.any {
            it.employeeId == employeeId &&
                    it.checkIn.toLocalDate() == checkOutTime.toLocalDate() &&
                    it.checkOut != null
        }
    }

    fun addCheckOut(employeeId: String, checkOutTime: LocalDateTime): Boolean {

        if (checkOutTime.isAfter(LocalDateTime.now())) {
            throw IllegalArgumentException("Check-in time cannot be in the future")
        }

        val attendance = attendances.find {
            it.employeeId == employeeId &&
                    it.checkIn.toLocalDate() == checkOutTime.toLocalDate() &&
                    it.checkOut == null
        }
        if (attendance != null) {
            if (checkOutTime.isBefore(attendance.checkIn)) {
                throw IllegalArgumentException("Check-out time cannot be before check-in time")
            }
            return attendance.checkOutAndCalculateHours(checkOutTime)
        }

        return false
    }

    fun getAttendance(): String {
        return if (attendances.isNotEmpty()) {
            attendances.toString()
        }
        else {
            "No Attendance Record Found"
        }
    }


    override fun toString(): String {
        return attendances.joinToString("\n") { it.toString() }
    }

}
