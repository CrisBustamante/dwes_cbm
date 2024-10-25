package org.dwescbm;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private ObjectId id;
    private String name;
    private String status;
    private int age;
    private LocalDate since;
    private List<Post> posts;
    private List<Profile> friends;

    // Constructor personalizado (sin Lombok) para inicializar una instancia con valores por defecto
    public Profile(String name, String status, int age) {
        this.id = new ObjectId();
        this.name = name;
        this.status = status;
        this.age = age;
        this.since = LocalDate.now();
        this.posts = new ArrayList<>();
        this.friends = new ArrayList<>();
    }
}
