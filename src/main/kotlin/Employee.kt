class Employee(
    val firstName: String,
    val lastName: String,
    val role: Role,
    val department: Department,
    val contactNumber: String,
    val reportingTo: String
) {
    val id: String = generateId()

    companion object {
        private var idCounter = 1

        private fun generateId(): String {
            return "EMP-${idCounter++}"
        }
    }

    fun validate(): Boolean {
        return firstName.isNotBlank() && lastName.isNotBlank() && contactNumber.isNotBlank() && reportingTo.isNotBlank()
    }

    override fun toString(): String {
        return "ID: $id, Name: $firstName $lastName, Role: $role, " +
                "Department: $department, Contact: $contactNumber, Reporting To: $reportingTo"
    }
}