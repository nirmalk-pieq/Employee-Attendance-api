package com.pieq.application

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.pieq.resources.AttendanceManager
import com.pieq.dao.AttendanceDao
import com.pieq.dao.EmployeeDao
import com.pieq.resources.EmployeeManager
import com.pieq.service.AttendanceService
import com.pieq.service.EmployeeService

import io.dropwizard.core.Application
import io.dropwizard.core.setup.Environment
import io.dropwizard.jdbi3.JdbiFactory
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin

class EmployeeApplication : Application<Configuration>() {
    override fun run(configuration: Configuration, environment: Environment) {
        val factory = JdbiFactory()

        val jdbi: Jdbi = factory.build(environment, configuration.database, "postgresql")
        jdbi.installPlugin(KotlinPlugin())
        jdbi.installPlugin(SqlObjectPlugin())

        val employeeDao = EmployeeDao(jdbi)
        val attendanceDao = AttendanceDao(jdbi)

        val employeeService = EmployeeService(employeeDao)
        val attendanceService = AttendanceService(attendanceDao, employeeDao)

        environment.jersey().register(EmployeeManager(employeeService))
        environment.jersey().register(AttendanceManager(attendanceService))


        environment.objectMapper.registerModule(KotlinModule.Builder().build())
    }
}

fun main(args: Array<String>) {
    EmployeeApplication().run(*args)
}
