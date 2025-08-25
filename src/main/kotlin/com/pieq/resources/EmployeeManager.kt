package com.pieq.resources

import com.pieq.model.CreateEmployeeRequest
import com.pieq.model.Department
import com.pieq.model.Employee
import com.pieq.model.Role
import com.pieq.service.EmployeeService
import jakarta.ws.rs.Path
import jakarta.ws.rs.POST
import jakarta.ws.rs.GET
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.Produces
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class EmployeeManager(private val service: EmployeeService) {

    private val logger: Logger = LoggerFactory.getLogger(EmployeeManager::class.java)
    @POST
    @Path("/addEmployee")
    fun addEmployee(request: CreateEmployeeRequest): Response {
        return try {
            val emp = Employee(
                firstName = request.firstName,
                lastName = request.lastName,
                role = request.role,
                department = request.department,
                reportingTo = request.reportingTo?:""
            )

            if (!emp.validate()) {
                Response.status(Response.Status.BAD_REQUEST)
                    .entity(mapOf(
                        "error" to "Invalid input",
                            "validRoles" to Role.entries.map { it.id to it.label },
                        "validDepartments" to Department.entries.map { it.id to it.label }
                    )).build()
            } else {
                val ok = service.addEmployee(emp)
                if (ok) {
                    Response.status(Response.Status.CREATED)
                        .entity(mapOf("message" to "Employee added", "id" to emp.id)).build()
                } else {
                    Response.status(Response.Status.CONFLICT)
                        .entity(mapOf("error" to "Employee with ID ${emp.id} already exists")).build()
                }
            }
        } catch (e: Exception) {
            Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(mapOf("error" to e.message)).build()
        }
    }



    @GET
    @Path("/")
    fun list(): Response = Response.ok(service.listEmployees()).build()

    @GET
    @Path("/{id}")
    fun get(@PathParam("id") id: String): Response {
        val emp = service.getEmployee(id)
        return if (emp != null) Response.ok(emp).build()
        else Response.status(Response.Status.NOT_FOUND).entity(mapOf("error" to "Employee not found")).build()
    }


}
