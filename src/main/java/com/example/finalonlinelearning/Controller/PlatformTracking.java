package com.example.finalonlinelearning.Controller;

import com.example.finalonlinelearning.Entities.Course;
import com.example.finalonlinelearning.Entities.MicroServiceCount;
import com.example.finalonlinelearning.Entities.MicroserviceResponse;
import com.example.finalonlinelearning.Entities.User;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
public class PlatformTracking {
    public MicroServiceCount getAllStatistics(String authToken) {
        MicroServiceCount response = new MicroServiceCount();
        response.setMessage("Statistics are");
        response.setUsersCount(getUsersCount(authToken));
        response.setStudentsCount(getStudentsCount(authToken));
        response.setInstructorsCount(getInstructorsCount(authToken));
        response.setCoursesCount(getCoursesCount(authToken));
        return response;
    }
    public List<User> getAllUsers(String authToken) {
        String MICROSERVICE_BASE_URL = "http://localhost:3001/user/getall";
        // Create a JAX-RS client
        Client client = ClientBuilder.newClient();

        // Call the microservice endpoint
        Response response = client.target(MICROSERVICE_BASE_URL)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", authToken)
                .get();

        // Check the response status
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            // If the response is OK, return the list of users
            MicroserviceResponse microserviceResponse = response.readEntity(MicroserviceResponse.class);
            return microserviceResponse.getUsers();
        } else {
            // If there was an error, return null
            return null;
        }
    }


        public List<Course> getAllCourses(String authToken) {
        String MICROSERVICE_BASE_URL = "http://localhost:7000/course/getall";
        // Create a JAX-RS client
        Client client = ClientBuilder.newClient();

        // Call the microservice endpoint
        Response response = client.target(MICROSERVICE_BASE_URL)
                .request(MediaType.APPLICATION_JSON)
                .get();

        // Check the response status
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            // If the response is OK, return the list of courses
            MicroserviceResponse microserviceResponse = response.readEntity(MicroserviceResponse.class);
            return microserviceResponse.getCourses();
        } else {
            // If there was an error, return null
            return null;
        }
    }
    public int getUsersCount(String authToken) {
        List<User> users = getAllUsers(authToken);
        return users != null ? users.size() : -1;
    }

    public int getStudentsCount(String authToken) {
        List<User> users = getAllUsers(authToken);
        return users != null ? (int) users.stream().filter(user -> "Student".equals(user.getRole())).count() : -1;
    }

    public int getInstructorsCount(String authToken) {
        List<User> users = getAllUsers(authToken);
        return users != null ? (int) users.stream().filter(user -> "Instructor".equals(user.getRole())).count() : -1;
    }
    private int getCoursesCount(String authToken) {
        List<Course> courses = getAllCourses(authToken);
        return courses != null ? courses.size() : -1;
    }




    public MicroserviceResponse getAllStudents(String authToken) {
        String MICROSERVICE_BASE_URL = "http://localhost:3001/user/getallStudents";
        // Create a JAX-RS client
        Client client = ClientBuilder.newClient();

        // Call the microservice endpoint with authentication token
        Response response = client.target(MICROSERVICE_BASE_URL)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", authToken) // Add the authentication token to the request headers without "Bearer" prefix
                .get();

        // Check the response status
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            // If the response is OK, return the list of users
            MicroserviceResponse microserviceResponse = response.readEntity(MicroserviceResponse.class);
            return microserviceResponse;
        } else {
            // If there was an error, return null
            return null;
        }
    }

}