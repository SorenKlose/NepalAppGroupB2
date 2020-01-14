package com.example.nepalappgroupb2.Quiz;

public class QuizQuestionsOne {

    public String questions[] = {
            "When should you visit the health facility to learn about your childs health?",
            "What is the recommended amount of IFA-tablets per day?",
            "When should you start taking IFA-tablets?",
            "What should you participate in every month during pregnancy?",
            "What should you eat every day?",
            "What should you do before drinking the water?",
            "What should you always do before preparing or eating a meal?",
            "What should you do while pregnant?",
            "How soon should you initiate breatsfeeding after birth?",
            "When should you visit the health facility after birth?",
            ""
    };

    public String choices[][] = {
            {"First month", "Second month", "Third month", "Fourth month"},
            {"1 tablet", "2 tablets", "5 tablets", "As many as i want"},
            {"First day of pregnancy", "Fourth month", "Whenever i want", "Never"},
            {"A mothers guide group", "Healthy group for mothers", "Health Mother's Group", "Healthy Child's Group"},
            {"Eggs, meat and milk products", "Fish and egg products", "Noodles and chips", "Meat noodles and chips products"},
            {"Nothing, just drink it", "Boil or filter", "Add suger", "Add salt"},
            {"Rinse hands with cold water", "Wake up the child", "Wash hands with soap", "Say a prayer"},
            {"Be more active than normal", "Drink alcohol to disinfect", "Do household work yourself", "Rest a lot"},
            {"Within 1 hour", "Within 2 hours", "Within 5 hours", "Within 10 hours"},
            {"The next day", "Within a month", "Within a week", "It doesn't matter"},
            {"","","",""}
    };

    public String correct[] = {
            "Fourth month",
            "1 tablet",
            "Fourth month",
            "Health Mother's Group",
            "Eggs, meat and milk products",
            "Boil or filter",
            "Wash hands with soap",
            "Rest a lot",
            "Within 1 hour",
            "Within a week",
            ""
    };

    public String getQuestions(int i){
        String question = questions[i];
        return question;
    }
    public String getChoices1(int i){
        String choice = choices[i][0];
        return choice;
    }
    public String getChoices2(int i){
        String choice = choices[i][1];
        return choice;
    }
    public String getChoices3(int i){
        String choice = choices[i][2];
        return choice;
    }
    public String getChoices4(int i){
        String choice = choices[i][3];
        return choice;
    }
    public String getCorrectChoice(int i){
        String correctChoice = correct[i];
        return correctChoice;
    }
}
