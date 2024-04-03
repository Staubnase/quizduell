package com.itech.UI;

import java.util.Scanner;

import com.itech.classes.Leaderboard;
import com.itech.classes.Player;
import com.itech.classes.Question;
import com.itech.interfaces.IGraphicalUI;

/**
 * The ConsoleUI class implements the IGraphicalUI interface and provides a console-based user interface for the Quizduell game.
 */
public class ConsoleUI implements IGraphicalUI {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Displays the main menu options for the Quizduell game.
     */
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

    /**
     * Displays a question to the user during the game.
     *
     * @param question        The question to be displayed.
     * @param questionNumber  The number of the question.
     * @param currentPlayer  The current player.
     */
    @Override
    public void showQuestion(Question question, int questionNumber, Player currentPlayer) {
        System.out.println("Frage " + (questionNumber + 1) + ": " + question.getQuestionText());
        for (int i = 0; i < question.getOptions().length; i++) {
            System.out.println((i + 1) + " - " + question.getOptions()[i]);
        }
    }

    /**
     * Gets the user's answer to a question.
     *
     * @return The index of the selected answer.
     */
    public int getAnswerFromUser() {
        System.out.print("Bitte wählen Sie eine Antwort (1-4): ");
        int answerIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Konsumieren des Zeilenendes nach der Nummerneingabe
        return answerIndex;
    }

    /**
     * Displays the leaderboard.
     *
     * @param leaderboard  The leaderboard to be displayed.
     */
    @Override
    public void showLeaderboard(Leaderboard leaderboard) {
        leaderboard.displayLeaderboard();
    }

    /**
     * Manages the players (addition/deletion).
     */
    @Override
    public void managePlayers() {
        System.out.println("");
        System.out.println("Spieler verwalten (Hinzufügen/Löschen): ");
    }

    /**
     * Displays the results of the game.
     *
     * @param score  The score achieved by the player.
     */
    @Override
    public void showResults(int score) {
        System.out.println("");
        System.out.println("Ihr Ergebnis: " + score + " Punkte");
        System.out.println("");
    }
}
