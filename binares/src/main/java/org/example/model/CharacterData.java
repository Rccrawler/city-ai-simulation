/**
 * Data model that represents the current physical and temporal attributes
 * of the character (age, basic needs, location, etc.).
 */
package org.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents the main data of a character
 */
public class CharacterData {
    private String FullName;
    private LocalDate DateBirth;
    private int BirthDay;
    private int BirthMonth;
    private String gender;
    private int age;
    private String personality;
    private String parents;

    // Physical state
    private int health; // 0-100
    private int dream; // 0-100
    private LocalDateTime lastAsleep;
    private LocalDateTime lastShower;
    private LocalDateTime lastDefecation;
    private LocalDateTime lastNutrition;

    // Ubicación
    private String locationExact;

    // Constructores
    public CharacterData() {
    }

    public CharacterData(String FullName, LocalDate DateBirth, String gender, String personality, String parents) {
        this.FullName = FullName;
        this.DateBirth = DateBirth;
        if (DateBirth != null) {
            this.BirthDay = DateBirth.getDayOfMonth();
            this.BirthMonth = DateBirth.getMonthValue();
        }
        this.gender = gender;
        this.personality = personality;
        this.parents = parents;
        this.health = 100;
        this.dream = 100;
        this.locationExact = "Unknown";
    }

    // Getters y Setters
    public String getFullName() { return FullName; }
    public void setFullName(String fullName) { this.FullName = fullName; }

    public LocalDate getDateBirth() { return DateBirth; }
    public void setDateBirth(LocalDate dateBirth) {
        this.DateBirth = dateBirth;
        if (dateBirth != null) {
            this.BirthDay = dateBirth.getDayOfMonth();
            this.BirthMonth = dateBirth.getMonthValue();
        }
    }

    public int getBirthDay() { return BirthDay; }
    public void setBirthDay(int diaNacimiento) { this.BirthDay = diaNacimiento; }

    public int getBirthMonth() { return BirthMonth; }
    public void setBirthMonth(int birthMonth) { this.BirthMonth = birthMonth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getPersonality() { return personality; }
    public void setPersonality(String personality) { this.personality = personality; }

    public String getParents() { return parents; }
    public void setParents(String parents) { this.parents = parents; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = Math.max(0, Math.min(100, health)); }

    public int getDream() { return dream; }
    public void setDream(int dream) { this.dream = Math.max(0, Math.min(100, dream)); }

    public LocalDateTime getLastAsleep() { return lastAsleep; }
    public void setLastAsleep(LocalDateTime lastAsleep) { this.lastAsleep = lastAsleep; }

    public LocalDateTime getLastShower() { return lastShower; }
    public void setLastShower(LocalDateTime lastShower) { this.lastShower = lastShower; }

    public LocalDateTime getLastDefecation() { return lastDefecation; }
    public void setLastDefecation(LocalDateTime lastDefecation) { this.lastDefecation = lastDefecation; }

    public LocalDateTime getLastNutrition() { return lastNutrition; }
    public void setLastNutrition(LocalDateTime lastNutrition) { this.lastNutrition = lastNutrition; }

    public String getLocationExact() { return locationExact; }
    public void setLocationExact(String locationExact) { this.locationExact = locationExact; }

    @Override
    public String toString() {
        return "CharacterData{" +
                "FullName='" + FullName + '\'' +
                ", age=" + age +
                ", health=" + health +
                ", dream=" + dream +
                ", location='" + locationExact + '\'' +
                '}';
    }
}
