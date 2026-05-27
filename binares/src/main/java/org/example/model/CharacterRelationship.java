/**
 * Data model that defines the relationship state and the history
 * of specific interactions with other characters in the simulation.
 */
package org.example.model;

import java.time.LocalDateTime;

/**
 * Represents the relationship between TWO specific characters.
 * File name example: Lance_eli.json or Elisabet_Rebeca.json
 *
 * The file name is decided by the characters and must be: character1_character2.json
 */
public class CharacterRelationship {
    private String character1;          // First character (e.g.: Lance)
    private String character2;          // Second character (e.g.: Elisabet)
    private int affection;              // -100 to 100
    private int trust;                  // 0-100
    private String history;             // Summary of their relationship
    private String lastConversation;    // Last thing they talked about
    private LocalDateTime lastInteraction; // When was the last interaction

    // Constructors
    public CharacterRelationship() {
    }

    public CharacterRelationship(String character1, String character2) {
        this.character1 = character1;
        this.character2 = character2;
        this.affection = 0;
        this.trust = 0;
    }

    // Getters and Setters
    public String getCharacter1() { return character1; }
    public void setCharacter1(String character1) { this.character1 = character1; }

    public String getCharacter2() { return character2; }
    public void setCharacter2(String character2) { this.character2 = character2; }

    public int getAffection() { return affection; }
    public void setAffection(int affection) { this.affection = Math.max(-100, Math.min(100, affection)); }

    public int getTrust() { return trust; }
    public void setTrust(int trust) { this.trust = Math.max(0, Math.min(100, trust)); }

    public String getHistory() { return history; }
    public void setHistory(String history) { this.history = history; }

    public String getLastConversation() { return lastConversation; }
    public void setLastConversation(String lastConversation) { this.lastConversation = lastConversation; }

    public LocalDateTime getLastInteraction() { return lastInteraction; }
    public void setLastInteraction(LocalDateTime lastInteraction) { this.lastInteraction = lastInteraction; }

    // Utility methods
    public void modifyAffection(int change) {
        this.affection = Math.max(-100, Math.min(100, this.affection + change));
    }

    public void modifyTrust(int change) {
        this.trust = Math.max(0, Math.min(100, this.trust + change));
    }

    /**
     * Generates the JSON file name for this relationship.
     * Example: "Lance_Elisabet.json"
     */
    public String generateFileName() {
        return character1 + "_" + character2 + ".json";
    }

    @Override
    public String toString() {
        return "CharacterRelationship{" +
                character1 + " ↔ " + character2 +
                ", affection=" + affection +
                ", trust=" + trust +
                '}';
    }
}