package com.itech;

import java.util.List;

import com.itech.interfaces.Benutzerinterface;

public class Multiplayer implements SpielModus {
    private Benutzerinterface ui;
    private List<Frage> fragen;
    private String spieler1;
    private String spieler2;
    
    public Multiplayer(Benutzerinterface ui, List<Frage> fragen, String spieler1, String spieler2) {
        this.ui = ui;
        this.fragen = fragen;
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
    }

    @Override
    public void starteSpiel() {
        ui.zeigeNachricht("Willkommen zum Multiplayer-Modus!");
        // Implementiere die Multiplayer-spezifische Spiellogik
        // Beispielsweise das Durchgehen der Fragen mit beiden Spielern und das Sammeln der Punkte
    }
}
