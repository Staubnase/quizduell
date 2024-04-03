package com.itech.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class QuestionDB {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/quizduelldb";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "root";

    static {
        try {
            // Stellen Sie sicher, dass der JDBC-Treiber geladen ist
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public QuestionDB() {
        //TODO Auto-generated constructor stub
    }

    public void addQuestion(Question question) {
        String sql = "INSERT INTO questions (question_text, answer1, answer2, answer3, answer4, correct_answer) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, question.getQuestionText());
            for (int i = 0; i < question.getOptions().length; i++) {
                pstmt.setString(i + 2, question.getOptions()[i]);
            }
            pstmt.setInt(6, question.getCorrectAnswerIndex() + 1); // Korrektur um DB Indexierung anzupassen
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeQuestion(int questionId) {
        String sql = "DELETE FROM questions WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, questionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int questionID = rs.getInt("id");
                String questionText = rs.getString("question_text");
                String[] options = {rs.getString("answer1"), rs.getString("answer2"), rs.getString("answer3"), rs.getString("answer4")};
                int correctAnswer = rs.getInt("correct_answer") - 1; // Korrektur um Array Indexierung anzupassen
                questions.add(new Question(questionID, questionText, options, correctAnswer));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }
}
