package com.itech.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The Leaderboard class represents a leaderboard that keeps track of players and their scores.
 */
public class Leaderboard {
    private static List<Player> players = new ArrayList<>();

    /**
     * Adds a player to the leaderboard.
     *
     * @param player The player to be added.
     */
    public static void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Removes a player from the leaderboard.
     *
     * @param player The player to be removed.
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * Displays the leaderboard in the console.
     * The leaderboard is sorted in descending order based on the players' scores.
     */
    public void displayLeaderboard() {
        System.out.println("Rangliste:");
        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p2.getScore(), p1.getScore()); // Absteigende Sortierung
            }
        });
        for (Player player : players) {
            System.out.println(player.getUsername() + ": " + player.getScore() + " Punkte");
        }
    }
    
    /**
     * Returns a formatted leaderboard string.
     *
     * @return The formatted leaderboard string.
     */
    public String getFormattedLeaderboard() {
        StringBuilder leaderboardString = new StringBuilder();
        // Sortieren der Spieler nach ihrer Punktzahl in absteigender Reihenfolge
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));

        // Erstellen des formatierten Strings
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            leaderboardString.append(i + 1).append(". ").append(player.getUsername()).append(": ").append(player.getScore()).append(" Punkte\n");
        }

        return leaderboardString.toString();
    }
}
