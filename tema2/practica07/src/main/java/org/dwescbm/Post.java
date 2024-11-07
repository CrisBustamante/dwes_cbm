package org.dwescbm;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import java.time.LocalDateTime;
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
    private List<Comment> comments;
    @Getter
    private LocalDateTime timestamp;

    public Post(String title, String content) {
        this.id = new ObjectId();
        this.title = title;
        this.content = content;
        this.likes = 0;
        this.comments = new ArrayList<>();
        this.timestamp = LocalDateTime.now();
    }

}
