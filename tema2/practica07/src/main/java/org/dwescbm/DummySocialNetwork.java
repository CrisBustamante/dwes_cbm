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
                case 3:
                    viewProfilePosts();
                    break;
                case 4:
                    viewProfiles();
                    break;
                case 5:
                    viewPostDetail();
                    break;
                case 6:
                    likePost();
                    break;
                case 7:
                    commentOnPost();
                    break;
                case 8:
                    addFriend();
                    break;
                case 9:
                    viewProfileStats();
                    break;
                case 10:
                    viewNetworkStats();
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 0);
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
        MongoDatabase database = mongoClient.getDatabase("crisdummynetwork");
        profileCollection = database.getCollection("daw2", Profile.class);
    }

    private static int menu() {
        Scanner scanner = new Scanner(System.in);
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su nombre:");
        String name = scanner.nextLine();
        System.out.println("Ingrese su estado:");
        String status = scanner.nextLine();
        System.out.println("Ingrese su edad:");
        int age = scanner.nextInt();

        Profile profile = new Profile(name, status, age);
        profileCollection.insertOne(profile);
        System.out.println("Perfil creado exitosamente. Su ID de perfil es: " + profile.getId());
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

    private static void viewProfilePosts() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el ID del perfil:");
        String profileId = scanner.nextLine();
        Profile profile = profileCollection.find(eq("_id", new ObjectId(profileId))).first();

        if (profile == null) {
            System.out.println("Perfil no encontrado.");
            return;
        }

        System.out.println("Publicaciones de " + profile.getName() + ":");
        for (Post post : profile.getPosts()) {
            System.out.println("Título: " + post.getTitle() + ", Likes: " + post.getLikes());
        }
    }

    private static void viewPostDetail() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el ID del perfil:");
        String profileId = scanner.nextLine();
        Profile profile = profileCollection.find(eq("_id", new ObjectId(profileId))).first();

        if (profile == null) {
            System.out.println("Perfil no encontrado.");
            return;
        }

        System.out.println("Ingrese el título de la publicación:");
        String title = scanner.nextLine();
        Post post = profile.getPosts().stream().filter(p -> p.getTitle().equals(title)).findFirst().orElse(null);

        if (post == null) {
            System.out.println("Publicación no encontrada.");
            return;
        }

        System.out.println("Título: " + post.getTitle());
        System.out.println("Contenido: " + post.getContent());
        System.out.println("Likes: " + post.getLikes());
        System.out.println("Comentarios: " + post.getComments());
    }

    private static void likePost() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el ID del perfil:");
        String profileId = scanner.nextLine();
        Profile profile = profileCollection.find(eq("_id", new ObjectId(profileId))).first();

        if (profile == null) {
            System.out.println("Perfil no encontrado.");
            return;
        }

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

    private static void commentOnPost() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el ID del perfil:");
        String profileId = scanner.nextLine();
        Profile profile = profileCollection.find(eq("_id", new ObjectId(profileId))).first();

        if (profile == null) {
            System.out.println("Perfil no encontrado.");
            return;
        }

        System.out.println("Ingrese el título de la publicación:");
        String title = scanner.nextLine();
        Post post = profile.getPosts().stream().filter(p -> p.getTitle().equals(title)).findFirst().orElse(null);

        if (post == null) {
            System.out.println("Publicación no encontrada.");
            return;
        }

        System.out.println("Ingrese su comentario:");
        String comment = scanner.nextLine();
        post.getComments().add(comment);
        profileCollection.replaceOne(eq("_id", profile.getId()), profile);
        System.out.println("Comentario añadido.");
    }

    private static void addFriend() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su ID de perfil:");
        String profileId = scanner.nextLine();
        Profile profile = profileCollection.find(eq("_id", new ObjectId(profileId))).first();

        if (profile == null) {
            System.out.println("Perfil no encontrado.");
            return;
        }

        System.out.println("Ingrese el ID del perfil del amigo:");
        String friendId = scanner.nextLine();
        Profile friendProfile = profileCollection.find(eq("_id", new ObjectId(friendId))).first();

        if (friendProfile == null) {
            System.out.println("Perfil de amigo no encontrado.");
            return;
        }

        profile.getFriends().add(friendProfile);
        profileCollection.replaceOne(eq("_id", profile.getId()), profile);
        System.out.println("Amigo añadido exitosamente.");
    }

    private static void viewProfileStats() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su ID de perfil:");
        String profileId = scanner.nextLine();
        Profile profile = profileCollection.find(eq("_id", new ObjectId(profileId))).first();

        if (profile == null) {
            System.out.println("Perfil no encontrado.");
            return;
        }

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
        // Aquí puedes implementar la lógica para mostrar los 3 perfiles con más amigos y más comentarios.
    }
}
