package com.itech.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Leaderboard {
    private static List<Player> players = new ArrayList<>();

    public static void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

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
