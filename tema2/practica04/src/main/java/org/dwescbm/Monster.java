package org.dwescbm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Monster {

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("alignment")
    private String alignment;

    @JsonProperty("hit_points")
    private int hitPoints;

    @JsonProperty("armor_class")
    private int armorClass;

    @JsonProperty("challenge_rating")
    private String challengeRating;

    // Getters y setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public String getChallengeRating() {
        return challengeRating;
    }

    public void setChallengeRating(String challengeRating) {
        this.challengeRating = challengeRating;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", alignment='" + alignment + '\'' +
                ", hitPoints=" + hitPoints +
                ", armorClass=" + armorClass +
                ", challengeRating='" + challengeRating + '\'' +
                '}';
    }
}
