package com.example.finalonlinelearning.Services;

import com.example.finalonlinelearning.Controller.PlatformTracking;
import com.example.finalonlinelearning.Entities.MicroServiceCount;
import com.example.finalonlinelearning.Entities.MicroserviceResponse;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/admin")
public class PlatformTrackingService {

    @Inject
    private PlatformTracking platformTracking;

    @Path("/getAllStudents")
    @GET
    @Produces("application/json")
    public Response getAllStudents(@HeaderParam("authorization") String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Authorization token is missing").build();
        }

        MicroserviceResponse microserviceResponse =  platformTracking.getAllStudents(authToken);
        if (microserviceResponse != null && microserviceResponse.getUsers() != null) {
            return Response.status(Response.Status.OK).entity(microserviceResponse).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching students!").build();
        }
    }

//    @Path("/getAllInstructors")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getAllInstructors() {
//        MicroserviceResponse microserviceResponse = platformTracking.getAllInstructors();
//        if (microserviceResponse != null && microserviceResponse.getUsers() != null) {
//            return Response.status(Response.Status.OK).entity(microserviceResponse).build();
//        } else {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching instructors!").build();
//        }
//    }

    //    @Path("/getUserById/{id}")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getUserById(@PathParam("id") String id) {
//        MicroserviceResponse microserviceResponse = adminController.getUserById(id);
//        if (microserviceResponse != null && microserviceResponse.getUser() != null) {
//            return Response.status(Response.Status.OK).entity(microserviceResponse).build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).entity("User not found with id: " + id).build();
//        }
//    }
//    @Path("/statistics")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getAllStatistics(@HeaderParam("authorization") String authToken) {
//        if (authToken == null || authToken.isEmpty()) {
//            return Response.status(Response.Status.UNAUTHORIZED).entity("Authorization token is missing").build();
//        }
//        MicroServiceCount microServiceCount = platformTracking.getAllStatistics(authToken);
//        if (microServiceCount != null) {
//            return Response.status(Response.Status.OK).entity(microServiceCount).build();
//        } else {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching statistics!").build();
//        }
//    }
    @Path("/statistics")
    @GET
    @Produces("application/json")
    public Response getAllStatistics(@HeaderParam("authorization") String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Authorization token is missing").build();
        }
        MicroServiceCount microServiceCount = platformTracking.getAllStatistics(authToken);
        if (microServiceCount != null) {
            return Response.status(Response.Status.OK).entity(microServiceCount).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching statistics!").build();
        }
    }
}


