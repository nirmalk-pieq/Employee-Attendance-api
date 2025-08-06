import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EmployeeManager {
    private val employeeList = EmployeeList()
    private val attendanceList = AttendanceList()

    fun addEmployee(firstName: String, lastName: String, role: Role, department: Department, contactNumber: String, reportingTo: String): String {
        val employee = Employee(firstName, lastName, role, department, contactNumber, reportingTo)
        return if (employee.validate()) {
            if (employeeList.addEmployee(employee)) {
                "Employee added successfully: ${employee.id}"
            }
            else {
                "Failed to add employee: ID already exists"
            }
        }
        else {
            "Failed to add employee: Invalid input"
        }
    }

    fun getEmployeeList(): String {
        return employeeList.toString()
    }

    fun addCheckIn(employeeId: String, checkInTime: String): String {
        try {
            if (!employeeList.employeeExists(employeeId)) {
                return "Employee ID not found"
            }
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val checkIn = LocalDateTime.parse(checkInTime, formatter)

            if (checkIn.isAfter(LocalDateTime.now())) {
                return "Check-in failed: Check-in time cannot be in the future"
            }

            return if (attendanceList.addCheckIn(employeeId, checkIn)) {
                "Check-in recorded successfully"
            } else {
                "Check-in failed: Already checked in for the day"
            }
        } catch (e: Exception) {
            return "Invalid date format. Use yyyy-MM-dd HH:mm"
        }
    }

    fun addCheckOut(employeeId: String, checkOutTime: String): String {
        try {
            if (!employeeList.employeeExists(employeeId)) {
                return "Employee ID not found"
            }
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val checkOut = LocalDateTime.parse(checkOutTime, formatter)

            if (checkOut.isAfter(LocalDateTime.now())) {
                return "Check-out failed: Check-out time cannot be in the future"
            }

            return if (!attendanceList.hasAlreadyCheckedIn(employeeId, checkOut)) {
                "Check-out failed: No check-in found for the day"
            } else if (attendanceList.hasAlreadyCheckedOut(employeeId, checkOut)) {
                "Check-out failed: Already checked out for the day"
            } else if (attendanceList.addCheckOut(employeeId, checkOut)) {
                "Check-out recorded successfully"
            } else {
                "Check-out failed"
            }
        } catch (e: Exception) {
            return "Invalid date format. Use yyyy-MM-dd HH:mm"
        }
    }

    fun getAttendanceSummary(): String {
        return attendanceList.getAttendance()
    }

}
