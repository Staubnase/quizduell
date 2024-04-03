package com.itech.classes;

/**
 * Represents a question in a quiz game.
 */
public class Question {
    private Integer id; // Feld für die Datenbank-ID
    private String questionText;
    private String[] options;
    private int correctAnswerIndex;

    /**
     * Constructs a new Question object with the specified parameters.
     *
     * @param id                 the ID of the question in the database
     * @param questionText       the text of the question
     * @param options            the array of answer options for the question
     * @param correctAnswerIndex the index of the correct answer in the options array
     */
    public Question(Integer id, String questionText, String[] options, int correctAnswerIndex) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    /**
     * Returns the ID of the question.
     *
     * @return the ID of the question
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the question.
     *
     * @param id the ID of the question
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the text of the question.
     *
     * @return the text of the question
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Returns the array of answer options for the question.
     *
     * @return the array of answer options
     */
    public String[] getOptions() {
        return options;
    }

    /**
     * Returns the index of the correct answer in the options array.
     *
     * @return the index of the correct answer
     */
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    /**
     * Checks if the given answer index is the correct answer.
     *
     * @param answerIndex the index of the answer to check
     * @return true if the answer is correct, false otherwise
     */
    public boolean isCorrectAnswer(int answerIndex) {
        return answerIndex == correctAnswerIndex;
    }
}
