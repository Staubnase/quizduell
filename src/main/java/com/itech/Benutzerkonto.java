package com.itech;

public class Benutzerkonto {
    private String benutzername;
    private int punkte;

    public Benutzerkonto(String benutzername, int punkte) {
        this.benutzername = benutzername;
        this.punkte = punkte;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }
}

