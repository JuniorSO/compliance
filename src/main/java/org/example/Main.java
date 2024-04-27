package org.example;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;

public class Main {
    private static JFrame regisFrame;
    private static MongoCollection<Document> regisCollection;
    private static MongoCollection<Document> adminCollection;
    private static final Dotenv dotenv = Dotenv.load();
    private static final Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
    @SuppressWarnings("rawtypes")
    private static final Map paramsImagem = ObjectUtils.asMap(
            "use_filename", true,
            "unique_filename", false,
            "overwrite", true
    );
    @SuppressWarnings("rawtypes")
    private static final Map paramsVideo = ObjectUtils.asMap(
            "resource_type", "video",
            "use_filename", true,
            "unique_filename", false,
            "overwrite", true
    );

    public static void main(String[] args) {
        cloudinary.config.secure = true;

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

        regisFrame = new JFrame("Regis");
        regisFrame.setContentPane(new Regis().getRegisPanel());
        regisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        regisFrame.pack();
        regisFrame.setVisible(true);
    }

    public static void insertDoc(String autor, String assunto, String desc, File arquivo) {
        String link;
        String tipo;

        if(arquivo != null) {

            if(arquivo.getName().endsWith(".mp4")) {
                try {
                    link = cloudinary.uploader().upload(arquivo, paramsVideo).get("url").toString();
                    tipo = "Vídeo";
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                try {
                    link = cloudinary.uploader().upload(arquivo, paramsImagem).get("url").toString();
                    tipo = "Imagem";
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            link = null;
            tipo = null;
        }

        Document doc = new Document("_id", new ObjectId())
                .append("autor", autor)
                .append("assunto", assunto)
                .append("descricao", desc)
                .append("data", LocalDateTime.now())
                .append("midia", link)
                .append("tipo_midia", tipo);

        try {
            regisCollection.insertOne(doc);
            JOptionPane.showMessageDialog(null, "Registro enviado.");
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível enviar o registro.");
        }
    }

    public static boolean getLogin(String login, String password) {
        for(int i = 0; i < 3; i++) {
            password = Base64.getEncoder().encodeToString(password.getBytes());
        }

        Document doc = new Document().append("login", login).append("senha", password);

        try {
            Document loginAndPassword = adminCollection.find(doc).first();

            assert loginAndPassword != null;
            return loginAndPassword.get("login").equals(login) && loginAndPassword.get("senha").equals(password);
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public static FindIterable<Document> getDocs() {
        return regisCollection.find();
    }

    public static Document getSpecificDoc(ObjectId id) {
        return regisCollection.find(Filters.eq("_id", id)).first();
    }

    public static long countDocs() {
        return regisCollection.countDocuments();
    }

    public static void deleteDoc(ObjectId id, String link, String tipo) {
        try {
            regisCollection.deleteOne(Filters.eq("_id", id));
        }
        catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Documento não existe.");
        }

        if(link != null && tipo != null) {

            String[] partes = link.split("/");
            String idMidia = partes[7].split("\\.")[0];

            if(tipo.equals("Vídeo")) {
                try {
                    cloudinary.uploader().destroy(idMidia, paramsVideo);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                try {
                    cloudinary.uploader().destroy(idMidia, paramsImagem);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static JFrame getRegisFrame() {
        return regisFrame;
    }
}