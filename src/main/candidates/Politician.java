package main.candidates;

import main.CIK;
import main.campaigns.CriminalCampaign;
import main.campaigns.NormalCampaign;
import main.educations.IPoliticianEdu;
import main.utils.Generator;

import java.util.Random;

public class Politician extends Candidate{

    public Politician(String name, String city, double money, IPoliticianEdu education, CIK cik) {
        super(name, city, money, education, cik);
    }

    @Override
    public void organizeCampaign() {
        int duration = Generator.generateRandomNumber(20, 25);
        double budget = this.giveMoneyForCampaign();
        Random r = new Random();
        if(r.nextBoolean()){
            this.campaign = new NormalCampaign(duration, budget, this, getCik());
        }
        else{
            this.campaign = new CriminalCampaign(duration, budget, this, getCik());
        }

        this.campaign.generateVoters();
    }
}
