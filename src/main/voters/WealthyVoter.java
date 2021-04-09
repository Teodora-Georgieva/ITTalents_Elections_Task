package main.voters;

import main.CIK;
import main.candidates.Candidate;

public class WealthyVoter extends Voter{
    public WealthyVoter(String name, Gender gender, String city, Candidate favouriteCandidate, boolean isBought, CIK cik) {
        super(name, gender, city, favouriteCandidate, isBought, cik);
    }

    @Override
    int getChanceToVoteForGeneratedCandidate() {
        return 50;
    }

    @Override
    public int getChanceToVote() {
        return 50;
    }

    @Override
    int getChanceForInvalidBulletin() {
        return 0;
    }

    @Override
    public String getType() {
        return "WealthyVoter";
    }
}
