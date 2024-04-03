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

public class SinglePlayerMode implements IGameMode {
    private QuestionDB questionDB;
    private Player player;
    private IGraphicalUI ui;
    private Random random;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int totalQuestions = 10;

    public SinglePlayerMode(QuestionDB questionDB, Player player, IGraphicalUI ui) {
        this.questionDB = questionDB;
        this.player = player;
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
    private void presentNextQuestion() {
        if (currentQuestionIndex < totalQuestions && currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            ui.showQuestion(question, currentQuestionIndex, player);
            currentQuestionIndex++;
        } else {
            endGame();
        }
    }

    public void checkAnswer(int answerIndex) {
        Question currentQuestion = questions.get(currentQuestionIndex - 1); // da currentQuestionIndex bereits inkrementiert wurde
        if (currentQuestion.isCorrectAnswer(answerIndex)) {
            player.updateScore(1);
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
        ui.showResults(player.getScore());
        //JOptionPane.showMessageDialog(null, "Spiel beendet. Ihre Punktzahl: " + player.getScore());
        Leaderboard.addPlayer(player);
        ui.showMenu();
    }

    // Diese Methode wird nicht mehr benötigt und kann entfernt oder als deprecated markiert werden.
    @Override
    public void presentQuestion(int questionIndex) {
        // Wird nicht verwendet.
    }
}
