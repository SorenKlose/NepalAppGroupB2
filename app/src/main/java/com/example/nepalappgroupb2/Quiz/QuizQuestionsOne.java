package com.example.nepalappgroupb2.Quiz;

public class QuizQuestionsOne {

    public String questions[] = {
            "When should you visit the health facility to learn about your childs health?",
            "What is the recommended amount of IFA-tablets per day?",
            "When should you start taking IFA-tablets?"
    };
    public String choises[][] = {
            {"First month", "Second month", "Third month", "Fourth month"},
            {"1 tablet", "2 tablets", "5 tablets", "As many as i want"},
            {"First day of pregnancy", "Fourth month", "Whenever i want", "Never"}
    };
    public String correct[] = {
            "Fourth month",
            "1 tablet",
            "Fourth month"
    };
    public String getQuestions(int i){
        String question = questions[i];
        return question;
    }
    public String getChoises1(int i){
        String choice = choises[i][0];
        return choice;
    }
    public String getChoises2(int i){
        String choice = choises[i][1];
        return choice;
    }
    public String getChoises3(int i){
        String choice = choises[i][2];
        return choice;
    }
    public String getChoises4(int i){
        String choice = choises[i][3];
        return choice;
    }
    public String getCorrectChoise(int i){
        String correctChoise = correct[i];
        return correctChoise;
    }
}
