package org.dwescbm;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private String content;
    private LocalDateTime timestamp;

    public Comment(String content) {
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
}
