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
import static com.mongodb.client.model.Filters.and;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.Scanner;

public class DummySocialNetwork {
    private static MongoCollection<Profile> profileCollection;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        setupMongoClient();
        System.out.println("Bienvenido a DummySocialNetwork");

        int opcion;
        do {
            opcion = menu();
            switch (opcion) {
                case 1 -> createProfile();
                case 2 -> createPost();
                case 3 -> viewProfilePosts();
                case 4 -> viewProfiles();
                case 5 -> viewPostDetail();
                case 6 -> likePost();
                case 7 -> commentOnPost();
                case 8 -> addFriend();
                case 9 -> viewProfileStats();
                case 10 -> viewNetworkStats();
                default -> System.out.println("Opción no válida");
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static void setupMongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://98.84.89.194:27017/");
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(clientSettings);
        MongoDatabase database = mongoClient.getDatabase("Albion");
        profileCollection = database.getCollection("Online", Profile.class);
    }

    private static int menu() {
        System.out.println("Seleccione una opción:");
        System.out.println("0. Salir");
        System.out.println("1. Crear mi perfil");
        System.out.println("2. Crear una publicación en mi perfil");
        System.out.println("3. Ver publicaciones de un perfil");
        System.out.println("4. Ver todos los perfiles");
        System.out.println("5. Ver una publicación en detalle");
        System.out.println("6. Dar like a una publicación");
        System.out.println("7. Comentar en una publicación");
        System.out.println("8. Añadir amigos");
        System.out.println("9. Mostrar estadísticas de mi perfil");
        System.out.println("10. Mostrar estadísticas de la red social");
        return scanner.nextInt();
    }

    private static void createProfile() {
        scanner.nextLine();
        System.out.println("Ingrese su nombre:");
        String firstName = scanner.nextLine();
        System.out.println("Ingrese su apellido:");
        String lastName = scanner.nextLine();
        System.out.println("Ingrese su estado:");
        String status = scanner.nextLine();
        System.out.println("Ingrese su edad:");
        int age = scanner.nextInt();

        Profile profile = new Profile(firstName, lastName, status, age);
        profileCollection.insertOne(profile);
        System.out.println("Perfil creado exitosamente. Su ID de perfil es: " + profile.getId());
    }

    private static Profile findProfileByName() {
        scanner.nextLine();
        System.out.println("Ingrese el nombre del perfil:");
        String firstName = scanner.nextLine();
        System.out.println("Ingrese el apellido del perfil:");
        String lastName = scanner.nextLine();

        Profile profile = profileCollection.find(and(eq("firstName", firstName), eq("lastName", lastName))).first();
        if (profile == null) {
            System.out.println("Perfil no encontrado.");
        }
        return profile;
    }

    private static void createPost() {
        Profile profile = findProfileByName();
        if (profile == null) return;

        System.out.println("Ingrese el título de la publicación:");
        String title = scanner.nextLine();
        System.out.println("Ingrese el contenido de la publicación:");
        String content = scanner.nextLine();

        Post post = new Post(title, content);
        profile.getPosts().add(post);
        profileCollection.replaceOne(eq("_id", profile.getId()), profile);
        System.out.println("Publicación creada exitosamente.");
    }

    private static void viewProfiles() {
        for (Profile profile : profileCollection.find()) {
            System.out.println("Perfil: " + profile.getFirstName() + " " + profile.getLastName() + ", Edad: " + profile.getAge() + ", Estado: " + profile.getStatus());
        }
    }

    private static void viewProfilePosts() {
        Profile profile = findProfileByName();
        if (profile == null) return;

        viewProfilePosts(profile);
    }

    private static void viewProfilePosts(Profile profile) {
        System.out.println("Publicaciones de " + profile.getFirstName() + " " + profile.getLastName() + ":");
        for (Post post : profile.getPosts()) {
            System.out.println("Título: " + post.getTitle() + ", Likes: " + post.getLikes() + ", Fecha: " + post.getTimestamp());
        }
    }

    private static void viewPostDetail() {
        Profile profile = findProfileByName();
        if (profile == null) return;

        viewProfilePosts(profile);

        System.out.println("Ingrese el título de la publicación a la que desea acceder en detalle:");
        String title = scanner.nextLine();

        Post post = profile.getPosts().stream().filter(p -> p.getTitle().equals(title)).findFirst().orElse(null);

        if (post == null) {
            System.out.println("Publicación no encontrada.");
            return;
        }

        System.out.println("Título: " + post.getTitle());
        System.out.println("Contenido: " + post.getContent());
        System.out.println("Likes: " + post.getLikes());
        System.out.println("Comentarios: ");
        post.getComments().forEach(comment -> System.out.println(comment.getTimestamp() + ": " + comment.getContent()));
    }

    private static void likePost() {
        Profile profile = findProfileByName();
        if (profile == null) return;

        System.out.println("Ingrese el título de la publicación:");
        String title = scanner.nextLine();
        Post post = profile.getPosts().stream().filter(p -> p.getTitle().equals(title)).findFirst().orElse(null);

        if (post == null) {
            System.out.println("Publicación no encontrada.");
            return;
        }

        post.setLikes(post.getLikes() + 1);
        profileCollection.replaceOne(eq("_id", profile.getId()), profile);
        System.out.println("Like añadido a la publicación.");
    }

    private static void addFriend() {
        System.out.println("Ingrese su perfil para agregar un amigo:");
        Profile profile = findProfileByName();
        if (profile == null) return;

        System.out.println("Ingrese el perfil del amigo:");
        Profile friendProfile = findProfileByName();
        if (friendProfile == null) return;

        profile.getFriends().add(friendProfile);
        profileCollection.replaceOne(eq("_id", profile.getId()), profile);
        System.out.println("Amigo añadido exitosamente.");
    }

    private static void viewProfileStats() {
        Profile profile = findProfileByName();
        if (profile == null) return;

        int totalLikes = profile.getPosts().stream().mapToInt(Post::getLikes).sum();
        int totalComments = profile.getPosts().stream().mapToInt(post -> post.getComments().size()).sum();

        System.out.println("Número de publicaciones: " + profile.getPosts().size());
        System.out.println("Número de amigos: " + profile.getFriends().size());
        System.out.println("Total de likes recibidos: " + totalLikes);
        System.out.println("Total de comentarios recibidos: " + totalComments);
    }

    private static void viewNetworkStats() {
        long totalProfiles = profileCollection.countDocuments();
        long influencers = profileCollection.countDocuments(eq("friends.1", new ObjectId()));

        System.out.println("Número total de perfiles: " + totalProfiles);
        System.out.println("Número de influencers: " + influencers);
    }

    private static void commentOnPost() {
        Profile profile = findProfileByName();
        if (profile == null) return;

        viewProfilePosts(profile);
        System.out.println("Ingrese el título de la publicación a la que desea añadir un comentario:");
        String title = scanner.nextLine();

        Post post = profile.getPosts().stream()
                .filter(p -> p.getTitle().equals(title))
                .findFirst()
                .orElse(null);

        if (post == null) {
            System.out.println("Publicación no encontrada.");
            return;
        }

        System.out.println("Ingrese su comentario:");
        String commentContent = scanner.nextLine();
        Comment comment = new Comment(commentContent);

        post.getComments().add(comment);
        profileCollection.replaceOne(eq("_id", profile.getId()), profile);
        System.out.println("Comentario añadido exitosamente.");
    }
}
