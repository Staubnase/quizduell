package com.itech;

import java.util.List;

import com.itech.interfaces.Benutzerinterface;
import com.itech.Singleplayer;
import com.itech.Multiplayer;

public class Menue {
    private final Benutzerinterface ui;
    private final Authentifizierungsservice authService;
    private final DatenbankManager dbManager;

    public Menue(Benutzerinterface ui, Authentifizierungsservice authService, DatenbankManager dbManager) {
        this.ui = ui;
        this.authService = authService;
        this.dbManager = dbManager;
    }

    public void zeigeHauptmenue() {
        boolean spielLaeuft = true;

        while (spielLaeuft) {
            ui.zeigeNachricht("\nWillkommen beim Quizduell!");
            ui.zeigeNachricht("1: Singleplayer\n2: Multiplayer\n3: Fragen managen\n4: Beenden");
            String auswahl = ui.leseEingabe();

            switch (auswahl) {
                case "1":
                    starteSingleplayer();
                    break;
                case "2":
                    starteMultiplayer();
                    break;
                case "3":
                    verwalteFragen();
                    break;
                case "4":
                    spielLaeuft = false;
                    ui.zeigeNachricht("Spiel beendet. Bis zum nächsten Mal!");
                    break;
                default:
                    ui.zeigeNachricht("Ungültige Auswahl. Bitte wähle 1, 2, 3 oder 4.");
                    break;
            }
        }
    }

    private void starteSingleplayer() {
        List<Frage> fragen = // Hole Fragen aus Datenbank oder initialisiere sie
        Singleplayer singleplayer = new Singleplayer(ui, fragen);
        singleplayer.starteSpiel();
    }

    private void starteMultiplayer() {
        ui.zeigeNachricht("Benutzername für Spieler 1:");
        String spieler1 = ui.leseEingabe();
        ui.zeigeNachricht("Benutzername für Spieler 2:");
        String spieler2 = ui.leseEingabe();

        List<Frage> fragen = // Hole Fragen aus Datenbank oder initialisiere sie
        Multiplayer multiplayer = new Multiplayer(ui, fragen, spieler1, spieler2);
        multiplayer.starteSpiel();
    }

    private void verwalteFragen() {
        // Implementiere die Logik für die Frageverwaltung
        ui.zeigeNachricht("Fragenverwaltung ausgewählt...");
        // Hier Logik zur Frageverwaltung starten...
    }
}
