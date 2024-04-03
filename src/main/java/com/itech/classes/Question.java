package com.itech.classes;

public class Question {
    private Integer id;; // Feld für die Datenbank-ID
    private String questionText;
    private String[] options;
    private int correctAnswerIndex;

    public Question(Integer id, String questionText, String[] options, int correctAnswerIndex) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public boolean isCorrectAnswer(int answerIndex) {
        return answerIndex == correctAnswerIndex;
    }
}
