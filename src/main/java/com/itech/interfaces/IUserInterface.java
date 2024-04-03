package com.itech.interfaces;

public interface IUserInterface extends IGraphicalUI, IGameMode{
    void login(String username);
    void createAccount(String username);
    void endGame();
}

