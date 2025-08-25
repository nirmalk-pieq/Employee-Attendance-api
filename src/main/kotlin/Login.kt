//import com.pieq.resources.EmployeeList
//import jakarta.ws.rs.FormParam
//import jakarta.ws.rs.POST
//import jakarta.ws.rs.Path
//import jakarta.ws.rs.core.MediaType
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import jakarta.ws.rs.core.Response
//
//class Login (val employeeList: EmployeeList)
//{
//
//    private val logger: Logger = LoggerFactory.getLogger(Login::class.java)
//
//    @POST
//    @Path("/login")
//    fun validate(
//        @FormParam ("username") username: String,
//        @FormParam ("password") password: String
//    ): Any {
//        logger.info("Login attempt for user: $username")
//        val validation = employeeList.employees.any {
//            it.firstName.equals(username, ignoreCase = true) && (it.firstName + it.lastName) == password
//        }
//        return if(validation)
//        {
//            logger.info("validation ${true}")
//            Response.status(200)
//                .entity(mapOf("message" to "Success"))
//                .type(MediaType.APPLICATION_JSON)
//                .build()
//        }
//        else
//        {
//            logger.info("Attempt fails")
//            Response.status(401)
//                .entity(mapOf("message" to "Unauthorised"))
//                .type(MediaType.APPLICATION_JSON)
//                .build()
//        }
//
//    }
//
//}