package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import java.time.LocalDateTime;

public class Main {
    public static JFrame regisFrame;
    private static MongoCollection<Document> regisCollection;

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
        regisCollection = mongoClient.getDatabase("Compliance").getCollection("regis");

        if(regisFrame == null) {
            regisFrame = new JFrame("Regis");
            regisFrame.setContentPane(new Regis().Regis);
            regisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            regisFrame.pack();
        }
        regisFrame.setVisible(true);
    }

    public static void insertDoc(String autor, String assunto, String desc) {
        Document doc = new Document("_id", new ObjectId())
                .append("autor", autor)
                .append("assunto", assunto)
                .append("descricao", desc)
                .append("data", LocalDateTime.now());

        regisCollection.insertOne(doc);
        JOptionPane.showMessageDialog(null, "Registro Enviado!");
    }
}