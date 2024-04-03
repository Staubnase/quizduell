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

/**
 * Represents the single player game mode in the quiz game.
 * Implements the {@link IGameMode} interface.
 */
public class SinglePlayerMode implements IGameMode {
    private QuestionDB questionDB;
    private Player player;
    private IGraphicalUI ui;
    private Random random;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int totalQuestions = 10;

    /**
     * Constructs a new SinglePlayerMode object.
     * 
     * @param questionDB the QuestionDB object to retrieve questions from
     * @param player the Player object representing the player in the game
     * @param ui the IGraphicalUI object for displaying the game UI
     */
    public SinglePlayerMode(QuestionDB questionDB, Player player, IGraphicalUI ui) {
        this.questionDB = questionDB;
        this.player = player;
        this.ui = ui;
        this.random = new Random();
        this.questions = questionDB.getAllQuestions();
        Collections.shuffle(this.questions); // Mischen der Fragenliste
    }

    /**
     * Starts the single player game.
     * Calls the presentNextQuestion() method to present the first question.
     */
    @Override
    public void startGame() {
        presentNextQuestion();
    }

    /**
     * Presents the next question to the player.
     * If there are more questions available, it shows the question and increments the currentQuestionIndex.
     * If all questions have been answered, it calls the endGame() method.
     */
    private void presentNextQuestion() {
        if (currentQuestionIndex < totalQuestions && currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            ui.showQuestion(question, currentQuestionIndex, player);
            currentQuestionIndex++;
        } else {
            endGame();
        }
    }

    /**
     * Checks the player's answer for the current question.
     * If the answer is correct, it updates the player's score and shows a message dialog.
     * If there are more questions available, it calls the presentNextQuestion() method.
     * If all questions have been answered, it calls the endGame() method.
     * 
     * @param answerIndex the index of the selected answer
     */
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

    /**
     * Ends the single player game.
     * Shows the player's score, adds the player to the leaderboard, and shows the game menu.
     */
    @Override
    public void endGame() {
        ui.showResults(player.getScore());
        //JOptionPane.showMessageDialog(null, "Spiel beendet. Ihre Punktzahl: " + player.getScore());
        Leaderboard.addPlayer(player);
        ui.showMenu();
    }

    /**
     * This method is no longer needed and can be removed or marked as deprecated.
     * 
     * @param questionIndex the index of the question to present
     * @deprecated This method is no longer used.
     */
    @Override
    public void presentQuestion(int questionIndex) {
        // Wird nicht verwendet.
    }
}
