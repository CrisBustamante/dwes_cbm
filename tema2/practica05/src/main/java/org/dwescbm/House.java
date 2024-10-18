package org.dwescbm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class House {

    private int id_house;
    private String houseName;
    private String founderName;
    private String houseLeader;
    private String houseGhost;
}
