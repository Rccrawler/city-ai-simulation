/**
 * Data model to store the character's complete biography.
 * This information serves as a long-term context anchor for the LLM.
 */
package org.example.model;

/**
 * Biografía del personaje
 */
public class Biography {
    private String idCharacter;
    private String SummarizedText; // Brief biography
    private String background; // Background information
    private String LifeGoals; // What does he/she want to achieve
    private String fears; // Their fears or phobias
    private String strengths; // His strengths of character
    private String weaknesses; // His weaknesses of character

    // Constructores
    public Biography() {
    }

    public Biography(String idCharacter) {
        this.idCharacter = idCharacter;
    }

    // Getters y Setters
    public String getIdCharacter() { return idCharacter; }
    public void setIdCharacter(String idCharacter) { this.idCharacter = idCharacter; }

    public String getSummarizedText() { return SummarizedText; }
    public void setSummarizedText(String SummarizedText) { this.SummarizedText = SummarizedText; }

    public String getBackground() { return background; }
    public void setBackground(String background) { this.background = background; }

    public String getLifeGoals() { return LifeGoals; }
    public void setLifeGoals(String LifeGoals) { this.LifeGoals = LifeGoals; }

    public String getFears() { return fears; }
    public void setFears(String fears) { this.fears = fears; }

    public String getStrengths() { return strengths; }
    public void setStrengths(String strengths) { this.strengths = strengths; }

    public String getWeaknesses() { return weaknesses; }
    public void setWeaknesses(String weaknesses) { this.weaknesses = weaknesses; }

    @Override
    public String toString() {
        return "Biography{" +
                "idCharacter='" + idCharacter + '\'' +
                ", SummarizedText='" + (SummarizedText != null ? SummarizedText.substring(0, Math.min(50, SummarizedText.length())) + "..." : "null") + '\'' +
                '}';
    }
}
