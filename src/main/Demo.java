package main;

import main.candidates.Candidate;
import main.candidates.Criminal;
import main.candidates.Politician;
import main.candidates.Showman;
import main.educations.*;
import main.utils.Generator;

import java.util.ArrayList;
import java.util.Random;

public class Demo {
    public static void main(String[] args) {
        CIK cik = new CIK();

        ArrayList<Candidate> candidates = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int num = Generator.generateRandomNumber(1, 3);
            String name = "Candidate" + (i+1);
            double money = Generator.generateRandomNumber(50000, 300000);
            String city = Generator.generateRandomCity();
            Random r = new Random();
            switch (num){
                case 1:
                    ICriminalEdu criminalEdu = null;
                    if(r.nextBoolean()){
                        criminalEdu = new None();
                    }
                    else{
                        criminalEdu = new Primary();
                    }
                    candidates.add(new Criminal(name, city, money, criminalEdu, cik)); break;

                case 2:
                    IShowmanEdu showmanEdu = null;
                    int number = Generator.generateRandomNumber(1, 3);
                    switch (number){
                        case 1: showmanEdu = new None();break;
                        case 2: showmanEdu = new Primary(); break;
                        default: showmanEdu = new Higher(); break;
                    }
                    candidates.add(new Showman(name, city, money, showmanEdu, cik)); break;

                default:
                    IPoliticianEdu politicianEdu = r.nextBoolean() ? new Secondary() : new Higher();
                    candidates.add(new Politician(name, city, money, politicianEdu, cik)); break;
            }
        }

        System.out.println("Candidates organizing campaigns...");
        for(Candidate candidate : candidates){
            candidate.organizeCampaign();
        }

        System.out.println();

        cik.startElections();
        System.out.println();
        cik.makeRanking();
        System.out.println();
        cik.showRanking();
        System.out.println();
        cik.showWinner();
        System.out.println();
        cik.showRunnerUp();
        System.out.println();
        cik.showTotalCountOfVoters();
        System.out.println();
        cik.showVotersActivityPercentage();
        System.out.println();
        cik.showVoterActivityByCities();
        System.out.println();
        cik.showPercentOfBoughtVotes();
        System.out.println();
        cik.showPercentOfInvalidBulletins();
        System.out.println();
        cik.showCandidatesByCities();
        System.out.println();
        cik.showFavouriteCandidateByVotersType();
        System.out.println();
        cik.showCityWithMostVoters();
        System.out.println();
        cik.showCityWithMinInvalidBulletins();
        System.out.println();
        cik.showCityWithMaxBoughtVotes();
        System.out.println();
        cik.showNumberOfVotesForCandidatesByEducation();
        System.out.println();
    }
}