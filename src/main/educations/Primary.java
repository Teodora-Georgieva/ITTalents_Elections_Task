package main.educations;

public class Primary extends Education implements IShowmanEdu, ICriminalEdu{
    @Override
    public String getType() {
        return "Primary";
    }
}
