package com.itech.classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionDBTest {

    private QuestionDB questionDB;

    @BeforeEach
    void setUp() {
        // Initialisieren der Datenbank mit erforderlichen Tabellen/Startdaten
        questionDB = new QuestionDB();
        questionDB.addQuestion(new Question(null, "Was ist die Hauptstadt von Frankreich?", new String[]{"Berlin", "Madrid", "Paris", "Rom"}, 2));
        questionDB.addQuestion(new Question(null, "Wie viele Kontinente gibt es?", new String[]{"5", "6", "7", "8"}, 2));
    }

    @AfterEach
    void tearDown() {
        // Optional: Aufräumen der Datenbank, falls erforderlich
    }

    @Test
    void testAddQuestion() {
        Question newQuestion = new Question(120, "Was ist Java?", new String[]{"Programmiersprache", "Kaffeesorte", "Betriebssystem", "Planet"}, 0);
        questionDB.addQuestion(newQuestion);
        List<Question> questions = questionDB.getAllQuestions();
        assertFalse(questions.isEmpty());
        assertEquals("Was ist Java?", questions.get(120).getQuestionText());
    }

    @Test
    void testGetAllQuestions() {
        List<Question> questions = questionDB.getAllQuestions();
        assertFalse(questions.isEmpty()); // Annahme: Initialdaten sind vorhanden
    }

    @Test
    void testQuestionExistsAfterAdd() {
        Question newQuestion = new Question(null, "Was ist ein Compiler?", new String[]{"Programm", "Gerät", "Prozess", "Fehler"}, 0);
        questionDB.addQuestion(newQuestion);
        List<Question> questions = questionDB.getAllQuestions();
        assertTrue(questions.stream().anyMatch(question -> question.getQuestionText().equals("Was ist ein Compiler?")));
    }

    @Test
    void testQuestionExistsAfterAdd2() {
        Question newQuestion = new Question(null, "Was ist eine Fliege?", new String[]{"Programm", "Gerät", "Tier", "Fehler"}, 3);
        questionDB.addQuestion(newQuestion);
        List<Question> questions = questionDB.getAllQuestions();
        assertTrue(questions.stream().anyMatch(question -> question.getQuestionText().equals("Was ist eine Fliege?")));
    }

    @Test
    void testCorrectAnswer() {
        // Annahme: Es gibt eine Frage mit korrekter Antwort an Index 2
        Question question = questionDB.getAllQuestions().get(0); // Beispielhaft die erste Frage nehmen
        assertEquals(2, question.getCorrectAnswerIndex());
    }
}
