class EmployeeList {
    private val employees = mutableListOf<Employee>()

    fun addEmployee(employee: Employee): Boolean {
        if (employeeExists(employee.id))
        {
            return false
        }
        return employees.add(employee)
    }

    fun deleteEmployee(id: String): Boolean {
        return employees.removeIf { it.id == id }
    }

    fun employeeExists(id: String): Boolean {
        return employees.any { it.id == id }
    }

    override fun toString(): String {
        return employees.joinToString("\n") { it.toString() }
    }

    fun getEmployee(id: String): Employee? {
        return employees.find { it.id == id }
    }
}
