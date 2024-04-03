package com.itech.interfaces;

import java.util.Scanner;

public class KonsolenUI implements Benutzerinterface {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void zeigeNachricht(String nachricht) {
        System.out.println(nachricht);
    }

    @Override
    public String leseEingabe() {
        return scanner.nextLine();
    }
}