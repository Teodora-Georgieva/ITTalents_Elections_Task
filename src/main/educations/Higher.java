package main.educations;

public class Higher extends Education implements IShowmanEdu, IPoliticianEdu{
    @Override
    public String getType() {
        return "Higher";
    }
}
