package com.itech;

import com.itech.interfaces.Benutzerinterface;
import java.util.List;

public class Spiel {
    private List<Frage> fragen;
    private int aktuelleFrageIndex = 0;
    private int punkte = 0;
    private Benutzerinterface ui;

    public Spiel(Benutzerinterface ui, List<Frage> fragen) {
        this.ui = ui;
        this.fragen = fragen;
    }

    public void starteSpiel() {
        ui.zeigeNachricht("Willkommen beim Quizduell!");
        while (aktuelleFrageIndex < fragen.size()) {
            zeigeAktuelleFrageUndWerteAus();
            aktuelleFrageIndex++;
        }
        zeigeEndeSpiel();
    }

    private void zeigeAktuelleFrageUndWerteAus() {
        Frage frage = fragen.get(aktuelleFrageIndex);
        ui.zeigeNachricht(frage.getFrageText());

        List<String> antworten = frage.getAntworten();
        for (int i = 0; i < antworten.size(); i++) {
            ui.zeigeNachricht((i + 1) + ": " + antworten.get(i));
        }

        int antwortIndex = Integer.parseInt(ui.leseEingabe()) - 1;
        if (frage.istRichtigeAntwort(antwortIndex)) {
            punkte++;
            ui.zeigeNachricht("Richtig!\n");
        } else {
            ui.zeigeNachricht("Falsch! Die richtige Antwort war: " + (frage.istRichtigeAntwort(antwortIndex) ? 1 : 0) + "\n");
        }
    }

    private void zeigeEndeSpiel() {
        ui.zeigeNachricht("Spiel beendet. Deine Punktzahl: " + punkte + " von " + fragen.size());
    }
}