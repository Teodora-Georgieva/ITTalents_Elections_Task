package main.voters;

import main.Bulletin;
import main.CIK;
import main.candidates.Candidate;
import main.utils.Generator;
import main.utils.Validator;

import java.util.HashSet;
import java.util.Objects;

public abstract class Voter {
    public enum Gender{
        MALE, FEMALE
    }

    private static int id = 1;
    private int voterID;
    private String name;
    private Gender gender;
    private String city;
    private Candidate favouriteCandidate;
    private boolean isBought;
    private CIK cik;


    public Voter(String name, Gender gender, String city, Candidate favouriteCandidate, boolean isBought, CIK cik) {
        this.voterID = id++;
        this.cik = cik;

        if(Validator.isValidString(name)) {
            this.name = name;
        }
        else{
            this.name = "Georgi Georgiev";
        }

        this.gender = gender;

        if(Validator.isValidString(city)) {
            this.city = city;
        }
        else{
            this.city = "Sofia";
        }

        this.favouriteCandidate = favouriteCandidate;
        this.isBought = isBought;
    }

    public Bulletin vote(){
        Candidate candidateToVoteFor = (Generator.generateRandomNumber(1, 100) < getChanceToVoteForGeneratedCandidate()) ?
                                        this.favouriteCandidate : this.chooseRandomCandidateToVoteFor();

        boolean isValid = (Generator.generateRandomNumber(1, 100) <= getChanceForInvalidBulletin()) ? false : true;

        Bulletin bulletin = new Bulletin(candidateToVoteFor, isValid, this);
        return bulletin;
    }

    private Candidate chooseRandomCandidateToVoteFor(){
        HashSet<Candidate> candidates = this.cik.getAllCandidates();
        candidates.remove(this.favouriteCandidate);

        System.out.println("favourite candidate: " + this.favouriteCandidate.getName());
        System.out.println("remaining candidates:");
        for(Candidate candidate : candidates){
            System.out.println(candidate.getName());
        }

        Candidate candidateToVoteFor = candidates.iterator().next(); //taka vseki put se vzima 1viq, a ne random
        return candidateToVoteFor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voter voter = (Voter) o;
        return voterID == voter.voterID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voterID);
    }

    abstract int getChanceToVoteForGeneratedCandidate();
    public abstract int getChanceToVote();
    abstract int getChanceForInvalidBulletin();
    public abstract String getType();

    public String getCity() {
        return city;
    }

    public boolean isBought() {
        return isBought;
    }
}
