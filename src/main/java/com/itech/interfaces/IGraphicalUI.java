package com.itech.interfaces;

import com.itech.classes.Question;
import com.itech.classes.Leaderboard;
import com.itech.classes.Player;

public interface IGraphicalUI {

    void showMenu();
    void showQuestion(Question question, int questionNumber, Player currentPlayer);
    void showLeaderboard(Leaderboard leaderboard);
    void managePlayers();
    void showResults(int score);

}
