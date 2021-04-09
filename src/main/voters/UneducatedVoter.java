package main.voters;

import main.CIK;
import main.candidates.Candidate;

public class UneducatedVoter extends Voter{
    public UneducatedVoter(String name, Gender gender, String city, Candidate favouriteCandidate, boolean isBought, CIK cik) {
        super(name, gender, city, favouriteCandidate, isBought, cik);
    }

    @Override
    int getChanceToVoteForGeneratedCandidate() {
        return 100;
    }

    @Override
    public int getChanceToVote() {
        return 90;
    }

    @Override
    int getChanceForInvalidBulletin() {
        return 40;
    }

    @Override
    public String getType() {
        return "UneducatedVoter";
    }
}
