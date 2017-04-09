package com.company;

public class Main {
    public static volatile WordSet wordSetObject = new WordSet();
    public static Finisher finisher;

    public static void main(String[] args) {
        String resources[] = new String[]{"1.txt", "2.txt", "3.txt", "4.txt", "5.txt", "6.txt", "7.txt", "8.txt", "9.txt"};
        finisher = new Finisher(resources.length);

        if (1==2) {
            for (int i = 0; i < resources.length; i++) {
                new Searcher(resources[i], i);
                finisher.addThread(i);
            }
        } else {
            for (int i = 0; i < resources.length; i++) {
                new SearcherReentrant(resources[i], i);
                finisher.addThread(i);
            }
        }
    }
}
