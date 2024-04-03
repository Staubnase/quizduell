package com.itech;

import java.util.ArrayList;
import java.util.List;

public class DatenbankManager {
    private List<Benutzerkonto> benutzerkonten = new ArrayList<>();

    public DatenbankManager() {
        // Initialisiere mit einigen Daten
        benutzerkonten.add(new Benutzerkonto("Spieler1", 20));
        benutzerkonten.add(new Benutzerkonto("Spieler2", 15));
    }

    public List<Benutzerkonto> holeAlleBenutzerkonten() {
        return new ArrayList<>(benutzerkonten); // Rückgabe einer Kopie der Liste
    }

    public void aktualisiereOderFuegeHinzu(Benutzerkonto konto) {
        for (int i = 0; i < benutzerkonten.size(); i++) {
            if (benutzerkonten.get(i).getBenutzername().equals(konto.getBenutzername())) {
                benutzerkonten.set(i, konto);
                return;
            }
        }
        benutzerkonten.add(konto);
    }
}
