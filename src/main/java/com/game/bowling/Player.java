package com.game.bowling;

/**
 * This class contain players name
 * @author abhishek.ghosh
 */
public class Player {

    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Player [name=" + name + "]";
    }

}
