package com.itech.classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class QuestionDBTest {

    private QuestionDB questionDB;

    @BeforeEach
    void setUp() {
        // Konfigurieren der DataSource für H2 In-Memory Datenbank
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        // Initialisieren der QuestionDB mit der H2 DataSource
        questionDB = new QuestionDB(dataSource);
        // Initialisieren der Datenbank mit erforderlichen Tabellen/Startdaten
    }

    @AfterEach
    void tearDown() {
        // Optional: Aufräumen der Datenbank, falls erforderlich
    }

    @Test
    void testAddQuestion() {
        Question newQuestion = new Question(null, "Was ist Java?", new String[]{"Programmiersprache", "Kaffeesorte", "Betriebssystem", "Planet"}, 0);
        questionDB.addQuestion(newQuestion);
        List<Question> questions = questionDB.getAllQuestions();
        assertFalse(questions.isEmpty());
        assertEquals("Was ist Java?", questions.get(0).getQuestionText());
    }

    @Test
    void testGetAllQuestions() {
        List<Question> questions = questionDB.getAllQuestions();
        assertFalse(questions.isEmpty()); // Annahme: Initialdaten sind vorhanden
    }

    @Test
    void testRemoveQuestion() {
        // Voraussetzung: Eine Frage existiert mit ID 1
        questionDB.removeQuestion(1);
        assertTrue(questionDB.getAllQuestions().isEmpty());
    }

    @Test
    void testQuestionExistsAfterAdd() {
        Question newQuestion = new Question(null, "Was ist ein Compiler?", new String[]{"Programm", "Gerät", "Prozess", "Fehler"}, 0);
        questionDB.addQuestion(newQuestion);
        List<Question> questions = questionDB.getAllQuestions();
        assertTrue(questions.stream().anyMatch(question -> question.getQuestionText().equals("Was ist ein Compiler?")));
    }

    @Test
    void testCorrectAnswer() {
        // Annahme: Es gibt eine Frage mit korrekter Antwort an Index 2
        Question question = questionDB.getAllQuestions().get(0); // Beispielhaft die erste Frage nehmen
        assertEquals(2, question.getCorrectAnswerIndex());
    }
}
