package com.itech;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import com.itech.interfaces.Benutzerinterface;
import com.itech.interfaces.GUI;
import com.itech.interfaces.KonsolenUI;

public class Main {
    public static void main(String[] args) {
        Benutzerinterface ui = new KonsolenUI();
        DatenbankManager dbManager = new DatenbankManager();
        Authentifizierungsservice authService = new Authentifizierungsservice(dbManager);

        ui.zeigeNachricht("Willkommen beim Quizduell!\n1: Singleplayer\n2: Multiplayer");
        String modus = ui.leseEingabe();
        List<String> spielerNamen = new ArrayList<>();

        if ("2".equals(modus)) {
            // Multiplayer-Modus
            for (int i = 1; i <= 2; i++) {
                ui.zeigeNachricht("Benutzername für Spieler " + i + ":");
                String benutzername = ui.leseEingabe();
                authService.registriereBenutzer(benutzername); // Registriert den Benutzer, falls nicht vorhanden
                spielerNamen.add(benutzername);
            }
        } else {
            // Singleplayer-Modus
            ui.zeigeNachricht("Benutzername:");
            String benutzername = ui.leseEingabe();
            authService.registriereBenutzer(benutzername); // Registriert den Benutzer, falls nicht vorhanden
            spielerNamen.add(benutzername);
        }

        // Fortfahren mit dem Spielstart...
        // Hier könnte man zum Beispiel die Punkte der Spieler laden und anzeigen
        final Benutzerinterface finalUi = ui;
        final Authentifizierungsservice finalAuthService = authService;

        for (String name : spielerNamen) {
            int punkte = finalAuthService.holePunkte(name);
            finalUi.zeigeNachricht("Punkte für " + name + ": " + punkte);
        }

        // Entscheide, welches Benutzerinterface verwendet werden soll
        //Benutzerinterface ui = args.length > 0 && args[0].equals("gui") ? new GUI() : new KonsolenUI();

        // Initialisiere die Fragen für das Quiz
        List<Frage> fragen = new ArrayList<>();
        fragen.add(new Frage("Was ist die Hauptstadt von Frankreich?", Arrays.asList("Berlin", "Paris", "Rom", "Madrid"), 1));
        fragen.add(new Frage("Wie viele Kontinente gibt es?", Arrays.asList("Fünf", "Sechs", "Sieben", "Acht"), 2));
        fragen.add(new Frage("Wer schrieb 'Faust'?", Arrays.asList("Johann Wolfgang von Goethe", "Friedrich Schiller", "William Shakespeare", "Molière"), 0));

        // Erstelle eine Spielinstanz mit dem gewählten Benutzerinterface und den initialisierten Fragen
        Spiel spiel = new Spiel(ui, fragen);

        // Starte das Spiel
        spiel.starteSpiel();
    }
}