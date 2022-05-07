package ru.hoty.collection;

import java.io.Serializable;

public class Venue implements Serializable {
    private Long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private int capacity; //Значение поля должно быть больше 0

    public Venue(Long id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "ID: " + id + "; Name: " + name + "; Capacity: " + capacity;
    }

    public int compareTo(Venue other) {
        if(getId() > other.getId()) return 1;
        if(getId() < other.getId()) return -1;
        return getName().compareTo(other.getName());
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
    
    public Long getId() {
        return id;
    }
}