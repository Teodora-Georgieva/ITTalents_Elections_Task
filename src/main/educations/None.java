package main.educations;

public class None extends Education implements IShowmanEdu, ICriminalEdu{
    @Override
    public String getType() {
        return "None";
    }
}
