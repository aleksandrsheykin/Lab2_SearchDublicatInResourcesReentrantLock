package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by admin on 10.04.2017.
 */
public class SearcherReentrant extends Thread {
    String res;
    Integer threadInd;

    public SearcherReentrant(String res, Integer threadInd) {
        this.threadInd = threadInd;
        this.res = res;
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        try {
            File file = new File(res);
            Scanner scanner = new Scanner(file);
            String word;

            while (scanner.hasNext()) {
                word = scanner.next();

                if (Pattern.compile("[a-zA-Z]+").matcher(word).matches()) {
                    System.out.println("File "+file+" has eng word");
                    return;
                }
                word = word.replaceAll("[^а-яА-Я]+", "");

                Main.wordSetObject.locker.lock();
                try {
                    if (Main.wordSetObject.flStop.get()) {
                        return;
                    }
                    if (!Main.wordSetObject.wordSet.add(word)) {
                        System.out.println("word '" + word + "' repeated in file " + res);
                        Main.wordSetObject.flStop.set(true);
                        Main.finisher.finish(this.threadInd);
                        Main.wordSetObject.locker.unlock();
                        return;
                    }
                } finally {
                    Main.wordSetObject.locker.unlock();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("file '"+res+"' not found");
            e.printStackTrace();
        }
    }

}

