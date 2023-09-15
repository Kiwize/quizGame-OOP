package com._Nology_test;

import com._Nology.Answer;
import com._Nology.Question;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class Question_test {
    @Test
    public void creatingQuestionSetsProperlyId() {
        // Arrange
        int id = 0;
        Question myTestQuestion = new Question(id, new String("hello?"),new ArrayList<Answer>());
        // Act
        int result = myTestQuestion.getId();
        // Assert
        assertEquals(id, result);
    }

    @Test
    public void creatingQuestionSetsProperlyDescriptionQuestion() {
        // Arrange
        String descriptionQuestion = "Capital city of Spain";
        Question myTestQuestion = new Question(0, descriptionQuestion,new ArrayList<Answer>());
        // Act
        String result = myTestQuestion.getDescriptionQuestion();
        // Assert
        assertEquals(descriptionQuestion, result);
    }
}
