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
 * Represents the multiplayer game mode in the Quizduell game.
 * Implements the IGameMode interface.
 */
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

    /**
     * Constructs a MultiPlayerMode object with the specified parameters.
     *
     * @param questionDB The QuestionDB object containing the questions for the game.
     * @param player1    The first player.
     * @param player2    The second player.
     * @param ui         The graphical user interface for displaying the game.
     */
    public MultiPlayerMode(QuestionDB questionDB, Player player1, Player player2, IGraphicalUI ui) {
        this.questionDB = questionDB;
        this.player1 = player1;
        this.player2 = player2;
        this.ui = ui;
        this.random = new Random();
        this.questions = questionDB.getAllQuestions();
        Collections.shuffle(this.questions); // Shuffle the questions list
    }

    /**
     * Starts the multiplayer game by presenting the first question.
     */
    @Override
    public void startGame() {
        presentNextQuestion();
    }

    /**
     * Presents the next question in the game.
     * If there are no more questions, ends the game.
     */
    private void presentNextQuestion() {
        if (currentQuestionIndex < totalQuestions && currentQuestionIndex < questions.size()) {
            Player currentPlayer = (currentQuestionIndex % 2 == 0) ? player1 : player2;
            Question question = questions.get(currentQuestionIndex);
            ui.showQuestion(question, currentQuestionIndex, currentPlayer); // Pass current player to UI
            currentQuestionIndex++;
            questionSelected = true;
        } else {
            endGame();
        }
    }

    /**
     * Checks the answer selected by the current player.
     *
     * @param answerIndex The index of the selected answer.
     */
    public void checkAnswer(int answerIndex) {
        if (!questionSelected) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie zuerst eine Frage aus.");
            return;
        }

        Question currentQuestion = questions.get(currentQuestionIndex - 1); // Since currentQuestionIndex has already been incremented
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
            presentNextQuestion(); // Present next question or end the game
        } else {
            endGame(); // If all questions have been answered, end the game
        }
    }

    /**
     * Ends the multiplayer game.
     * Adds the players to the leaderboard and shows the menu.
     */
    @Override
    public void endGame() {
        Leaderboard.addPlayer(player1);
        Leaderboard.addPlayer(player2);
        ui.showMenu();
    }

    /**
     * This method is no longer needed and can be removed or marked as deprecated.
     *
     * @param questionIndex The index of the question.
     */
    @Override
    public void presentQuestion(int questionIndex) {
        // Not used.
    }
}
