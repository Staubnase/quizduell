package com.itech;

public class Spieler {
    private String benutzername;
    private int punkte;

    public Spieler(String benutzername) {
        this.benutzername = benutzername;
        this.punkte = 0;
    }

    public void aktualisierePunkte(int punkte) {
        this.punkte += punkte;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public int getPunkte() {
        return punkte;
    }
}