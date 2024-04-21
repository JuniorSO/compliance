package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;

import javax.swing.*;

public class Main {
    public static JFrame regisFrame;

    public static void main(String[] args) {
        String user = "";
        String password = "";
        String connection = "";

        ConnectionString connectionString = new ConnectionString("mongodb+srv://" + user + ":" + password + connection);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
                .build();

        MongoClient mongoClient = MongoClients.create(settings);

        Document socorro = mongoClient.getDatabase("Compliance").getCollection("admin").find().first();
        System.out.println(socorro.get("login"));

        if(regisFrame == null) {
            regisFrame = new JFrame("Regis");
            regisFrame.setContentPane(new Regis().Regis);
            regisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            regisFrame.pack();
            regisFrame.setVisible(true);
        }
        else {
            regisFrame.setVisible(true);
        }
    }
}