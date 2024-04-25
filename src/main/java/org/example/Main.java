package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Base64;

public class Main {
    public static JFrame regisFrame;
    private static MongoCollection<Document> regisCollection;
    private static MongoCollection<Document> adminCollection;

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
        adminCollection = mongoClient.getDatabase("Compliance").getCollection("admin");

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

    public static boolean getLogin(String login, String password) {
        for(int i = 0; i < 3; i++) {
            password = Base64.getEncoder().encodeToString(password.getBytes());
        }
        Document doc = new Document().append("login", login).append("senha", password);
        try {
            Document loginAndPassword = adminCollection.find(doc).first();

            return loginAndPassword.get("login").equals(login) && loginAndPassword.get("senha").equals(password);
        }
        catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Login ou senha incorretos.");
            return false;
        }
    }

    public static FindIterable<Document> getDocs() {
        return regisCollection.find();
    }

    public static long countDocs() {
        return regisCollection.countDocuments();
    }

    public static void deleteDoc(ObjectId id) {
        regisCollection.deleteOne(Filters.eq("_id", id));
    }
}