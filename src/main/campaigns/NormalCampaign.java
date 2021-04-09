package main.campaigns;

import main.CIK;
import main.candidates.Candidate;
import main.utils.Constants;
import main.utils.Generator;
import main.voters.MidTierVoter;
import main.voters.UneducatedVoter;
import main.voters.Voter;
import main.voters.WealthyVoter;

import java.util.Random;

public class NormalCampaign extends Campaign{
    public NormalCampaign(int duration, double budget, Candidate candidate, CIK cik) {
        super(duration, budget, candidate, cik);
    }

    @Override
    public void generateVoters() {
        for (int i = 0; i < this.getDuration(); i++) {
            for (int j = 0; j < Constants.NUM_OF_VOTERS_PER_DAY_LEGAL; j++) {
                int num = Generator.generateRandomNumber(1, 3);
                Voter voter = null;
                Random r = new Random();
                Voter.Gender gender = r.nextBoolean() ? Voter.Gender.FEMALE : Voter.Gender.MALE;
                String name = (gender == Voter.Gender.MALE) ? Generator.generateMaleName() : Generator.generateFemaleName();
                String city = Generator.generateRandomCity();
                switch(num){
                    case 1: voter = new UneducatedVoter(name, gender, city, getCandidate(), false, getCik()); break;
                    case 2: voter = new MidTierVoter(name, gender, city, getCandidate(), false, getCik()); break;
                    case 3: voter = new WealthyVoter(name, gender, city, getCandidate(), false, getCik()); break;
                }

                this.addVoter(voter);
                this.getCik().registerVoter(voter);
            }
        }
    }
}