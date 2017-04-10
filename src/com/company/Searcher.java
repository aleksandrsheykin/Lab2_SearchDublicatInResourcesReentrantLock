package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by admin on 08.04.2017.
 */
public class Searcher extends Thread {
    private String res;
    private Integer threadInd;
    private WordSet wordSetObject;
    private Finisher finisher;

    public Searcher(String res, Integer threadInd, WordSet wordSetObject, Finisher finisher) {
        this.threadInd = threadInd;
        this.res = res;
        this.wordSetObject = wordSetObject;
        this.finisher = finisher;
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        try {
            File file = new File(res);
            Scanner scanner = new Scanner(file);
            String word;

            if (!scanner.hasNext()) {
                throw new IOException();
            }

            while (scanner.hasNext()) {
                word = scanner.next();

                if (Pattern.compile("[a-zA-Z]+").matcher(word).matches()) {
                    System.out.println("File "+file+" has eng word");
                    return;
                }
                word = word.replaceAll("[^а-яА-Я]+", "");

                synchronized (wordSetObject.wordSet) {
                    if (wordSetObject.flStop.get()) { return; }
                    if (!wordSetObject.wordSet.add(word)) {
                        System.out.println("word '"+word+"' repeated in file "+res);
                        wordSetObject.flStop.set(true);
                        finisher.finish(this.threadInd);
                        return;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("file '"+res+"' is not found");
        } catch (IllegalArgumentException e) {
            System.out.println("'"+res+"' is not file");
        } catch (SecurityException e) {
            System.out.println("file '"+res+"' is not available");
        } catch (IOException e) {
            System.out.println("file is not reading '"+res+"'");
        }
    }

}
