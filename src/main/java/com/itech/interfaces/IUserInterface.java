package com.itech.interfaces;

/**
 * The IUserInterface interface represents the user interface for the Quizduell game.
 * It extends the IGraphicalUI and IGameMode interfaces.
 */
public interface IUserInterface extends IGraphicalUI, IGameMode {

    /**
     * Logs in the user with the specified username.
     *
     * @param username The username of the user.
     */
    void login(String username);

    /**
     * Creates a new account with the specified username.
     *
     * @param username The username for the new account.
     */
    void createAccount(String username);

    /**
     * Ends the current game.
     */
    void endGame();
}

