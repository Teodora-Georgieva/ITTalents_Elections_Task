package main.campaigns;

import main.CIK;
import main.candidates.Candidate;
import main.voters.Voter;

import java.util.HashSet;

public abstract class Campaign {
    private int duration;
    private double budget;
    private Candidate candidate;
    private HashSet<Voter> generatedVoters;
    private CIK cik;

    public Campaign(int duration, double budget, Candidate candidate, CIK cik) {
        this.cik = cik;
        if(isValidDuration(duration)) {
            this.duration = duration;
        }
        else{
            this.duration = 22;
        }

        if(budget > 0) {
            this.budget = budget;
        }
        else{
            this.budget = 100000;
        }

        this.candidate = candidate;
        this.generatedVoters = new HashSet<>();
    }

    public abstract void generateVoters();

    private boolean isValidDuration(int duration){
        return duration >= 20 && duration <= 25;
    }

    void addVoter(Voter voter){
        this.generatedVoters.add(voter);
    }

    public int getDuration() {
        return duration;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public int getCountOfGeneratedVoters(){
        return this.generatedVoters.size();
    }

    public double getBudget() {
        return budget;
    }

    public void decreaseBudget(int money){
        if(money <= this.budget) {
            this.budget -= money;
        }
    }

    public CIK getCik() {
        return cik;
    }
}