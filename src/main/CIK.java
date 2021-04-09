package main;

import main.candidates.Candidate;
import main.utils.Generator;
import main.voters.Voter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CIK {
    private HashMap<String, HashSet<Bulletin>> bulletinsByCities; //contains valid and invalid bulletins
    private HashMap<Candidate, HashMap<String, Integer>> votesForCandidates; //contains only valid bulletins
    private HashSet<Candidate> allCandidates;
    private HashMap<String, HashSet<Voter>> allVoters;
    private HashMap<Candidate, Integer> totalVotesForCandidates;

    public CIK(){
        this.allCandidates = new HashSet<>();
        this.bulletinsByCities = new HashMap<>();
        this.votesForCandidates = new HashMap<>();
        this.allVoters = new HashMap<>();
        this.totalVotesForCandidates = new HashMap<>();
    }

    public void startElections(){
        for(String city : this.allVoters.keySet()){
            for(Voter voter : this.allVoters.get(city)) {
                if (Generator.generateRandomNumber(1, 100) <= voter.getChanceToVote()) {
                    Bulletin bulletin = voter.vote();
                    String voterCity = voter.getCity();
                    if (!this.bulletinsByCities.containsKey(voterCity)) {
                        this.bulletinsByCities.put(voterCity, new HashSet<>());
                    }

                    this.bulletinsByCities.get(voterCity).add(bulletin);
                }
            }
        }
    }

    public void makeRanking(){
        for(Candidate candidate : this.allCandidates){
            this.votesForCandidates.put(candidate, new HashMap<>());
            for(String city : this.bulletinsByCities.keySet()){
                this.votesForCandidates.get(candidate).put(city, 0);
            }
        }

        for(Map.Entry<String, HashSet<Bulletin>> e : this.bulletinsByCities.entrySet()){
            String city = e.getKey();
            HashSet<Bulletin> bulletins = e.getValue();
            for(Bulletin bulletin : bulletins){
                if(bulletin.isValid()){
                    Candidate candidate = bulletin.getCandidate();
                    int crrVotes = this.votesForCandidates.get(candidate).get(city);
                    this.votesForCandidates.get(candidate).put(city, crrVotes+1);
                }
            }
        }

        this.rankCandidatesByVotes();
    }

    public void showRanking(){
        for(Candidate candidate : this.votesForCandidates.keySet()){
            System.out.println(candidate.getName() + ":");
            for(Map.Entry<String, Integer> entry : this.votesForCandidates.get(candidate).entrySet()){
                System.out.println("    " + entry.getKey() + " - " + entry.getValue() + " votes");
            }
            System.out.println();
        }
    }

    private void rankCandidatesByVotes(){
        for(Candidate candidate : this.votesForCandidates.keySet()){
            int totalVotes = 0;
            for(Map.Entry<String, Integer> entry : this.votesForCandidates.get(candidate).entrySet()){
                totalVotes+=entry.getValue();
            }

            this.totalVotesForCandidates.put(candidate, totalVotes);
        }
    }

    public void showWinner(){
        ArrayList<Map.Entry<Candidate, Integer>> list = new ArrayList<>(this.totalVotesForCandidates.entrySet());
        list.sort((e1, e2) -> e2.getValue() - e1.getValue());

        Map.Entry<Candidate, Integer> winnerData = list.get(0);

        System.out.println("Showing all candidates and votes:");
        for(Map.Entry<Candidate, Integer> entry : list){
            System.out.println(entry.getKey().getName() + " - " + entry.getValue() + " votes");
        }
        System.out.println();
        System.out.println("Winner: ");
        System.out.println(winnerData.getKey().getName() + " - " + winnerData.getValue() + " votes");
    }

    public void showRunnerUp(){
        ArrayList<Map.Entry<Candidate, Integer>> list = new ArrayList<>(this.totalVotesForCandidates.entrySet());
        list.sort((e1, e2) -> e2.getValue() - e1.getValue());
        System.out.println("Runner Up: ");
        Map.Entry<Candidate, Integer> runnerUpData = list.get(1);
        System.out.println(runnerUpData.getKey().getName() + " - " + runnerUpData.getValue() + " votes");
    }

    private int calculateTotalCountOfVoters(){
        int total = 0;

        for(String city : this.bulletinsByCities.keySet()){
            HashSet<Bulletin> bulletins = this.bulletinsByCities.get(city);
            total+=bulletins.size();
        }

        return total;
    }

    public void showTotalCountOfVoters(){
        System.out.println("Total count of voters: " + calculateTotalCountOfVoters());
    }

    private double calculateTotalGeneratedVoters(){
        double totalGenerated = 0;
        for(Candidate candidate : this.allCandidates){
            int generated = candidate.getCampaign().getCountOfGeneratedVoters();
            totalGenerated+=generated;
        }

        return totalGenerated;
    }

    public void showVotersActivityPercentage(){
        double totalVoters = calculateTotalCountOfVoters();
        double totalGenerated = calculateTotalGeneratedVoters();

        double percent = (totalVoters/totalGenerated)*100;
        System.out.println("Percent of actually voted from all generated voters: " + percent + "%");
    }

    public void showVoterActivityByCities(){
        HashMap<String, Integer> hashMap = new HashMap<>();
        for(Map.Entry<String, HashSet<Bulletin>> entry : this.bulletinsByCities.entrySet()){
            hashMap.put(entry.getKey(), entry.getValue().size());
        }

        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(hashMap.entrySet());
        list.sort((e1, e2) -> e2.getValue() - e1.getValue());

        System.out.println("Showing voter activity by cities:");
        for(Map.Entry<String, Integer> entry : list){
            System.out.println(entry.getKey() + " - " + (entry.getValue()/calculateTotalGeneratedVoters())*100 + "%");
        }
    }

    private int calculateCountOfBoughtVoters(){
        int bought = 0;
        for(String city : this.bulletinsByCities.keySet()){
            HashSet<Bulletin> bulletins = this.bulletinsByCities.get(city);
            for(Bulletin bulletin : bulletins){
                if(bulletin.getVoter().isBought()){
                    bought++;
                }
            }
        }

        return bought;
    }

    private int calculateCountOfInvalidBulletins(){
        int invalid = 0;
        for(String city : this.bulletinsByCities.keySet()){
            HashSet<Bulletin> bulletins = this.bulletinsByCities.get(city);
            for(Bulletin bulletin : bulletins){
                if(!bulletin.isValid()){
                    invalid++;
                }
            }
        }

        return invalid;
    }

    public void showPercentOfInvalidBulletins(){
        int totalCountOfBulletins = calculateTotalCountOfVoters();
        int invalidBulletins = calculateCountOfInvalidBulletins();
        double percent = invalidBulletins*1.0/totalCountOfBulletins*100;
        System.out.println("Percent of invalid bulletins: " + percent + "%");
    }

    public void showPercentOfBoughtVotes(){
        int totalCountOfVoters = calculateTotalCountOfVoters();
        int countOfBoughtVoters = calculateCountOfBoughtVoters();
        double percent = (countOfBoughtVoters*1.0/totalCountOfVoters)*100;
        System.out.println("Percent of bought votes: " + percent + "%");
    }

    public void registerVoter(Voter voter){
        String city = voter.getCity();
        if(!this.allVoters.containsKey(city))
        {
            this.allVoters.put(city, new HashSet<>());
        }

        this.allVoters.get(city).add(voter);
    }

    public HashSet<Candidate> getAllCandidates() {
        HashSet<Candidate> copyCandidates = new HashSet<>(this.allCandidates);
        return copyCandidates;
    }

    public void registerCandidate(Candidate candidate){
        this.allCandidates.add(candidate);
    }

    public void showCandidatesByCities(){
        HashMap<String, HashSet<Candidate>> candidatesByCities = new HashMap<>();
        for(Candidate candidate : this.allCandidates){
            String city = candidate.getCity();
            if(!candidatesByCities.containsKey(city)){
                candidatesByCities.put(city, new HashSet<>());
            }

            candidatesByCities.get(city).add(candidate);
        }

        System.out.println("Showing candidates by cities:");
        for(String city : candidatesByCities.keySet()){
            System.out.println(city + ":");
            for(Candidate candidate : candidatesByCities.get(city)){
                System.out.println("    " + candidate.getName());
            }
            System.out.println();
        }
    }

    public void showFavouriteCandidateByVotersType(){
        HashMap<String, HashMap<Candidate, Integer>> byVotersType = new HashMap<>();

        for(HashSet<Bulletin> bulletins : this.bulletinsByCities.values()){
            for(Bulletin bulletin : bulletins){
                if(bulletin.isValid()){
                    String typeOfVoter = bulletin.getVoter().getType();
                    if(!byVotersType.containsKey(typeOfVoter)){
                        byVotersType.put(typeOfVoter, new HashMap<>());
                    }

                    Candidate candidate = bulletin.getCandidate();
                    if(!byVotersType.get(typeOfVoter).containsKey(candidate)){
                        byVotersType.get(typeOfVoter).put(candidate, 1);
                    }
                    else{
                        int crrCount = byVotersType.get(typeOfVoter).get(candidate);
                        byVotersType.get(typeOfVoter).put(candidate, crrCount + 1);
                    }
                }
            }
        }

        for(String typeOfVoter : byVotersType.keySet()){
            System.out.println(typeOfVoter);
            for(Map.Entry<Candidate, Integer> entry : byVotersType.get(typeOfVoter).entrySet()){
                System.out.println("    " + entry.getKey().getName() + " - " + entry.getValue() + " votes");
            }
            System.out.println();
        }

        System.out.println();

        for(String typeOfVoter : byVotersType.keySet()){
            int maxCountOfVoters = 0;
            String winner = null;
            System.out.print(typeOfVoter + ": ");
            for(Map.Entry<Candidate, Integer> entry : byVotersType.get(typeOfVoter).entrySet()){
                if(entry.getValue() > maxCountOfVoters){
                    maxCountOfVoters = entry.getValue();
                    winner = entry.getKey().getName();
                }
            }
            System.out.println("winner: " + winner + " - " + maxCountOfVoters + " votes");
        }
    }

    public void showCityWithMostVoters(){
        HashMap<String, Integer> citiesAndVoters = new HashMap<>();
        for(Map.Entry<String, HashSet<Bulletin>> entry : this.bulletinsByCities.entrySet()){
            citiesAndVoters.put(entry.getKey(), entry.getValue().size());
        }

        ArrayList<Map.Entry<String, Integer>> sorted = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : citiesAndVoters.entrySet()) {
            sorted.add(entry);
        }

        sorted.sort((e1, e2) -> e2.getValue() - e1.getValue());

        System.out.println("City with most voters: " + sorted.get(0).getKey());
    }

    public void showCityWithMinInvalidBulletins(){
        HashMap<String, Integer> citiesWithInvalidBulletins = new HashMap<>();
        for(String city : this.bulletinsByCities.keySet()){
            int countOfInvalid = 0;
            for(Bulletin bulletin : this.bulletinsByCities.get(city)){
                if(!bulletin.isValid()){
                    countOfInvalid++;
                }
            }

            citiesWithInvalidBulletins.put(city, countOfInvalid);
        }

        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(citiesWithInvalidBulletins.entrySet());
        list.sort((e1, e2) -> e1.getValue() - e2.getValue());
        System.out.println("City with min invalid bulletins: " + list.get(0).getKey() + " - " + list.get(0).getValue() +
                           " invalid bulletins");
    }

    public void showCityWithMaxBoughtVotes(){
        HashMap<String, Integer> citiesWithBoughtVotes = new HashMap<>();
        for(String city : this.bulletinsByCities.keySet()){
            int countOfBought = 0;
            for(Bulletin bulletin : this.bulletinsByCities.get(city)){
                if(!bulletin.getVoter().isBought()){
                    countOfBought++;
                }
            }

            citiesWithBoughtVotes.put(city, countOfBought);
        }

        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(citiesWithBoughtVotes.entrySet());
        list.sort((e1, e2) -> e2.getValue() - e1.getValue());
        System.out.println("City with max bought votes: " + list.get(0).getKey() + " - " + list.get(0).getValue() +
                " bought votes");
    }

    public void showNumberOfVotesForCandidatesByEducation(){
        HashMap<String, Integer> votesByEducationType = new HashMap<>();
        for(HashSet<Bulletin> bulletins : this.bulletinsByCities.values()){
            for(Bulletin bulletin : bulletins){
                if(bulletin.isValid()){
                    String educationType = bulletin.getCandidate().getEducation().getType();
                    if(!votesByEducationType.containsKey(educationType)){
                        votesByEducationType.put(educationType, 1);
                    }
                    else{
                        int crrCount = votesByEducationType.get(educationType);
                        votesByEducationType.put(educationType, crrCount+1);
                    }
                }
            }
        }

        System.out.println("Showing number of votes for candidates by education type:");
        for(Map.Entry<String, Integer> entry : votesByEducationType.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue() + " votes");
        }
    }
}