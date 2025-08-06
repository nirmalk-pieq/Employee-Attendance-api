import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Attendance(val employeeId: String, val checkIn: LocalDateTime) {
    var checkOut: LocalDateTime? = null
    private var hoursWorked: Double = 0.0

    fun checkOutAndCalculateHours(checkOutTime: LocalDateTime): Boolean {
        if (checkOut != null) return false
        checkOut = checkOutTime
        hoursWorked = Duration.between(checkIn, checkOut).toMinutes() / 60.0
        return true
    }

    override fun toString(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        return "Employee ID: $employeeId, Check-in: ${checkIn.format(formatter)}, " +
                "Check-out: ${checkOut?.format(formatter) ?: "Not checked out"}, " +
                "Hours: ${String.format("%.2f", hoursWorked)}"
    }
}
