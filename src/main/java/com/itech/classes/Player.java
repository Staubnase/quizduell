package com.itech.classes;

public class Player {
    private String username;
    private int score;

    public Player(String username) {
        this.username = username;
        this.score = 0;
    }

    public void updateScore(int points) {
        this.score += points;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
    
}

