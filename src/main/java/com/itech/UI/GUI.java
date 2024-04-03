package com.itech.UI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import com.itech.GameModes.MultiPlayerMode;
import com.itech.GameModes.SinglePlayerMode;
import com.itech.classes.Leaderboard;
import com.itech.classes.Player;
import com.itech.classes.Question;
import com.itech.classes.QuestionDB;
import com.itech.interfaces.IGameMode;
import com.itech.interfaces.IGraphicalUI;
import java.util.List;

/**
 * The GUI class represents the graphical user interface for the Quizduell game.
 * It extends the JFrame class and implements the IGraphicalUI interface.
 * The GUI class is responsible for displaying the main menu, questions, and managing player interactions.
 */
public class GUI extends JFrame implements IGraphicalUI {
    private JPanel mainPanel;
    private JPanel questionPanel; // Panel für Fragen
    private JPanel menuPanel; // Panel für das Menü
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private JLabel currentPlayerLabel;
    private JButton questionsManagementButton;
    private JButton submitButton, singlePlayerButton, multiPlayerButton, leaderboardButton, managePlayersButton, exitButton;
    private Leaderboard leaderboard;
    private QuestionDB questionDB;
    private IGameMode gameMode;
    private Map<String, Player> registeredPlayers = new HashMap<>();

    public GUI() {
        this.leaderboard = new Leaderboard();
        this.questionDB = new QuestionDB();
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Quizduell");
        setSize(1500, 1300);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        setupMenuButtons();
        setupMenuPanel();
        setupQuestionPanel();

        mainPanel.add(menuPanel, BorderLayout.CENTER);
        questionPanel.setVisible(false);

        pack();
        setVisible(true);
    }

    private void setupMenuButtons() {
        singlePlayerButton = new JButton("Einzelspieler");
        singlePlayerButton.addActionListener(e -> startSinglePlayerGame());

        multiPlayerButton = new JButton("Mehrspieler");
        multiPlayerButton.addActionListener(e -> startMultiPlayerGame());

        leaderboardButton = new JButton("Rangliste");
        leaderboardButton.addActionListener(e -> showLeaderboard(this.leaderboard));

        managePlayersButton = new JButton("Spieler verwalten");
        managePlayersButton.addActionListener(e -> managePlayers());

        questionsManagementButton = new JButton("Fragen verwalten");
        questionsManagementButton.addActionListener(e -> manageQuestions());

        exitButton = new JButton("Spiel beenden");
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void setupMenuPanel() {
        menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        menuPanel.add(singlePlayerButton);
        menuPanel.add(multiPlayerButton);
        menuPanel.add(leaderboardButton);
        menuPanel.add(managePlayersButton);
        menuPanel.add(questionsManagementButton);
        menuPanel.add(exitButton);
        menuPanel.setSize(1000, 1300);

        JButton helpButton = new JButton("?");
        helpButton.addActionListener(e -> showHelp());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(helpButton);
        menuPanel.add(buttonPanel);
    }

    private void setupQuestionPanel() {
        questionPanel = new JPanel();
        questionPanel.setSize(1000, 1300);
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionLabel = new JLabel("Bitte wählen Sie eine Option aus dem Menü.");
        questionPanel.add(questionLabel);
    
        currentPlayerLabel = new JLabel(""); // Label für den aktuellen Spieler hinzugefügt
        questionPanel.add(currentPlayerLabel); // Aktuellen Spieler zum Panel hinzufügen
    
    
        optionButtons = new JRadioButton[4];
        ButtonGroup optionGroup = new ButtonGroup();
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JRadioButton();
            optionGroup.add(optionButtons[i]);
            questionPanel.add(optionButtons[i]);
        }
    
        submitButton = new JButton("Antwort einreichen");
        submitButton.addActionListener(e -> processSelectedAnswer());
        questionPanel.add(submitButton);
        
        // "?" Button hinzufügen
        JButton helpButton = new JButton("?");
        helpButton.addActionListener(e -> showHelp());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(helpButton);
        questionPanel.add(buttonPanel);
    
        mainPanel.add(questionPanel, BorderLayout.CENTER);
    }
    
    private void showHelp() {
        // Hier können Sie den Hilfetext oder die Hilfestellungen anzeigen
        if (gameMode != null) {
            JOptionPane.showMessageDialog(this, "Wähle eine Antwort aus und klicke auf 'Antwort einreichen'.", "Hilfe", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Wählen Sie eine Option aus dem Menü, um das Spiel zu starten.", "Hilfe", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    

    private void processSelectedAnswer() {
        int selectedOptionIndex = getSelectedOptionIndex();
        if (selectedOptionIndex != -1 && gameMode instanceof SinglePlayerMode) {
            ((SinglePlayerMode) gameMode).checkAnswer(selectedOptionIndex);
        } else {
            if (selectedOptionIndex != -1 && gameMode instanceof MultiPlayerMode) {
                ((MultiPlayerMode) gameMode).checkAnswer(selectedOptionIndex);               
            } else {
                
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Antwort aus.");
            }
        }
    }

    private int getSelectedOptionIndex() {
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) {
                return i;
            }
        }
        return -1; // Keine Option ausgewählt
    }

    private void startSinglePlayerGame() {
        Player singlePlayer = getOrCreatePlayer("Spieler1"); 
        if (singlePlayer != null) {
            gameMode = new SinglePlayerMode(questionDB, singlePlayer, this);
            currentPlayerLabel.setText("Aktueller Spieler: " + singlePlayer.getUsername()); // Label aktualisieren
            gameMode.startGame();

        } else {
            System.out.println("Der Spieler konnte nicht initialisiert werden.");
        }
    }

    
    

    private void startMultiPlayerGame() {
        Player player1 = getOrCreatePlayer("Spieler1");
        Player player2 = getOrCreatePlayer("Spieler2");
        gameMode = new MultiPlayerMode(questionDB, player1, player2, this);
        gameMode.startGame();
        currentPlayerLabel.setText("Aktueller Spieler: " + player1.getUsername()); // Label aktualisieren
    }

    private Player getOrCreatePlayer(String defaultUsername) {
        String username = JOptionPane.showInputDialog(this, "Bitte geben Sie Ihren Benutzernamen ein:", defaultUsername);
        if (username != null && !username.trim().isEmpty()) {
            return registeredPlayers.computeIfAbsent(username.trim(), uname -> new Player(uname));
        } else {
            return null;
        }
    }

    @Override
    public void showQuestion(Question question, int questionNumber, Player currentPlayer) {
        questionLabel.setText("Frage " + (questionNumber + 1) + ": " + question.getQuestionText());
        currentPlayerLabel.setText("Aktueller Spieler: " + currentPlayer.getUsername());
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(question.getOptions()[i]);
            optionButtons[i].setVisible(true);
            optionButtons[i].setSelected(false);
        }
        submitButton.setVisible(true);

        menuPanel.setVisible(false);
        questionPanel.setVisible(true);
        mainPanel.add(questionPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
        }


        @Override
        public void showMenu() {
            questionPanel.setVisible(false);
            menuPanel.setVisible(true);
            mainPanel.revalidate();
            mainPanel.repaint();
        }

        @Override
        public void showLeaderboard(Leaderboard leaderboard) {
            if (leaderboard == null) {
                JOptionPane.showMessageDialog(this, "Die Rangliste ist derzeit nicht verfügbar.");
                return;
            }

            String leaderboardText = leaderboard.getFormattedLeaderboard();

            if (leaderboardText == null || leaderboardText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Die Rangliste ist leer.");
            } else {
                JFrame leaderboardFrame = new JFrame("Rangliste");
                JTextArea textArea = new JTextArea(leaderboardText);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                leaderboardFrame.add(scrollPane);
                leaderboardFrame.setSize(400, 600);
                leaderboardFrame.setLocationRelativeTo(null);
                leaderboardFrame.setVisible(true);
            }
        }

        @Override
        public void managePlayers() {
            JDialog managePlayersDialog = new JDialog(this, "Spieler verwalten", true);
            managePlayersDialog.setLayout(new BorderLayout());

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());

            JButton addButton = new JButton("Spieler hinzufügen");
            JButton deleteButton = new JButton("Spieler löschen");
            JButton listButton = new JButton("Alle Spieler anzeigen");
            JButton closeButton = new JButton("Schließen");

            addButton.addActionListener(e -> addPlayer());
            deleteButton.addActionListener(e -> deletePlayer());
            listButton.addActionListener(e -> listPlayers());
            closeButton.addActionListener(e -> managePlayersDialog.dispose());

            buttonPanel.add(addButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(listButton);
            buttonPanel.add(closeButton);

            managePlayersDialog.add(buttonPanel, BorderLayout.CENTER);
            managePlayersDialog.pack();
            managePlayersDialog.setLocationRelativeTo(this);
            managePlayersDialog.setVisible(true);
        }

        private void addPlayer() {
            String playerName = JOptionPane.showInputDialog(this, "Spielername:");
            if (playerName != null && !playerName.trim().isEmpty()) {
                Player newPlayer = new Player(playerName.trim());
                registeredPlayers.put(playerName.trim(), newPlayer);
                JOptionPane.showMessageDialog(this, playerName + " hinzugefügt.");
            }
        }

        private void deletePlayer() {
            String playerName = JOptionPane.showInputDialog(this, "Zu löschender Spielername:");
            if (playerName == null || playerName.trim().isEmpty()) {
                return;
            } else if (registeredPlayers.remove(playerName.trim()) != null) {
                JOptionPane.showMessageDialog(this, playerName + " gelöscht.");
            } else {
                JOptionPane.showMessageDialog(this, "Spieler nicht gefunden.");
            }
        }


        private void listPlayers() {
            StringBuilder playersList = new StringBuilder("Registrierte Spieler:\n");
            registeredPlayers.keySet().forEach(name -> playersList.append(name).append("\n"));
            JOptionPane.showMessageDialog(this, playersList.toString());
        }

        @Override
        public void showResults(int score) {
            JOptionPane.showMessageDialog(this, "Das Spiel ist vorbei. Ihre Punktzahl: " + score);
        }

        private void manageQuestions() {
            JDialog questionsManagementDialog = new JDialog(this, "Fragen verwalten", true);
            questionsManagementDialog.setLayout(new BorderLayout());

            JButton showQuestionsButton = new JButton("Fragen anzeigen");
            JButton addQuestionButton = new JButton("Frage hinzufügen");
            JButton deleteQuestionButton = new JButton("Frage löschen");
            JButton closeButton = new JButton("Schließen");

            showQuestionsButton.addActionListener(e -> showQuestions());
            addQuestionButton.addActionListener(e -> addQuestion());
            deleteQuestionButton.addActionListener(e -> deleteQuestion());
            closeButton.addActionListener(e -> questionsManagementDialog.dispose());

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(showQuestionsButton);
            buttonPanel.add(addQuestionButton);
            buttonPanel.add(deleteQuestionButton);
            buttonPanel.add(closeButton);

            questionsManagementDialog.add(buttonPanel, BorderLayout.CENTER);
            questionsManagementDialog.pack();
            questionsManagementDialog.setLocationRelativeTo(this);
            questionsManagementDialog.setVisible(true);
        }

        private void showQuestions() {
            List<Question> questions = questionDB.getAllQuestions();
            StringBuilder questionsList = new StringBuilder("Verfügbare Fragen:\n");
            for (Question question : questions) {
                questionsList.append("ID: ").append(question.getId()).append(", ");
                questionsList.append("Frage: ").append(question.getQuestionText()).append("\n");
            }
            JOptionPane.showMessageDialog(this, questionsList.toString());
        }

        private void addQuestion() {
            JTextField questionText = new JTextField();
            JTextField answer1 = new JTextField();
            JTextField answer2 = new JTextField();
            JTextField answer3 = new JTextField();
            JTextField answer4 = new JTextField();
            JTextField correctAnswer = new JTextField();
            Object[] message = {
                "Frage:", questionText,
                "Antwort 1:", answer1,
                "Antwort 2:", answer2,
                "Antwort 3:", answer3,
                "Antwort 4:", answer4,
                "Richtige Antwort (1-4):", correctAnswer
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Neue Frage hinzufügen", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String[] options = {answer1.getText(), answer2.getText(), answer3.getText(), answer4.getText()};
                    int correctAnswerIndex = Integer.parseInt(correctAnswer.getText()) - 1;
                    Question question = new Question(null, questionText.getText(), options, correctAnswerIndex);
                    questionDB.addQuestion(question);
                    JOptionPane.showMessageDialog(this, "Frage hinzugefügt");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige Nummer für die richtige Antwort ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        }



        private void deleteQuestion() {
            String questionIdStr = JOptionPane.showInputDialog("Frage ID zum Löschen:");
            if (questionIdStr != null && !questionIdStr.trim().isEmpty()) {
                int questionId = Integer.parseInt(questionIdStr);
                questionDB.removeQuestion(questionId);
                JOptionPane.showMessageDialog(this, "Frage gelöscht");
            }
        }

        }
