/**
 * Data model to keep track of the latest conversations
 * and the character's short-term actions. It serves as immediate memory.
 */
package org.example.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Character's conversation and action history
 */
public class ConversationHistory {
    private String idCharacter;
    private List<Event> events;

    public static class Event {
        private LocalDateTime timestamp;
        private String content;
        private String interlocutorCharacter; // Who did he/she speak to or interact with (null if it is an action)

        public Event(LocalDateTime timestamp, String content, String interlocutorCharacter) {
            this.timestamp = timestamp;
            this.content = content;
            this.interlocutorCharacter = interlocutorCharacter;
        }

        // Getters and Setters
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        public String getInterlocutorCharacter() { return interlocutorCharacter; }
        public void setInterlocutorCharacter(String interlocutorCharacter) { this.interlocutorCharacter = interlocutorCharacter; }

        /**
         * Determines whether it is a conversation (has interlocutorCharacter) or an action (null)
         */
        public boolean isConversation() {
            return interlocutorCharacter != null;
        }

        @Override
        public String toString() {
            if (isConversation()) {
                return "[" + timestamp + "] " + interlocutorCharacter + ": " + content;
            } else {
                return "[" + timestamp + "] ACTION: " + content;
            }
        }
    }

    // Constructors
    public ConversationHistory() {
        this.events = new ArrayList<>();
    }

    public ConversationHistory(String idCharacter) {
        this.idCharacter = idCharacter;
        this.events = new ArrayList<>();
    }

    // Getters and Setters
    public String getIdCharacter() { return idCharacter; }
    public void setIdCharacter(String idCharacter) { this.idCharacter = idCharacter; }

    public List<Event> getEvents() { return events; }
    public void setEvents(List<Event> events) { this.events = events; }

    // Utility methods
    public void addEvent(Event event) {
        this.events.add(event);
    }

    public void addConversation(String content, String interlocutorCharacter) {
        this.events.add(new Event(LocalDateTime.now(), content, interlocutorCharacter));
    }

    public void addAction(String content) {
        this.events.add(new Event(LocalDateTime.now(), content, null));
    }

    public List<Event> getLastEvents(int count) {
        int start = Math.max(0, events.size() - count);
        return events.subList(start, events.size());
    }

    @Override
    public String toString() {
        return "ConversationHistory{" +
                "idCharacter='" + idCharacter + '\'' +
                ", events=" + events.size() +
                '}';
    }
}