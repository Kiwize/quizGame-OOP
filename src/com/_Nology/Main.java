package com._Nology;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    // TODO: Add another player - Add timing (https://stackabuse.com/how-to-get-current-date-and-time-in-java/)
    public static void main(String[] args) {
        // write your code here
        Scanner myScanner = new Scanner(System.in);
        System.out.println("What's you're name?");
        String playerName = myScanner.nextLine();
        System.out.println("Welcome to QuizzyQuiz, " + playerName + " !");
        System.out.println("Every right answer will give you 10 points, and you will have 5 questions..");
        System.out.println("In order to win you'll need 30 points. Good Luck ! ^^");

        QuizGame player1game = getRandomQuizSetForQuizGame(playerName);

        for (Question question : player1game.questions) // foreach
        {
            System.out.println(question.getDescriptionQuestion());
            for (Answer answer : question.answers) // foreach
            {
                System.out.println(answer.getCodeAnswer() + ") " + answer.getDescriptionAnswer());
            }
            System.out.println("Choose your answer!");
            String playerAnswer = myScanner.nextLine();

            while (!player1game.isValidAnswer(playerAnswer)) {
                System.out.println("Not a valid answer, select a correct option please!");
                playerAnswer = myScanner.nextLine();
            }

            if (player1game.isCorrectThisAnswer(playerAnswer, question.answers)) {
                player1game.addPlayerScore(10);
                System.out.println("Bravoooo, You got it right ! you accumulate 10 points!");
            } else {
                System.out.println("Oops.. wrong answer! Better luck next time ! :)");
            }
        }
        String feedbackMessage = "";
        if (player1game.getPlayerScore() >= 30) {
            feedbackMessage = "Congratulations !! Your score is " + player1game.getPlayerScore() + " points ! You are a champ! :D";
        } else {
            feedbackMessage = "I'm afraid your score is " + player1game.getPlayerScore() + "points.. You lost the game! Better luck next time ;)";
        }
        System.out.println(feedbackMessage);
    }

    public static int generateRandomNumber() {
        int randomNumber = new Random().nextInt(26 - 0 + 1) + 1;
        return randomNumber;
    }

    public static QuizGame getRandomQuizSetForQuizGame(String playerNameParam) {
        int myRandomNumber = generateRandomNumber();
        QuizGame myRandomQuizGame = null;
        if (myRandomNumber > 0 && myRandomNumber <= 5) {
            myRandomQuizGame = getFirstQuizGame(playerNameParam);
        } else if (myRandomNumber > 5 && myRandomNumber <= 10) {
            myRandomQuizGame = getSecondQuizGame(playerNameParam);
        } else if (myRandomNumber > 10 && myRandomNumber <= 15) {
            myRandomQuizGame = getThirdQuizGame(playerNameParam);
        } else if (myRandomNumber > 15 && myRandomNumber <= 20) {
            myRandomQuizGame = getFourthQuizGame(playerNameParam);
        } else if (myRandomNumber > 20 && myRandomNumber <= 25) {
            myRandomQuizGame = getFifthQuizGame(playerNameParam);
        }

        return myRandomQuizGame;
    }

    public static QuizGame getFirstQuizGame(String playerNameParam) {
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(getFirstQuestionWithAnswers());
        questions.add(getSecondQuestionWithAnswers());
        questions.add(getThirdQuestionWithAnswers());
        questions.add(getFourthQuestionWithAnswers());
        questions.add(getFifthQuestionWithAnswers());

        return new QuizGame(playerNameParam, 0, questions);
    }

    public static QuizGame getSecondQuizGame(String playerNameParam) {
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(getSixthQuestionWithAnswers());
        questions.add(getSeventhQuestionWithAnswers());
        questions.add(getEighthQuestionWithAnswers());
        questions.add(getNinethQuestionWithAnswers());
        questions.add(getTenthQuestionWithAnswers());

        return new QuizGame(playerNameParam, 0, questions);
    }

    public static QuizGame getThirdQuizGame(String playerNameParam) {
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(getEleventhQuestionWithAnswers());
        questions.add(getTwelfthQuestionWithAnswers());
        questions.add(getThirteenthQuestionWithAnswers());
        questions.add(getFourteenthQuestionWithAnswers());
        questions.add(getFifteenthQuestionWithAnswers());

        return new QuizGame(playerNameParam, 0, questions);
    }

    public static QuizGame getFourthQuizGame(String playerNameParam) {
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(getSixteenthQuestionWithAnswers());
        questions.add(getSeventeenthQuestionWithAnswers());
        questions.add(getEighteenthQuestionWithAnswers());
        questions.add(getNineteenthQuestionWithAnswers());
        questions.add(getTwentiethQuestionWithAnswers());

        return new QuizGame(playerNameParam, 0, questions);
    }

    public static QuizGame getFifthQuizGame(String playerNameParam) {
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(getTwentyFirstQuestionWithAnswers());
        questions.add(getTwentySecondQuestionWithAnswers());
        questions.add(getTwentyThirdQuestionWithAnswers());
        questions.add(getTwentyFourthQuestionWithAnswers());
        questions.add(getTwentyFifthQuestionWithAnswers());

        return new QuizGame(playerNameParam, 0, questions);
    }

    public static Question getFirstQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "San Juan", false));
        answers.add(createAnswer("b", "Asuncion", false));
        answers.add(createAnswer("c", "San Jose", true));
        answers.add(createAnswer("d", "Santiago", false));

        return createQuestion(0, "Capital city of Costa Rica ?", answers);
    }

    public static Question getSecondQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Seoul", false));
        answers.add(createAnswer("b", "Manila", true));
        answers.add(createAnswer("c", "Hanoi", false));
        answers.add(createAnswer("d", "Taipei", false));

        return createQuestion(1, "Capital city of Philippines ?", answers);
    }

    public static Question getThirdQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Male", true));
        answers.add(createAnswer("b", "Maseru", false));
        answers.add(createAnswer("c", "Port Louis", false));
        answers.add(createAnswer("d", "chisinau", false));

        return createQuestion(2, "Capital city of Maldives ?", answers);
    }

    public static Question getFourthQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Muscat", false));
        answers.add(createAnswer("b", "Munich", false));
        answers.add(createAnswer("c", "Bucharest", true));
        answers.add(createAnswer("d", "Belgrade", false));

        return createQuestion(3, "Capital city of Romania ?", answers);
    }

    public static Question getFifthQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Brussels", false));
        answers.add(createAnswer("b", "Bern", true));
        answers.add(createAnswer("c", "San Marino", false));
        answers.add(createAnswer("d", "Tallin", true));

        return createQuestion(4, "Capital city of Switzerland ?", answers);
    }

    private static Question getSixthQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "A sponge", false));
        answers.add(createAnswer("b", "A stone", false));
        answers.add(createAnswer("c", "A candle", false));
        answers.add(createAnswer("d", "An egg", true));

        return createQuestion(4, "What has to be broken before you can use it ?", answers);
    }

    private static Question getSeventhQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Germany", false));
        answers.add(createAnswer("b", "Uk", false));
        answers.add(createAnswer("c", "New Zealand", true));
        answers.add(createAnswer("d", "Italy", false));

        return createQuestion(4, "What was the first country to approve women's suffrage ?", answers);
    }

    private static Question getEighthQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "1959", false));
        answers.add(createAnswer("b", "1969", true));
        answers.add(createAnswer("c", "1967", false));
        answers.add(createAnswer("d", "1981", false));

        return createQuestion(4, "In what year did man reach the moon ?", answers);
    }

    private static Question getNinethQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Venice", true));
        answers.add(createAnswer("b", "Milan", false));
        answers.add(createAnswer("c", "Marseille", false));
        answers.add(createAnswer("d", "Rome", false));

        return createQuestion(4, "What was Marco Polo's home city ?", answers);
    }

    private static Question getTenthQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "2000", false));
        answers.add(createAnswer("b", "1985", false));
        answers.add(createAnswer("c", "1994", true));
        answers.add(createAnswer("d", "1952", false));

        return createQuestion(4, "In what year did the Rwandan genocide take place ?", answers);
    }

    private static Question getEleventhQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "1991", true));
        answers.add(createAnswer("b", "2015", false));
        answers.add(createAnswer("c", "1960", false));
        answers.add(createAnswer("d", "1500 BC ;)", false));

        return createQuestion(4, "In what year was the Soviet Union dissolved ?", answers);
    }

    private static Question getTwelfthQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Canary Islands", false));
        answers.add(createAnswer("b", "Malta", false));
        answers.add(createAnswer("c", "Greece", true));
        answers.add(createAnswer("d", "Cyprus", false));

        return createQuestion(4, "To which country does the island of Crete belong ?", answers);
    }

    private static Question getThirteenthQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Poland", false));
        answers.add(createAnswer("b", "Hungary", true));
        answers.add(createAnswer("c", "Peru", false));
        answers.add(createAnswer("d", "Luxembourg", false));

        return createQuestion(4, "In which country in Europe is Magyar spoken ?", answers);
    }

    private static Question getFourteenthQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Brazil", false));
        answers.add(createAnswer("b", "Japan", false));
        answers.add(createAnswer("c", "China", false));
        answers.add(createAnswer("d", "France", true));

        return createQuestion(4, "What is the most visited country in the world ?", answers);
    }

    private static Question getFifteenthQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "USA and Russia", true));
        answers.add(createAnswer("b", "Morocco and Spain", false));
        answers.add(createAnswer("c", "Japan and China", false));
        answers.add(createAnswer("d", "UK and France", false));

        return createQuestion(4, "Between which countries is the Bering Strait ?", answers);
    }

    private static Question getSixteenthQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Wolfgang Amadeus Mozart", false));
        answers.add(createAnswer("b", "Van Gogh", false));
        answers.add(createAnswer("c", "Pablo Picasso", true));
        answers.add(createAnswer("d", "Leonardo Da Vinci", false));

        return createQuestion(4, "Who painted the 'Guernica' ?", answers);
    }

    private static Question getSeventeenthQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Jolly Jumper", false));
        answers.add(createAnswer("b", "Rocinante", true));
        answers.add(createAnswer("c", "Margarita", false));
        answers.add(createAnswer("d", "John Doe", false));

        return createQuestion(4, "What is the name of Don Quixote de la Mancha's horse ?", answers);
    }

    private static Question getEighteenthQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Butane gas", false));
        answers.add(createAnswer("b", "Oxygen", false));
        answers.add(createAnswer("c", "Hydrogen", false));
        answers.add(createAnswer("d", "Ozone", true));

        return createQuestion(4, "What gas in the atmosphere protects us from ultraviolet radiation ?", answers);
    }

    private static Question getNineteenthQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Sugar Cane", false));
        answers.add(createAnswer("b", "Agave", true));
        answers.add(createAnswer("c", "Rosemary", false));
        answers.add(createAnswer("d", "Palm tree", false));

        return createQuestion(4, "From which plant is tequila made ?", answers);
    }

    private static Question getTwentiethQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Baker Street", true));
        answers.add(createAnswer("b", "Carnaby Street", false));
        answers.add(createAnswer("c", "Barking Road", false));
        answers.add(createAnswer("d", "Piccadilly Street", false));

        return createQuestion(4, "What fictional street did Sherlock Holmes live on ?", answers);
    }

    private static Question getTwentyFirstQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Roger Moore", false));
        answers.add(createAnswer("b", "Pierce Brosnan", false));
        answers.add(createAnswer("c", "Sean Connery", true));
        answers.add(createAnswer("d", "Daniel Craig", false));

        return createQuestion(4, "Which of the actors who played James Bond fought Dr. No ?", answers);
    }

    private static Question getTwentySecondQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Optimus Prime", true));
        answers.add(createAnswer("b", "Amazon Prime", false));
        answers.add(createAnswer("c", "Ironhide", false));
        answers.add(createAnswer("d", "Megatron", false));

        return createQuestion(4, "What is the name of the leader of the Autobots in the 'Transformers' movie saga ?", answers);
    }

    private static Question getTwentyThirdQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Madagascar", false));
        answers.add(createAnswer("b", "USA", false));
        answers.add(createAnswer("c", "China", false));
        answers.add(createAnswer("d", "Uk", true));

        return createQuestion(4, "Where was Ping-Pong invented?", answers);
    }

    private static Question getTwentyFourthQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "Miami Heat", false));
        answers.add(createAnswer("b", "Chicago Bulls", false));
        answers.add(createAnswer("c", "Boston Celtics", true));
        answers.add(createAnswer("d", "Los Angeles Lakers", false));

        return createQuestion(4, "What is the NBA team with the most titles ?", answers);
    }

    private static Question getTwentyFifthQuestionWithAnswers() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(createAnswer("a", "John Terry", false));
        answers.add(createAnswer("b", "Miroslav Klose", true));
        answers.add(createAnswer("c", "Diego Armando Maradona", false));
        answers.add(createAnswer("d", "Ronaldinho Gáucho", false));

        return createQuestion(4, "Who is the all-time top scorer in the football World Cup?", answers);
    }

    public static Question createQuestion(int id, String descriptionQuestion, ArrayList<Answer> answers) {
        //TODO: Add validations for parameters - answers.length...
        return new Question(id, descriptionQuestion, answers);
    }

    public static Answer createAnswer(String codeAnswer, String descriptionAnswer, boolean isCorrect) {
        return new Answer(codeAnswer, descriptionAnswer, isCorrect);
    }
}

