package ru.hoty.collection;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import ru.hoty.utils.TextFormatter;

public class Ticket implements Comparable<Ticket>, Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float price; //Поле не может быть null, Значение поля должно быть больше 0
    private String comment; //Поле может быть null
    private TicketType type; //Поле может быть null
    private Venue venue; //Поле не может быть null

    public Ticket() {
        id = null;
        name = null;
        coordinates = null;
        creationDate = null;
        price = null;
        comment = null;
        type = null;
        venue = null;
    }

    public Ticket(Long tId, String tName, Coordinates tCoordinates, LocalDateTime tCreationDate, 
                  Float tPrice, String tComment, TicketType tType, Venue tVenue) {
        id = tId;
        name = tName;
        coordinates = tCoordinates;
        creationDate = tCreationDate;
        price = tPrice;
        comment = tComment;
        type = tType;
        venue = tVenue; 
    }

    public void setId(Long tId) {
        id = tId;
    }

    public void setName(String tName) {
        name = tName;
    }

    public void setCoordinates(Coordinates tCoordinates) {
        coordinates = tCoordinates;
    }

    public void setCreationDate(LocalDateTime tCreationDate) {
        creationDate = tCreationDate;
    }

    public void setPrice(Float tPrice) {
        price = tPrice;
    }

    public void setComment(String tComment) {
        comment = tComment;
    }

    public void setType(TicketType tType) {
        type = tType;
    }

    public void setVenue(Venue tVenue) {
        venue = tVenue;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Float getPrice() {
        return price;
    }

    public String getComment() {
        return comment;
    }

    public TicketType getType() {
        return type;
    }

    public Venue getVenue() {
        return venue;
    }

    @Override
    public String toString() {
        return  TextFormatter.toGreen("\n----------------------------" + name + "----------------------------\n") +
                "Id:\t\t\t" + TextFormatter.toPurple(id + "\n") +
                "Coordinates:\t\t" + TextFormatter.toPurple(coordinates + "\n") +
                "Creation date:\t" + TextFormatter.toPurple(
                    creationDate.format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")) + "\n") +
                "Price:\t\t" + TextFormatter.toPurple(price + "\n") +
                "Comment:\t\t" + TextFormatter.toPurple(comment + "\n") +
                "Ticket type:\t\t" + TextFormatter.toPurple(type + "\n") +
                "Venue:\t\t" + TextFormatter.toPurple(venue + "\n") +
                TextFormatter.toGreen("----------------------------" + name + "----------------------------\n");
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, price, comment, type, venue);
    }

    @Override
    public boolean equals(Object otherObj) {
        if(this == otherObj) return true;

        if(otherObj == null) return false;

        if(!(otherObj instanceof Ticket)) return false;

        Ticket other = (Ticket) otherObj;

        if(((this.getPrice() == null) && (other.getPrice() != null)) || 
           ((this.getPrice() != null) && (other.getPrice() == null))) {
            return false;
        }

        if(((this.getType() == null) && (other.getType() != null)) ||
           ((this.getType() != null) && (other.getType() == null))) {
            return false;
        }

        return (this.getName().equals(other.getName())
            && this.getCoordinates().equals(other.getCoordinates())
            && this.getCreationDate().equals(other.getCreationDate())
            && this.getPrice().equals(other.getPrice())
            && ((this.getComment().equals(other.getComment())) ||
                (this.getComment() == null && other.getComment() == null))
            && ((this.getType().equals(other.getType())) ||
                (this.getType() == null && other.getType() == null))
            && this.getVenue().equals(other.getVenue()));
    }

    @Override
    public int compareTo(Ticket other) {
        if(getId() > other.getId()) return 1;
        if(getId() < other.getId()) return -1;
        return getCreationDate().compareTo(other.getCreationDate());
    }
}