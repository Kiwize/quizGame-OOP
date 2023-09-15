package com._Nology_test;

import com._Nology.Answer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Answer_test {
    @Test
    public void creatingAnswerSetsProperlyCodeAnswer() {
        // Arrange
        String codeAnswer = "a";
        Answer myTestAnswer = new Answer(codeAnswer, "Brussels", false);
        // Act
        String result = myTestAnswer.getCodeAnswer();
        // Assert
        assertEquals("a", result);
    }

    @Test
    public void creatingAnswerSetsProperlyDescriptionAnswer() {
        // Arrange
        String descriptionAnswer = "Brussels";
        Answer myTestAnswer = new Answer("a", descriptionAnswer, false);
        // Act
        String result = myTestAnswer.getDescriptionAnswer();
        // Assert
        assertEquals("Brussels", result);
    }

    @Test
    public void creatingAnswerSetsProperlyIsCorrect() {
        // Arrange
        Boolean isCorrect = false;
        Answer myTestAnswer = new Answer("a", "Brussels", isCorrect);
        // Act
        Boolean result = myTestAnswer.getIsCorrect();
        // Assert
        assertEquals(false, result);
    }
}
