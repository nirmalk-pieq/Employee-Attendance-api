fun main() {
    val manager = EmployeeManager()

    fun createEmployee() {
        print("Enter First Name: ")
        val firstName = readLine() ?: ""
        print("Enter Last Name: ")
        val lastName = readLine() ?: ""
        print("Enter Role (${Role.entries.joinToString()}) : ")
        val roleInput = readln()
        val role = try {
            Role.valueOf(roleInput.uppercase())
        }
        catch (e: IllegalArgumentException) {
            println("Invalid Role. Available roles: ${Role.entries.joinToString()}")
            return
        }
        print("Enter Department (${Department.entries.joinToString()}): ")
        val departmentInput = readln()
        val department: Department = try {
            Department.valueOf(departmentInput.uppercase())
        }
        catch (e: IllegalArgumentException) {
            println("Invalid Department. Available Departments: ${Department.entries.joinToString()}")
            return
        }
        print("Enter Contact Number: ")
        val contactNumber = readLine() ?: ""
        print("Enter Reporting To: ")
        val reportingTo = readLine() ?: ""
        println(manager.addEmployee(firstName, lastName, role, department, contactNumber, reportingTo))
    }

    fun getEmployee()
    {
        val employees = manager.getEmployeeList()
        println(employees.ifEmpty { "No employees found" })
    }

    fun createCheckin()
    {
        print("Enter Employee ID: ")
        val id = readLine() ?: ""
        print("Enter Check-in Time (yyyy-MM-dd HH:mm): ")
        val checkInTime = readLine() ?: ""
        println(manager.addCheckIn(id, checkInTime))
    }

    fun createCheckout()
    {
        print("Enter Employee ID: ")
        val id = readLine() ?: ""
        print("Enter Check-out Time (yyyy-MM-dd HH:mm): ")
        val checkOutTime = readLine() ?: ""
        println(manager.addCheckOut(id, checkOutTime))
    }

    fun getAttendanceReport()
    {
        println(manager.getAttendanceSummary())
    }

    while (true) {
        println("\nEmployee Attendance System")
        println("1. Add Employee")
        println("2. View Employee List")
        println("3. Add Check-In")
        println("4. Add Check-Out")
        println("5. View Working Hours Summary")
        println("6. Exit")
        print("Enter choice: ")

        when (readLine()?.toIntOrNull() ?: 0) {
            1 -> createEmployee()

            2 -> getEmployee()

            3 -> createCheckin()

            4 -> createCheckout()

            5 -> getAttendanceReport()

            6 -> {
                println("Exiting...")
                break
            }

            else -> println("Invalid choice")
        }
    }
}

