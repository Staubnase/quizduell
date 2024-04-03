package com.itech.interfaces;

import com.itech.classes.Question;
import com.itech.classes.Leaderboard;
import com.itech.classes.Player;

/**
 * The IGraphicalUI interface represents the graphical user interface for a quiz game.
 * It provides methods to display the menu, questions, leaderboard, manage players, and show results.
 */
public interface IGraphicalUI {

    /**
     * Displays the menu.
     */
    void showMenu();

    /**
     * Displays a question with the given question object, question number, and current player.
     *
     * @param question       the question to be displayed
     * @param questionNumber the number of the question
     * @param currentPlayer the current player
     */
    void showQuestion(Question question, int questionNumber, Player currentPlayer);

    /**
     * Displays the leaderboard with the given leaderboard object.
     *
     * @param leaderboard the leaderboard to be displayed
     */
    void showLeaderboard(Leaderboard leaderboard);

    /**
     * Manages the players.
     */
    void managePlayers();

    /**
     * Displays the results with the given score.
     *
     * @param score the score to be displayed
     */
    void showResults(int score);

}
