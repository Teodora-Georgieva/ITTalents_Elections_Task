package main;

import main.candidates.Candidate;
import main.voters.Voter;

import java.util.Objects;

public class Bulletin {
    private static int id = 1;
    private int bulletinID;
    private Candidate candidate;
    private boolean isValid;
    private Voter voter;

    public Bulletin(Candidate candidate, boolean isValid, Voter voter) {
        this.bulletinID = id++;
        this.candidate = candidate;
        this.isValid = isValid;
        this.voter = voter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bulletin bulletin = (Bulletin) o;
        return bulletinID == bulletin.bulletinID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bulletinID);
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public boolean isValid() {
        return isValid;
    }

    public Voter getVoter() {
        return voter;
    }
}