package org.dwescbm;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.bson.codecs.pojo.PojoCodecProvider;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import java.util.Scanner;

public class DummySocialNetwork {
    private static MongoCollection<Profile> profileCollection;

    public static void main(String[] args) {
        setupMongoClient();
        System.out.println("Bienvenido a DummySocialNetwork");

        int opcion;
        do {
            opcion = menu();
            switch (opcion) {
                case 1:
                    createProfile();
                    break;
                case 2:
                    createPost();
                    break;
                case 4:
                    viewProfiles();
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 0);
    }

    private static void setupMongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://98.84.89.194:5432/dummynetwork");
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(clientSettings);
        MongoDatabase database = mongoClient.getDatabase("dummynetwork");
        profileCollection = database.getCollection("daw2", Profile.class);
    }

    private static int menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione una opción:");
        System.out.println("0. Salir");
        System.out.println("1. Crear mi perfil");
        System.out.println("2. Crear una publicación en mi perfil");
        System.out.println("4. Ver todos los perfiles");
        return scanner.nextInt();
    }

    private static void createProfile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su nombre:");
        String name = scanner.nextLine();
        System.out.println("Ingrese su estado:");
        String status = scanner.nextLine();
        System.out.println("Ingrese su edad:");
        int age = scanner.nextInt();

        Profile profile = new Profile(name, status, age);
        profileCollection.insertOne(profile);
        System.out.println("Perfil creado exitosamente.");
    }

    private static void createPost() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el ID del perfil (como cadena ObjectId):");
        String profileId = scanner.nextLine();

        Profile profile = profileCollection.find(eq("_id", new ObjectId(profileId))).first();
        if (profile == null) {
            System.out.println("Perfil no encontrado.");
            return;
        }

        System.out.println("Ingrese el título de la publicación:");
        String title = scanner.nextLine();
        System.out.println("Ingrese el contenido de la publicación:");
        String content = scanner.nextLine();

        Post post = new Post(title, content);
        profile.getPosts().add(post);
        profileCollection.replaceOne(eq("_id", new ObjectId(profileId)), profile);
        System.out.println("Publicación creada exitosamente.");
    }

    private static void viewProfiles() {
        for (Profile profile : profileCollection.find()) {
            System.out.println("Perfil: " + profile.getName() + ", Edad: " + profile.getAge() + ", Estado: " + profile.getStatus());
        }
    }
}
