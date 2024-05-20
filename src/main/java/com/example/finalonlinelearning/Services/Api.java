package com.example.finalonlinelearning.Services;

import com.example.finalonlinelearning.Controller.CourseEnrollementController;
import com.example.finalonlinelearning.Controller.PlatformTracking;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class Api extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(CourseEnrollementController.class);
        classes.add(PlatformTracking.class);
        classes.add(CourseEnrollementService.class);
        classes.add(PlatformTrackingService.class); /// Add your resource classes here
        classes.add(CORSFilter.class); // Register the CORSFilter class
        return classes;
    }
}
