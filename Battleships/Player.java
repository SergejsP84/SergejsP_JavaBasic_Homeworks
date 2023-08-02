package lesson5.Battleships;

public class Player {

    private String name;
    private Field field;

    public Player(String name, Field field) {
        this.name = name;
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public Field getField() {
        return field;
    }
}
