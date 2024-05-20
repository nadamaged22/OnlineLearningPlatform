package com.example.finalonlinelearning.DB_Methods;

import com.example.finalonlinelearning.DB_Connection.DB;
import com.example.finalonlinelearning.Entities.Enrollment;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DB_Methods {
    public static boolean enrollCourse(ObjectId studentId, ObjectId courseId) {
        try {
            MongoCollection<Document> enrollmentCollection = DB.getCourseEnrollementCollection();
            Document enrollment = new Document("studentId", studentId)
                    .append("courseId", courseId)
                    .append("status", "pending");
            enrollmentCollection.insertOne(enrollment);
            return true;
        } catch (Exception e) {
            System.err.println(e.toString());
            return false;
        }
    }

    public static List<Document> getPendingEnrollments() { //for instructor
        try {
            MongoCollection<Document> enrollmentCollection = DB.getCourseEnrollementCollection();
            List<Document> pendingEnrollments = new ArrayList<>();

            // Create a filter to retrieve only enrollments with the status "pending"
            Bson filter = Filters.eq("status", "pending");

            // Find documents in the enrollment collection that match the filter
            FindIterable<Document> enrollments = enrollmentCollection.find(filter);

            // Iterate through the documents
            for (Document enrollmentDoc : enrollments) {
                // Convert ObjectId to String for studentId and courseId
                String studentId = enrollmentDoc.getObjectId("studentId").toString();
                String _ID = enrollmentDoc.getObjectId("_id").toString();
                String courseId = enrollmentDoc.getObjectId("courseId").toString();

                // Remove the original ObjectId fields
                enrollmentDoc.remove("_id");
                enrollmentDoc.remove("studentId");
                enrollmentDoc.remove("courseId");

                // Add the converted fields
                enrollmentDoc.put("_id", _ID);
                enrollmentDoc.put("studentId", studentId);
                enrollmentDoc.put("courseId", courseId);

                pendingEnrollments.add(enrollmentDoc);
            }
            return pendingEnrollments;
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
    }

    public static boolean createNotification(ObjectId studentId, String title, String message) {
        try {
            MongoCollection<Document> notificationCollection = DB.getNotificationCollection();
            Document notification = new Document("studentId", studentId)
                    .append("title", title)
                    .append("message", message)
                    .append("status", "unread");
            notificationCollection.insertOne(notification);
            return true;
        } catch (Exception e) {
            System.err.println(e.toString());
            return false;
        }
    }

    public static List<Document> getPendingEnrollments(ObjectId studentId) { //for student
        try {
            MongoCollection<Document> enrollmentCollection = DB.getCourseEnrollementCollection();
            List<Document> pendingEnrollments = new ArrayList<>();

            // Create a filter to retrieve only enrollments with the status "pending" for the given student
            Bson filter = Filters.and(Filters.eq("status", "pending"), Filters.eq("studentId", studentId));

            // Find documents in the enrollment collection that match the filter
            FindIterable<Document> enrollments = enrollmentCollection.find(filter);

            // Iterate through the documents
            for (Document enrollmentDoc : enrollments) {
                // Convert ObjectId to String for studentId and courseId
                String _ID = enrollmentDoc.getObjectId("_id").toString();
                String courseId = enrollmentDoc.getObjectId("courseId").toString();
                String studentID = enrollmentDoc.getObjectId("studentId").toString();

                // Remove the original ObjectId fields
                enrollmentDoc.remove("_id");
                enrollmentDoc.remove("courseId");
                enrollmentDoc.remove("studentId");

                // Add the converted fields
                enrollmentDoc.put("_id", _ID);
                enrollmentDoc.put("courseId", courseId);
                enrollmentDoc.put("studentId", studentID);

                pendingEnrollments.add(enrollmentDoc);
            }
            return pendingEnrollments;
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
    }


    public static List<Document> getPastEnrollments(ObjectId studentId) { //for student
        try {
            MongoCollection<Document> enrollmentCollection = DB.getCourseEnrollementCollection();
            List<Document> pendingEnrollments = new ArrayList<>();

            // Create a filter to retrieve only enrollments with the status "pending" for the given student
            Bson filter = Filters.and(
                    Filters.in("status", Arrays.asList("Accepted", "Rejected")),
                    Filters.eq("studentId", studentId)
            );

            // Find documents in the enrollment collection that match the filter
            FindIterable<Document> enrollments = enrollmentCollection.find(filter);

            // Iterate through the documents
            for (Document enrollmentDoc : enrollments) {
                // Convert ObjectId to String for studentId and courseId
                String _ID = enrollmentDoc.getObjectId("_id").toString();
                String courseId = enrollmentDoc.getObjectId("courseId").toString();
                String studentID = enrollmentDoc.getObjectId("studentId").toString();

                // Remove the original ObjectId fields
                enrollmentDoc.remove("_id");
                enrollmentDoc.remove("courseId");
                enrollmentDoc.remove("studentId");

                // Add the converted fields
                enrollmentDoc.put("_id", _ID);
                enrollmentDoc.put("courseId", courseId);
                enrollmentDoc.put("studentId", studentID);

                pendingEnrollments.add(enrollmentDoc);
            }
            return pendingEnrollments;
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
    }

    public static boolean cancelEnrollmentRequest(ObjectId enrollmentId) {
        try {
            MongoCollection<Document> enrollmentCollection = DB.getCourseEnrollementCollection();
            Document query = new Document("_id", enrollmentId);
            Document update = new Document("$set", new Document("status", "cancel"));
            enrollmentCollection.updateOne(query, update);
            return true;
        } catch (Exception e) {
            System.err.println(e.toString());
            return false;
        }
    }

    public static String getEnrollmentStatus(ObjectId enrollmentId) {
        try {
            MongoCollection<Document> enrollmentCollection = DB.getCourseEnrollementCollection();

            // Create a filter to retrieve enrollment with the given ID
            Bson filter = Filters.eq("_id", enrollmentId);

            // Find the enrollment document that matches the filter
            Document enrollmentDoc = enrollmentCollection.find(filter).first();

            // If enrollment document exists, return its status
            if (enrollmentDoc != null) {
                return enrollmentDoc.getString("status");
            } else {
                return null; // Enrollment not found
            }
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
    }


    public static boolean isEnrolled(ObjectId userId, ObjectId courseId) {
        try {
            // Initialize database connection if needed (only if not already initialized)
            DB.initializeDatabaseConnection();

            MongoCollection<Document> collection = DB.getCourseEnrollementCollection();
            List<String> statuses = Arrays.asList("Accepted", "pending");

            Document query = new Document("studentId", userId)
                    .append("courseId", courseId)
                    .append("status", new Document("$in", statuses));

            return collection.find(query).iterator().hasNext();
        } catch (Exception e) {
            System.err.println(e.toString());
            return false;
        }
    }

    public static boolean updateEnrollmentStatus(ObjectId enrollmentId, String status) {
        try {
            MongoCollection<Document> enrollmentCollection = DB.getCourseEnrollementCollection();
            Document query = new Document("_id", enrollmentId);
            Document update = new Document("$set", new Document("status", status));
            enrollmentCollection.updateOne(query, update);
            return true;
        } catch (Exception e) {
            System.err.println(e.toString());
            return false;
        }
    }
    public static boolean updateStatus(ObjectId courseId, ObjectId studentId, String status) {
        try {
            MongoCollection<Document> enrollmentCollection = DB.getCourseEnrollementCollection();
            Document query = new Document("courseId", courseId).append("studentId", studentId);
            Document update = new Document("$set", new Document("status", status));
            enrollmentCollection.updateOne(query, update);
            return true;
        } catch (Exception e) {
            System.err.println(e.toString());
            return false;
        }
    }
    public static ObjectId getStudentIdFromEnrollment(ObjectId enrollmentId) {
        try {
            MongoCollection<Document> enrollmentCollection = DB.getCourseEnrollementCollection();
            Document query = new Document("_id", enrollmentId);
            Document enrollment = enrollmentCollection.find(query).first();

            if (enrollment != null) {
                return enrollment.getObjectId("studentId");
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
    }
}


