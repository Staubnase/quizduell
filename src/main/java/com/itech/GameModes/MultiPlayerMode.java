package com.itech.GameModes;

import com.itech.classes.Leaderboard;
import com.itech.classes.Player;
import com.itech.classes.Question;
import com.itech.classes.QuestionDB;
import com.itech.interfaces.IGameMode;
import com.itech.interfaces.IGraphicalUI;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

public class MultiPlayerMode implements IGameMode {
    private QuestionDB questionDB;
    private Player player1;
    private Player player2;
    private IGraphicalUI ui;
    private Random random;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int totalQuestions = 20;
    private boolean questionSelected = false;

    public MultiPlayerMode(QuestionDB questionDB, Player player1, Player player2, IGraphicalUI ui) {
        this.questionDB = questionDB;
        this.player1 = player1;
        this.player2 = player2;
        this.ui = ui;
        this.random = new Random();
        this.questions = questionDB.getAllQuestions();
        Collections.shuffle(this.questions); // Mischen der Fragenliste
    }

    @Override
    public void startGame() {
        presentNextQuestion();
    }

    // Diese Methode ersetzt die beiden überladenen presentQuestion-Methoden.
// In der MultiPlayerMode-Klasse

private void presentNextQuestion() {
    if (currentQuestionIndex < totalQuestions && currentQuestionIndex < questions.size()) {
        Player currentPlayer = (currentQuestionIndex % 2 == 0) ? player1 : player2;
        Question question = questions.get(currentQuestionIndex);
        ui.showQuestion(question, currentQuestionIndex, currentPlayer); // Aktuellen Spieler an UI übergeben
        currentQuestionIndex++;
        questionSelected = true;
    } else {
        endGame();
    }
}

    public void checkAnswer(int answerIndex) {
        if (!questionSelected) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie zuerst eine Frage aus.");
            return;
        }

        Question currentQuestion = questions.get(currentQuestionIndex - 1); // da currentQuestionIndex bereits inkrementiert wurde
        if (currentQuestion.isCorrectAnswer(answerIndex)) {
            if (currentQuestionIndex % 2 == 0) {
                player2.updateScore(1);
            } else {
                player1.updateScore(1);
            }
            JOptionPane.showMessageDialog(null, "Richtige Antwort!");
        } else {
            JOptionPane.showMessageDialog(null, "Falsche Antwort.");
        }
    
        if (currentQuestionIndex < totalQuestions && currentQuestionIndex < questions.size()) {
            presentNextQuestion(); // Nächste Frage präsentieren oder Spiel beenden
        } else {
            endGame(); // Wenn alle Fragen beantwortet wurden, beende das Spiel
        }
    }

    @Override
    public void endGame() {
        //ui.showResults(player1.getScore(), player2.getScore());
        //JOptionPane.showMessageDialog(null, "Spiel beendet. Ihre Punktzahl: " + player.getScore());
        Leaderboard.addPlayer(player1);
        Leaderboard.addPlayer(player2);
        ui.showMenu();
    }

    // Diese Methode wird nicht mehr benötigt und kann entfernt oder als deprecated markiert werden.
    @Override
    public void presentQuestion(int questionIndex) {
        // Wird nicht verwendet.
    }
}
