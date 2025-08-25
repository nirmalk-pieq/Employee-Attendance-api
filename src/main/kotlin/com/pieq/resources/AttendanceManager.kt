package com.pieq.resources

import com.pieq.service.AttendanceService
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.time.LocalDateTime

@Path("/attendance")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AttendanceManager(private val service: AttendanceService) {

    @POST
    @Path("/addCheckin")
    fun addCheckIn(
        @QueryParam("employeeId") employeeId: String,
        @QueryParam("checkInTime") checkInTime: String?
    ): Response =
        try {
            val attendance = service.addCheckIn(employeeId, checkInTime)
            Response.status(Response.Status.CREATED).entity(attendance).build()
        } catch (e: IllegalArgumentException) {
            Response.status(Response.Status.BAD_REQUEST).entity(mapOf("error" to e.message)).build()
        } catch (e: IllegalStateException) {
            Response.status(Response.Status.CONFLICT).entity(mapOf("error" to e.message)).build()
        } catch (e: Exception) {
            Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mapOf("error" to e.message)).build()
        }


    @POST
    @Path("/addCheckOut")
    fun addCheckOut(
        @QueryParam("employeeId") employeeId: String,
        @QueryParam("checkOutTime") checkOutTime: String
    ): Response =
        try {
            val attendance = service.addCheckOut(employeeId, checkOutTime)
            Response.ok(attendance).build()
        } catch (e: IllegalArgumentException) {
            Response.status(Response.Status.BAD_REQUEST).entity(mapOf("error" to e.message)).build()
        } catch (e: IllegalStateException) {
            Response.status(Response.Status.CONFLICT).entity(mapOf("error" to e.message)).build()
        } catch (e: Exception) {
            Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mapOf("error" to e.message)).build()
        }


    @GET
    @Path("/{employeeId}")
    fun listByEmployee(@PathParam("employeeId") employeeId: String): Response {
        val recs = service.listByEmployee(employeeId)
        return if (recs.isEmpty()) {
            Response.status(Response.Status.NOT_FOUND).entity(mapOf("error" to "No attendance found for employee $employeeId")).build()
        } else Response.ok(recs).build()
    }

    @GET
    @Path("/")
    fun listAttendance(): Response = Response.ok(service.listAttendance()).build()

    @GET
    @Path("/report")
    fun dateReport(
        @QueryParam("fromDate") fromDate: String,
        @QueryParam("toDate") toDate: String
    ): Response =
        try {
            val attendance = service.dateReport(fromDate,toDate)
            Response.status(Response.Status.OK).entity(attendance).build()
        }
        catch (e: IllegalArgumentException) {
            Response.status(Response.Status.BAD_REQUEST).entity(mapOf("error" to e.message)).build()
        }
        catch (e: Exception) {
            Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mapOf("error" to e.message)).build()
        }
}
