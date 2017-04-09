package com.company;

public class Main {
    public static volatile WordSet wordSetObject = new WordSet();
    public static Finisher finisher;

    public static void main(String[] args) {
        String resources[] = new String[] {"1.txt", "2.txt", "3.txt"};
        finisher = new Finisher(resources.length);

        for (int i=0; i<resources.length; i++) {
            new Searcher(resources[i], i);
            finisher.addThread(i);
        }

    }
}
