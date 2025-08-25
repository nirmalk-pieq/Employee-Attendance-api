package com.pieq.service

import com.pieq.dao.EmployeeDao
import com.pieq.model.Employee

class EmployeeService(private val dao: EmployeeDao) {
    fun addEmployee(employee: Employee): Boolean {
        if (dao.findById(employee.id) != null)
        {
            return false
        }
        return dao.insert(employee)
    }
    fun deleteEmployee(id: String): Boolean = dao.delete(id)
    fun getEmployee(id: String): Employee? = dao.findById(id)
    fun listEmployees(): List<Employee> = dao.findAll()
}
