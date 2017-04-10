package tests;

import com.company.Finisher;
import com.company.Searcher;
import com.company.SearcherReentrant;
import com.company.WordSet;
import org.junit.*;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

/**
 * Created by admin on 10.04.2017.
 */
public class MainTest {
    private static Finisher finisher;
    private static volatile WordSet wordSetObject;

    @BeforeClass
    public static void init() {
        finisher = new Finisher(1);
    }

    @Test
    public void testWordSet() {
        WordSet wordSet = new WordSet();
        assertFalse(wordSet.flStop.get());
        assertTrue(wordSet.wordSet.size() == 0);

        wordSet.locker.lock();
        wordSet.flStop.set(true);
        wordSet.wordSet.add("tests");
        assertTrue(wordSet.flStop.get());
        assertTrue(wordSet.wordSet.size() == 1);
        assertTrue(wordSet.locker.isLocked());
    }

    @Before
    public void initSearcherTest() {
        wordSetObject = new WordSet();
    }

    @Test
    public void searcherTest() {
        new Searcher("1.txt", 0, wordSetObject, finisher);
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(wordSetObject.wordSet.size() > 0);
    }

    @After
    public void clearSearcherTest() {
        wordSetObject.wordSet.clear();
        wordSetObject.flStop.set(false);
    }

    @Test
    public void searcherReentrantTest() {
        new SearcherReentrant("1.txt", 0, wordSetObject, finisher);
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(wordSetObject.wordSet.size() > 0);
    }

    @After
    public void clearSearcherReentrantTest() {
        wordSetObject.wordSet.clear();
        wordSetObject.flStop.set(false);
    }

    @Test
    public void ioExceptionsTest() {
        new Searcher("forTestNotTxt.txt", 0, wordSetObject, finisher);
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(wordSetObject.wordSet.size() == 0);
    }

    @After
    public void clearIoExceptionsTest() {
        wordSetObject.wordSet.clear();
        wordSetObject.flStop.set(false);
    }

    @Test
    public void fileNotFoundExceptionTest() {
        new Searcher("fileNotExist.txt", 0, wordSetObject, new Finisher(1));
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(wordSetObject.wordSet.size() == 0);
    }

}