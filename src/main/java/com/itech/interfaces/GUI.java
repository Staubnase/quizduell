package com.itech.interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements Benutzerinterface {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private JButton button;
    private String eingabe;
    private final Object lock = new Object();

    public GUI() {
        erstelleGUI();
    }

    private void erstelleGUI() {
        frame = new JFrame("Quizduell");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        textArea = new JTextArea();
        frame.add(textArea, BorderLayout.CENTER);

        textField = new JTextField();
        button = new JButton("Eingabe");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock) {
                    eingabe = textField.getText();
                    textField.setText("");
                    lock.notify();
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textField, BorderLayout.CENTER);
        panel.add(button, BorderLayout.EAST);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    @Override
    public void zeigeNachricht(String nachricht) {
        final String finalNachricht = nachricht;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textArea.append(finalNachricht + "\n");
            }
        });
    }

    @Override
    public String leseEingabe() {
        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return eingabe;
    }
}