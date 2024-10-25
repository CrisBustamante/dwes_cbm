package org.dwescbm;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private ObjectId id;
    private String title;
    private String content;
    private int likes;
    private List<String> comments;

    // Constructor personalizado (sin Lombok) para inicializar una instancia con valores por defecto
    public Post(String title, String content) {
        this.id = new ObjectId();
        this.title = title;
        this.content = content;
        this.likes = 0;
        this.comments = new ArrayList<>();
    }
}
