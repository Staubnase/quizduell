package com.itech.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The QuestionDB class represents a database for storing and retrieving quiz questions.
 * It provides methods for adding, removing, and retrieving questions from the database.
 */
public class QuestionDB {
    // Database connection details
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/quizduelldb";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "root";

    static {
        try {
            // Ensure that the JDBC driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a question to the database.
     * 
     * @param question The question to be added.
     */
    public void addQuestion(Question question) {
        // SQL statement for inserting a question into the database
        String sql = "INSERT INTO questions (question_text, answer1, answer2, answer3, answer4, correct_answer) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, question.getQuestionText());
            for (int i = 0; i < question.getOptions().length; i++) {
                pstmt.setString(i + 2, question.getOptions()[i]);
            }
            pstmt.setInt(6, question.getCorrectAnswerIndex() + 1);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a question from the database.
     * 
     * @param questionId The ID of the question to be removed.
     */
    public void removeQuestion(int questionId) {
        // SQL statement for deleting a question from the database
        String sql = "DELETE FROM questions WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, questionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all questions from the database.
     * 
     * @return A list of all questions in the database.
     */
    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        // SQL statement for selecting all questions from the database
        String sql = "SELECT * FROM questions";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int questionID = rs.getInt("id");
                String questionText = rs.getString("question_text");
                String[] options = {rs.getString("answer1"), rs.getString("answer2"), rs.getString("answer3"), rs.getString("answer4")};
                int correctAnswer = rs.getInt("correct_answer") - 1;
                questions.add(new Question(questionID, questionText, options, correctAnswer));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    /**
     * Retrieves a question from the database based on its ID.
     * 
     * @param questionId The ID of the question to retrieve.
     * @return The question with the specified ID, or null if no question is found or an error occurs.
     */
    public Question getQuestionById(int questionId) {
        // SQL statement for selecting a question from the database based on its ID
        String sql = "SELECT * FROM questions WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, questionId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String questionText = rs.getString("question_text");
                    String[] options = {
                        rs.getString("answer1"), rs.getString("answer2"),
                        rs.getString("answer3"), rs.getString("answer4")
                    };
                    int correctAnswer = rs.getInt("correct_answer") - 1;
                    return new Question(questionId, questionText, options, correctAnswer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
