package com.itech.interfaces;

/**
 * The IGameMode interface represents a game mode in a quiz game.
 * It defines the methods that need to be implemented by any game mode.
 */
public interface IGameMode {
    
    /**
     * Starts the game.
     */
    void startGame();
    
    /**
     * Presents a question to the player.
     * 
     * @param questionIndex the index of the question to be presented
     */
    void presentQuestion(int questionIndex);
    
    /**
     * Ends the game.
     */
    void endGame();
}

