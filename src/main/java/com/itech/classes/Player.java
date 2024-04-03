package com.itech.classes;

/**
 * Represents a player in the game.
 */
public class Player {
    private String username;
    private int score;

    /**
     * Constructs a new Player object with the specified username.
     * The initial score is set to 0.
     *
     * @param username the username of the player
     */
    public Player(String username) {
        this.username = username;
        this.score = 0;
    }

    /**
     * Updates the player's score by adding the specified points.
     *
     * @param points the points to be added to the player's score
     */
    public void updateScore(int points) {
        this.score += points;
    }

    /**
     * Returns the username of the player.
     *
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the score of the player.
     *
     * @return the score of the player
     */
    public int getScore() {
        return score;
    }
}

