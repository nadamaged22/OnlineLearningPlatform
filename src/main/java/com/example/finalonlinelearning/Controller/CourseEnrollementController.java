package com.example.finalonlinelearning.Controller;

import com.example.finalonlinelearning.DB_Methods.DB_Methods;
import com.example.finalonlinelearning.Entities.Course;
import com.example.finalonlinelearning.Entities.Enrollment;
import com.example.finalonlinelearning.Entities.MicroserviceResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateful;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateful
public class CourseEnrollementController {
    private static final String COURSE_API_URL = "http://localhost:7000/course/getall";

public Response enrollCourse(String courseId, String token) {
    try {
        // Get the user ID and role from the token
        ObjectId userId = getUserIdFromToken(token);
        String userRole = getUserRoleFromToken(token);

        if (userId == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
        }

        // Check if the user is a student
        if (!"Student".equals(userRole)) {
            return Response.status(Response.Status.FORBIDDEN).entity("Only students can enroll in courses").build();
        }

        // Check if the course exists
        if (!isCourseExist(courseId)) {
            return Response.status(Response.Status.NOT_FOUND).entity("Course with ID " + courseId + " not found").build();
        } else if (isEnrolled(userId, courseId)) {
            return Response.status(Response.Status.CONFLICT).entity("Already enrolled in the course").build();
        } else {
            ObjectId courseObjectId = new ObjectId(courseId);
            boolean enrolled = DB_Methods.enrollCourse(userId, courseObjectId);
            if (!enrolled) {
                return Response.status(Response.Status.OK).entity("Failed to enroll in the course").build();
            }
            return Response.status(Response.Status.OK).entity("Course enrolled successfully").build();
        }

    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
}
    public Response updateEnrollmentStatus(String enrollmentId, Map<String, String> requestBody, String token) {
        try {
            // Get the user ID and role from the token
            ObjectId userId = getUserIdFromToken(token);
            String userRole = getUserRoleFromToken(token);

            if (userId == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
            }

            // Check if the user is an instructor
            if (userRole != null && userRole.equals("Instructor")) {
                String status = requestBody.get("status");

                // Update enrollment status
                ObjectId enrollmentObjectId = new ObjectId(enrollmentId);
                boolean updated = DB_Methods.updateEnrollmentStatus(enrollmentObjectId, status);
                if (updated) {
                    return Response.status(Response.Status.OK).entity("Enrollment status updated successfully").build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to update enrollment status").build();
                }
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity("You do not have permission to update enrollment status").build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

    }
    public Response getPendingEnrollmentRequests(String token) {
        try {
            // Get the user ID and role from the token
            ObjectId userId = getUserIdFromToken(token);
            String userRole = getUserRoleFromToken(token);

            if (userId == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
            }

            // Check if the user is an instructor
            if (userRole != null && userRole.equals("Instructor")) {
                List<Document> pendingEnrollments = DB_Methods.getPendingEnrollments();
                return Response.status(Response.Status.OK).entity(pendingEnrollments).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity("You do not have permission to view pending enrollment requests").build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    public Response ViewCurrentEnrollement(String token) {
        try {
            // Get the user ID and role from the token
            ObjectId userId = getUserIdFromToken(token);
            String userRole = getUserRoleFromToken(token);

            if (userId == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
            }

            // Check if the user is a student
            if (!"Student".equals(userRole)) {
                return Response.status(Response.Status.FORBIDDEN).entity("Only students can view their current enrollment requests").build();
            }

            // Get pending enrollment requests for the logged-in student
            List<Document> pendingEnrollments = DB_Methods.getPendingEnrollments(userId);
            if (pendingEnrollments != null) {
                return Response.status(Response.Status.OK).entity(pendingEnrollments).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to retrieve pending enrollment requests").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    public Response ViewPastEnrollement(String token) {
        try {
            // Get the user ID and role from the token
            ObjectId userId = getUserIdFromToken(token);
            String userRole = getUserRoleFromToken(token);

            if (userId == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
            }

            // Check if the user is a student
            if (!"Student".equals(userRole)) {
                return Response.status(Response.Status.FORBIDDEN).entity("Only students can view their past enrollment requests").build();
            }

            // Get pending enrollment requests for the logged-in student
            List<Document> pendingEnrollments = DB_Methods.getPastEnrollments(userId);
            if (pendingEnrollments != null) {
                return Response.status(Response.Status.OK).entity(pendingEnrollments).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to retrieve pending enrollment requests").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    //instructor accept
    public Response acceptRequest(String enrollmentId, String token) {
        try {
            // Get the user ID and role from the token
            ObjectId userId = getUserIdFromToken(token);
            String userRole = getUserRoleFromToken(token);

            if (userId == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
            }

            // Check if the user is an instructor
            if (userRole != null && userRole.equals("Instructor")) {
                // Update enrollment status to "Accepted"
                ObjectId enrollmentObjectId = new ObjectId(enrollmentId);
                boolean updated = DB_Methods.updateEnrollmentStatus(enrollmentObjectId, "Accepted");

                if (!updated) {
                    return Response.status(Response.Status.NOT_FOUND).entity("Request not found or you don't own this course").build();
                }

                // Assuming you have a method to get the studentId from the enrollmentId
                ObjectId studentId = DB_Methods.getStudentIdFromEnrollment(enrollmentObjectId);

                // Create notification for the student
                boolean notificationCreated = DB_Methods.createNotification(studentId, "Request Accepted", "Your request for the course has been accepted");

                if (!notificationCreated) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to create notification").build();
                }

                return Response.status(Response.Status.OK).entity("Request accepted").build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity("You do not have permission to accept enrollment requests").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    //instructor reject
    public Response rejectRequest(String enrollmentId, String token) {
        try {
            // Get the user ID and role from the token
            ObjectId userId = getUserIdFromToken(token);
            String userRole = getUserRoleFromToken(token);

            if (userId == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
            }

            // Check if the user is an instructor
            if (userRole != null && userRole.equals("Instructor")) {
                // Update enrollment status to "Rejected"
                ObjectId enrollmentObjectId = new ObjectId(enrollmentId);
                boolean updated = DB_Methods.updateEnrollmentStatus(enrollmentObjectId, "Rejected");

                if (!updated) {
                    return Response.status(Response.Status.NOT_FOUND).entity("Request not found or you don't own this course").build();
                }

                // Assuming you have a method to get the studentId from the enrollmentId
                ObjectId studentId = DB_Methods.getStudentIdFromEnrollment(enrollmentObjectId);

                // Create notification for the student
                boolean notificationCreated = DB_Methods.createNotification(studentId, "Request Rejected", "Your request for the course has been rejected");

                if (!notificationCreated) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to create notification").build();
                }

                return Response.status(Response.Status.OK).entity("Request rejected").build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity("You do not have permission to reject enrollment requests").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    public Response cancelEnrollmentRequest(String enrollmentId, String token) {
    try {
        // Get the user ID and role from the token
        ObjectId userId = getUserIdFromToken(token);
        String userRole = getUserRoleFromToken(token);

        if (userId == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
        }

        // Check if the user is a student
        if (!"Student".equals(userRole)) {
            return Response.status(Response.Status.FORBIDDEN).entity("Only students can cancel their enrollment requests").build();
        }

        // Cancel enrollment request
        ObjectId enrollmentObjectId = new ObjectId(enrollmentId);
        String enrollmentStatus = DB_Methods.getEnrollmentStatus(enrollmentObjectId); // Assuming there's a method to retrieve the enrollment status
        if (enrollmentStatus == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No enrollment request found").build();
        } else if (enrollmentStatus.equals("cancel")) {
            return Response.status(Response.Status.NOT_FOUND).entity("No course exists").build();
        }

        boolean canceled = DB_Methods.updateEnrollmentStatus(enrollmentObjectId, "cancel");
        if (canceled) {
            return Response.status(Response.Status.OK).entity("Enrollment request canceled successfully").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to cancel enrollment request").build();
        }

    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
}




    private ObjectId getUserIdFromToken(String token) {
        try {
            byte[] keyBytes = "sarahagfuisg1654685sarahagfuisg1654685sarahagfuisg1654685".getBytes(StandardCharsets.UTF_8);
            SecretKeySpec key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Print out the claims to see what's inside
            System.out.println("Claims: " + claims.toString());

            return new ObjectId(claims.get("id", String.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private String getUserRoleFromToken(String token) {
        try {
            byte[] keyBytes = "sarahagfuisg1654685sarahagfuisg1654685sarahagfuisg1654685".getBytes(StandardCharsets.UTF_8);
            SecretKeySpec key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("role", String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isCourseExist(String courseId) {
        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(COURSE_API_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                MicroserviceResponse microserviceResponse = response.readEntity(MicroserviceResponse.class);
                List<Course> Course = microserviceResponse.getCourses();
                for (Course course : Course) {
                    if (course.get_id().equals(courseId)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean isEnrolled(ObjectId userId, String courseId) {
        ObjectId courseObjectId = new ObjectId(courseId);
        return DB_Methods.isEnrolled(userId, courseObjectId);
    }
}
