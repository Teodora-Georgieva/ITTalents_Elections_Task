package main.candidates;

import main.CIK;
import main.campaigns.Campaign;
import main.educations.IEducation;
import main.utils.Validator;

import java.util.Objects;

public abstract class Candidate {
    private static int id = 1;
    private int candidateID;
    private String name;
    private String city;
    private double money;
    private boolean isBought;
    private IEducation education;
    Campaign campaign;
    private CIK cik;

    public Candidate(String name, String city, double money, IEducation education, CIK cik) {
        this.candidateID = id++;
        this.cik = cik;

        if(Validator.isValidString(name)) {
            this.name = name;
        }
        else{
            this.name = "Ivan Ivanov";
        }

        if(Validator.isValidString(city)) {
            this.city = city;
        }
        else {
            this.city = "Sofia";
        }

        if(money > 0) {
            this.money = money;
        }
        else{
            this.money = 100000;
        }

        this.education = education;
        this.cik.registerCandidate(this);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return candidateID == candidate.candidateID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidateID);
    }

    public abstract void organizeCampaign();

    public double giveMoneyForCampaign(){
        double moneyForCampaign = this.money;
        this.money = 0;
        return moneyForCampaign;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public CIK getCik() {
        return cik;
    }

    public String getCity() {
        return city;
    }

    public IEducation getEducation() {
        return education;
    }
}