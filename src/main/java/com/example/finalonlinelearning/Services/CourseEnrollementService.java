package com.example.finalonlinelearning.Services;

import com.example.finalonlinelearning.Controller.CourseEnrollementController;
import com.example.finalonlinelearning.DB_Connection.DB;
import com.example.finalonlinelearning.DB_Methods.DB_Methods;
import org.bson.types.ObjectId;


import javax.inject.Inject;
        import javax.ws.rs.*;
        import javax.ws.rs.core.Context;
        import javax.ws.rs.core.HttpHeaders;
        import javax.ws.rs.core.MediaType;
        import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;

@Path("/Course")
public class CourseEnrollementService {
    static {
        DB.initializeDatabaseConnection();
    }

    @Inject
    private CourseEnrollementController CourseEnrollement;


    @Path("/enrollCourse/{courseId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response enrollCourse(@PathParam("courseId") String courseId, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        if (token == null || token.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Authorization token is missing").build();
        }

        // Enroll the student in the course
        Response enrollmentResponse = CourseEnrollement.enrollCourse(courseId, token);

        if (enrollmentResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            return Response.status(Response.Status.OK).entity("Course enrolled successfully").build();
        } else if (enrollmentResponse.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            return Response.status(Response.Status.FORBIDDEN).entity("You do not have permission to enroll in the course").build();
        } else if (enrollmentResponse.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
            return Response.status(Response.Status.CONFLICT).entity("You are already enrolled in this course").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to enroll in the course").build();
        }
    }

    @Path("/updateEnrollmentStatus/{enrollmentId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEnrollmentStatus(@PathParam("enrollmentId") String enrollmentId, Map<String, String> requestBody, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        if (token == null || token.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Authorization token is missing").build();
        }

        // Update enrollment status
        Response updateResponse = CourseEnrollement.updateEnrollmentStatus(enrollmentId, requestBody, token);

        if (updateResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            return Response.status(Response.Status.OK).entity("Enrollment status updated successfully").build();
        } else if (updateResponse.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to update enrollment status").build();
        } else if (updateResponse.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            return Response.status(Response.Status.FORBIDDEN).entity("You do not have permission to update enrollment status").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to update enrollment status").build();
        }
    }

    @Path("/pendingEnrollments")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPendingEnrollments(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        if (token == null || token.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Authorization token is missing").build();
        }
        return CourseEnrollement.getPendingEnrollmentRequests(token);
    }

    //current logged in student pending requests
    @Path("/ViewCurrentEnrollement")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPendingStudentEnrollments(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        if (token == null || token.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Authorization token is missing").build();
        }
        return CourseEnrollement.ViewCurrentEnrollement(token);
    }

    @Path("/ViewPastEnrollement")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response ViewPastEnrollements(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        if (token == null || token.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Authorization token is missing").build();
        }
        return CourseEnrollement.ViewPastEnrollement(token);
    }


    @Path("/cancelEnrollment/{enrollmentId}") //soft delete
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelEnrollmentRequest(@PathParam("enrollmentId") String enrollmentId, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try {
            if (token == null || token.isEmpty()) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Authorization token is missing").build();
            }

            // Parse enrollment ID to ObjectId
            ObjectId enrollmentObjectId = new ObjectId(enrollmentId);

            // Get the enrollment status
            String enrollmentStatus = DB_Methods.getEnrollmentStatus(enrollmentObjectId);

            // Check if enrollment exists and not already canceled
            if (enrollmentStatus == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Enrollment not found").build();
            } else if (enrollmentStatus.equals("cancel")) {
                return Response.status(Response.Status.NOT_FOUND).entity("Enrollment already canceled").build();
            }

            // Cancel the enrollment request
            Response cancelResponse = CourseEnrollement.cancelEnrollmentRequest(enrollmentId, token);

            // Handle cancel response
            if (cancelResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                return Response.status(Response.Status.OK).entity("Enrollment request canceled successfully").build();
            } else if (cancelResponse.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to cancel enrollment request").build();
            } else if (cancelResponse.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
                return Response.status(Response.Status.FORBIDDEN).entity("You do not have permission to cancel the enrollment request").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to cancel enrollment request").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }
    @Path("/acceptRequest/{enrollmentId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response acceptEnrollmentRequest(@PathParam("enrollmentId") String enrollmentId, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        if (token == null || token.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Authorization token is missing").build();
        }

        // Accept the enrollment request
        Response acceptResponse = CourseEnrollement.acceptRequest(enrollmentId, token);

        if (acceptResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            return Response.status(Response.Status.OK).entity("Request accepted").build();
        } else if (acceptResponse.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Request not found or you don't own this course").build();
        } else if (acceptResponse.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to accept the request").build();
        } else if (acceptResponse.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            return Response.status(Response.Status.FORBIDDEN).entity("You do not have permission to accept the request").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to accept the request").build();
        }
    }
    @Path("/rejectRequest/{enrollmentId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response rejectEnrollmentRequest(@PathParam("enrollmentId") String enrollmentId, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        if (token == null || token.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Authorization token is missing").build();
        }

        // Reject the enrollment request
        Response rejectResponse = CourseEnrollement.rejectRequest(enrollmentId, token);

        if (rejectResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            return Response.status(Response.Status.OK).entity("Request rejected").build();
        } else if (rejectResponse.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Request not found or you don't own this course").build();
        } else if (rejectResponse.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to reject the request").build();
        } else if (rejectResponse.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            return Response.status(Response.Status.FORBIDDEN).entity("You do not have permission to reject the request").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to reject the request").build();
        }
    }

}