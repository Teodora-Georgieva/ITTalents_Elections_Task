package main.utils;

import java.util.Random;

public abstract class Generator {
    public static String[] cities = {"Sofia", "Plovdiv", "Varna", "Burgas", "Pernik", "Veliko Tarnovo"};
    public static String[] femaleNames = {"Ivana", "Elena", "Teodora", "Maria", "Petya", "Nicole", "Mia", "Sonya", "Radostina"};
    public static String[] maleNames = {"Ivan", "Evgeni", "Teodor", "Mario", "Petar", "Nikolai", "Martin", "Sasho", "Radoslav"};

    static Random r = new Random();

    public static String generateRandomCity(){
        return cities[r.nextInt(cities.length)];
    }

    public static int generateRandomNumber(int min, int max){

        return r.nextInt(max - min + 1) + min;
    }

    public static String generateFemaleName(){
        return femaleNames[r.nextInt(femaleNames.length)];
    }

    public static String generateMaleName(){
        return maleNames[r.nextInt(maleNames.length)];
    }
}