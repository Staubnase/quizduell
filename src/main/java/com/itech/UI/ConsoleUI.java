package com.itech.UI;

import java.util.Scanner;

import com.itech.classes.Leaderboard;
import com.itech.classes.Player;
import com.itech.classes.Question;
import com.itech.interfaces.IGraphicalUI;

public class ConsoleUI implements IGraphicalUI {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void showMenu() {
        System.out.println("");
        System.out.println("Willkommen beim Quizduell!");
        System.out.println("1. Einzelspieler");
        System.out.println("2. Mehrspieler");
        System.out.println("3. Rangliste anzeigen");
        System.out.println("4. Spieler verwalten");
        System.out.println("5. Fragen verwalten");
        System.out.println("6. Spiel Beenden");
        System.out.print("Bitte wählen Sie eine Option: ");
        System.out.println("");
    }

    // Implementieren Sie alle erforderlichen Methoden von IGraphicalUI
    @Override
    public void showQuestion(Question question, int questionNumber, Player currentPlayer) {
        System.out.println("Frage " + (questionNumber + 1) + ": " + question.getQuestionText());
        for (int i = 0; i < question.getOptions().length; i++) {
            System.out.println((i + 1) + " - " + question.getOptions()[i]);
        }
    }

    public int getAnswerFromUser() {
        System.out.print("Bitte wählen Sie eine Antwort (1-4): ");
        int answerIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Konsumieren des Zeilenendes nach der Nummerneingabe
        return answerIndex;
    }

    @Override
    public void showLeaderboard(Leaderboard leaderboard) {
        leaderboard.displayLeaderboard();
    }

    @Override
    public void managePlayers() {
        System.out.println("");
        System.out.println("Spieler verwalten (Hinzufügen/Löschen): ");
    }

    @Override
    public void showResults(int score) {
        System.out.println("");
        System.out.println("Ihr Ergebnis: " + score + " Punkte");
        System.out.println("");
    }
}
