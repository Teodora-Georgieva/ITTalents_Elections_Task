package main.voters;

import main.CIK;
import main.candidates.Candidate;

public class MidTierVoter extends Voter{
    public MidTierVoter(String name, Gender gender, String city, Candidate favouriteCandidate, boolean isBought, CIK cik) {
        super(name, gender, city, favouriteCandidate, isBought, cik);
    }

    @Override
    int getChanceToVoteForGeneratedCandidate() {
        return 70;
    }

    @Override
    public int getChanceToVote() {
        return 70;
    }

    @Override
    int getChanceForInvalidBulletin() {
        return 10;
    }

    @Override
    public String getType() {
        return "MidTierVoter";
    }
}
