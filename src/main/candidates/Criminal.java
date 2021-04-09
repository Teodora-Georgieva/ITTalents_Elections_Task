package main.candidates;

import main.CIK;
import main.campaigns.NormalCampaign;
import main.educations.ICriminalEdu;
import main.utils.Generator;

public class Criminal extends Candidate{
    public Criminal(String name, String city, double money, ICriminalEdu education, CIK cik) {
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