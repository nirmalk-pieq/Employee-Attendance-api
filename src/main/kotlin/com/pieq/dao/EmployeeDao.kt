package com.pieq.dao

import com.pieq.model.Employee
import org.jdbi.v3.core.Jdbi

class EmployeeDao(private val jdbi: Jdbi) {

    fun insert(employee: Employee): Boolean {
        return jdbi.withHandle<Boolean, Exception> { h ->
            h.createUpdate(
                """
                INSERT INTO employee (employeeId, firstName, lastName, role, department, reportingTo)
                VALUES (:id, :firstName, :lastName, :role, :department, :reportingTo)
                """
            )
                .bind("id", employee.id)
                .bind("firstName", employee.firstName)
                .bind("lastName", employee.lastName)
                .bind("role", employee.role)
                .bind("department", employee.department)
                .bind("reportingTo", employee.reportingTo)
                .execute() > 0
        }
    }

    fun findById(id: String): Employee? =
        jdbi.withHandle<Employee?, Exception> { h ->
            h.createQuery(
                """
                SELECT employeeId as id, firstName, lastName, role, department, reportingTo
                FROM employee WHERE employeeId = :id
                """
            )
                .bind("id", id)
                .mapTo(Employee::class.java)
                .findFirst()
                .orElse(null)
        }

    fun findAll(): List<Employee> =
        jdbi.withHandle<List<Employee>, Exception> { h ->
            h.createQuery(
                """
                SELECT employeeId as id, firstName, lastName, role, department, reportingTo
                FROM employee
                """
            )
                .mapTo(Employee::class.java)
                .list()
        }

    fun delete(id: String): Boolean =
        jdbi.withHandle<Boolean, Exception> { h ->
            h.createUpdate("DELETE FROM employee WHERE employeeId = :id")
                .bind("id", id)
                .execute() > 0
        }
}
