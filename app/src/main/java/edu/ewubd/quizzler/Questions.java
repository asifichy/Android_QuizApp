package edu.ewubd.quizzler;

public class Questions {
    String ID = "";
    String question = "";
    String optionA = "";
    String optionB = "";
    String optionC = "";
    String optionD = "";
    String answer = "";
    String category = "";

    public Questions(String ID, String question, String optionA, String optionB, String optionC, String optionD, String answer, String category) {
        this.ID = ID;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.category = category;
    }
}
