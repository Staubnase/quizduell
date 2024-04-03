package com.itech;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.itech.GameModes.MultiPlayerMode;
import com.itech.GameModes.SinglePlayerMode;
import com.itech.UI.ConsoleUI;
import com.itech.UI.GUI;
import com.itech.classes.Leaderboard;
import com.itech.classes.Player;
import com.itech.classes.Question;
import com.itech.classes.QuestionDB;
import com.itech.interfaces.IGameMode;
import com.itech.interfaces.IGraphicalUI;

public class QuizDuellApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final QuestionDB questionDB = new QuestionDB();
    private static IGraphicalUI ui;
    private static final Leaderboard leaderboard = new Leaderboard();
    private static final Map<String, Player> registeredPlayers = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Willkommen bei Quizduell!");
        System.out.println("Möchten Sie in der Konsole (C) oder im GUI (G) spielen?");
        String choice = scanner.next();
        if ("G".equalsIgnoreCase(choice)) {
            ui = new GUI();
        } else if ("C".equalsIgnoreCase(choice)) {
            ui = new ConsoleUI();
        } else {
            System.out.println("Ungültige Eingabe. Beende das Spiel.");
            return;
        }
        boolean running = true;

        while (running) {
            ui.showMenu();
            int userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1: // Einzelspieler
                    System.out.println("");
                    Player singlePlayer = getOrCreatePlayer(scanner, registeredPlayers);
                    IGameMode singlePlayerMode = new SinglePlayerMode(questionDB, singlePlayer, ui);
                    singlePlayerMode.startGame();
                    leaderboard.addPlayer(singlePlayer);
                    System.out.println("");
                    break;
                case 2: // Mehrspieler
                    System.out.println("");
                    System.out.println("Spieler 1:");
                    Player player1 = getOrCreatePlayer(scanner, registeredPlayers);
                    System.out.println("Spieler 2:");
                    Player player2 = getOrCreatePlayer(scanner, registeredPlayers);
                    IGameMode multiplayerMode = new MultiPlayerMode(questionDB, player1, player2, ui);
                    multiplayerMode.startGame();
                    leaderboard.addPlayer(player1);
                    leaderboard.addPlayer(player2);
                    System.out.println("");
                    break;
                case 3: // Rangliste anzeigen
                    System.out.println("");
                    ui.showLeaderboard(leaderboard);
                    System.out.println("");
                    break;
                case 4: // Spieler verwalten (Platzhalter)
                    System.out.println("");
                    managePlayers();
                    System.out.println("");
                    break;
                case 5: // Fragen verwalten
                    manageQuestions();
                    break;
                case 6: // Spiel beenden
                    running = false;
                    break;
                default:
                    System.out.println("");
                    System.out.println("Ungültige Auswahl. Bitte wählen Sie eine gültige Option.");
                    System.out.println("");
            }
        }

        System.out.println("Danke fürs Spielen!");
        scanner.close();
    }

     private static void manageQuestions() {
        boolean managing = true;

        while (managing) {
            System.out.println("Fragen verwalten:");
            System.out.println("1. Frage hinzufügen");
            System.out.println("2. Frage entfernen");
            System.out.println("3. Alle Fragen anzeigen");
            System.out.println("4. Zurück zum Hauptmenü");
            System.out.print("Wählen Sie eine Option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addQuestion();
                    break;
                case 2:
                    removeQuestion();
                    break;
                case 3:
                    showAllQuestions();
                    break;
                case 4:
                    managing = false;
                    break;
                default:
                    System.out.println("Ungültige Auswahl. Bitte wählen Sie eine gültige Option.");
            }
        }
    }

    private static void addQuestion() {
        System.out.print("Geben Sie die Frage ein: ");
        String questionText = scanner.nextLine();

        String[] options = new String[4];
        for (int i = 0; i < 4; i++) {
            System.out.print("Geben Sie Antwort " + (i + 1) + " ein: ");
            options[i] = scanner.nextLine();
        }

        System.out.print("Geben Sie die Nummer der richtigen Antwort (1-4) ein: ");
        int correctAnswer = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        Question question = new Question(null, questionText, options, correctAnswer);
        questionDB.addQuestion(question);
        System.out.println("Frage erfolgreich hinzugefügt.");
    }

    private static void removeQuestion() {
        System.out.print("Geben Sie die ID der zu entfernenden Frage ein: ");
        int questionId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        questionDB.removeQuestion(questionId);
        System.out.println("Frage erfolgreich entfernt.");
    }

    private static void showAllQuestions() {
        List<Question> questions = questionDB.getAllQuestions();
        System.out.println("Alle Fragen:");
        for (Question question : questions) {
            System.out.println("ID: " + question.getId());
            System.out.println("Frage: " + question.getQuestionText());
            String[] options = question.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }
            System.out.println("Korrekte Antwort: " + (question.getCorrectAnswerIndex() + 1));
            System.out.println();
        }
    }

    private static void managePlayers() {
        System.out.println("");
        System.out.println("1. Spieler hinzufügen");
        System.out.println("2. Spieler löschen");
        System.out.println("3. Alle Spieler anzeigen");
        System.out.print("Wählen Sie eine Option: ");
        System.out.println("");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                // Direkter Aufruf von getOrCreatePlayer, da der Benutzername innerhalb der
                // Methode abgefragt wird.
                System.out.println("Geben Sie den Benutzernamen des neuen Spielers ein:");
                getOrCreatePlayer(scanner, registeredPlayers);
                break;
            case 2:
                System.out.println("Geben Sie den Benutzernamen des zu löschenden Spielers ein:");
                String deleteUsername = scanner.next();
                if (registeredPlayers.remove(deleteUsername) != null) {
                    System.out.println("Spieler " + deleteUsername + " wurde gelöscht.");
                } else {
                    System.out.println("Spieler nicht gefunden.");
                }
                break;
            case 3:
                System.out.println("Registrierte Spieler:");
                registeredPlayers.keySet().forEach(System.out::println);
                break;
            default:
                System.out.println("Ungültige Auswahl.");
        }
    }

    private static Player getOrCreatePlayer(Scanner scanner, Map<String, Player> registeredPlayers) {
        System.out.print("Bitte geben Sie Ihren Benutzernamen ein: ");
        String username = scanner.next().trim();
        return registeredPlayers.computeIfAbsent(username, uname -> new Player(uname));
    }

}
