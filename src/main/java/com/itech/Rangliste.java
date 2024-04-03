package com.itech;

import java.util.Comparator;
import java.util.List;

import com.itech.interfaces.Benutzerinterface;

public class Rangliste {
    private DatenbankManager dbManager;

    public Rangliste(DatenbankManager dbManager) {
        this.dbManager = dbManager;
    }

    public void zeigeRangliste(Benutzerinterface ui) {
        List<Benutzerkonto> rangliste = dbManager.holeAlleBenutzerkonten();
        rangliste.sort(Comparator.comparingInt(Benutzerkonto::getPunkte).reversed());

        ui.zeigeNachricht("Rangliste:");
        rangliste.forEach(konto -> ui.zeigeNachricht(konto.getBenutzername() + ": " + konto.getPunkte() + " Punkte"));
    }
}
