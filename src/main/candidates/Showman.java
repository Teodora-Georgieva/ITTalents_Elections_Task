package main.candidates;

import main.CIK;
import main.campaigns.NormalCampaign;
import main.educations.IShowmanEdu;
import main.utils.Generator;

public class Showman extends Candidate{
    public Showman(String name, String city, double money, IShowmanEdu education, CIK cik) {
        super(name, city, money, education, cik);
    }

    @Override
    public void organizeCampaign() {
        int duration = Generator.generateRandomNumber(20, 25);
        double budget = this.giveMoneyForCampaign();
        this.campaign = new NormalCampaign(duration, budget, this, getCik());
        this.campaign.generateVoters();
    }
}