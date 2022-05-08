package ru.hoty.collection;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Integer x; //Максимальное значение поля: 846, Поле не может быть null
    private Float y; //Максимальное значение поля: 672, Поле не может быть null

    public Coordinates(Integer x, Float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "X: " + x + "; Y: " + y;
    }

    public Integer getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public int compareTo(Coordinates other) {
        if(getX() * getX() + getY() * getY() ==
                other.getX() * other.getX() + other.getY() * other.getY()) {
            return 0;
        }
        return (getX() * getX() + getY() * getY() >
                other.getX() * other.getX() + other.getY() * other.getY() ? 1 : -1);
    }
}