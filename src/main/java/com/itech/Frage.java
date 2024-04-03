package com.itech;

import java.util.List;

public class Frage {
    private String frageText;
    private List<String> antworten;
    private int korrekteAntwortIndex;

    public Frage(String frageText, List<String> antworten, int korrekteAntwortIndex) {
        this.frageText = frageText;
        this.antworten = antworten;
        this.korrekteAntwortIndex = korrekteAntwortIndex;
    }

    public String getFrageText() {
        return frageText;
    }

    public List<String> getAntworten() {
        return antworten;
    }

    public boolean istRichtigeAntwort(int antwortIndex) {
        return antwortIndex == korrekteAntwortIndex;
    }
}
