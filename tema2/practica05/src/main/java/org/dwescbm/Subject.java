package org.dwescbm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Subject {

    private int id_subject;
    private String subject_name;
    private String classRoom;
    private boolean mandatory;
}
