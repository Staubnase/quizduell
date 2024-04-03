package com.itech;

import java.util.List;

import com.itech.interfaces.Benutzerinterface;

public class Singleplayer implements SpielModus {
    private Benutzerinterface ui;
    private List<Frage> fragen;
    
    public Singleplayer(Benutzerinterface ui, List<Frage> fragen) {
        this.ui = ui;
        this.fragen = fragen;
    }

    @Override
    public void starteSpiel() {
        ui.zeigeNachricht("Willkommen zum Singleplayer-Modus!");
        // Implementiere die Singleplayer-spezifische Spiellogik
        // Beispielsweise das Durchgehen der Fragen und das Sammeln der Punkte
    }
}
