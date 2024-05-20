package com.example.finalonlinelearning.DB_Connection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;


public class DB {
    private static MongoClient client;
    private static MongoDatabase db;
    public static void initializeDatabaseConnection() {
        client = MongoClients.create("mongodb+srv://nadamaged:nnn123nnn@atlascluster.jgnsmsu.mongodb.net/?retryWrites=true&w=majority&appName=AtlasCluster");
        // Access the database
        db = client.getDatabase("OnlinePlatform");
    }

    public static MongoCollection<Document> getCourseEnrollementCollection(){
        return db.getCollection("CourseEnrollement");
    }
    public static MongoCollection<Document> getNotificationCollection(){
        return db.getCollection("Notifications");
    }


    public static MongoCollection<Document> getSequenceCollection(){
        return db.getCollection("sequences");
    }

}

