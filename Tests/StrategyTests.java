package Tests;
import Controller.Context;
import Controller.Strategy.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

public class StrategyTests {

    @Test
    void symbolCheck() throws IOException {
        Context cont = new Context();
        NormalStrategy norm = new NormalStrategy();

        cont.setStrategy(norm);
        String[] symbols = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "{", "}", "[", "]", ":", ";", "'", ",", ".", "?"};

        String phrase = cont.executeStrategy(new int[] {1,0});

        boolean contains = false;
        while (!contains){
            for(String s: symbols){
                if (phrase.contains(s)){
                    contains = true;
                    break;
                }
            }
            phrase = cont.executeStrategy(new int[] {1,0});
        }
        assertTrue(contains);
    }

    @Test
    void numCheck() throws IOException {
        Context cont = new Context();
        NormalStrategy norm = new NormalStrategy();

        cont.setStrategy(norm);
        String[] symbols = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        String phrase = cont.executeStrategy(new int[] {0,1});

        boolean contains = false;
        while (!contains){
            for(String s: symbols){
                if (phrase.contains(s)){
                    contains = true;
                    break;
                }
            }
            phrase = cont.executeStrategy(new int[] {0,1});
        }
        assertTrue(contains);
    }

    @Test
    void numSymbolCheck() throws IOException {
        Context cont = new Context();
        NormalStrategy norm = new NormalStrategy();

        cont.setStrategy(norm);
        String[] nums = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] symbols = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "{", "}", "[", "]", ":", ";", "'", ",", ".", "?"};

        String phrase = cont.executeStrategy(new int[] {1,1});

        boolean[] contains = {false, false};

        while (!contains[0] && !contains[1]){


            for(String s: symbols){
                if (phrase.contains(s)){
                    contains[0] = true;
                    break;
                }
            }
            for (String n: nums){
                if (phrase.contains(n)){
                    contains[1] = true;
                    break;

                }
            }
            if(!contains[0] || !contains[1]){
                contains[0] = false;
                contains[1] = false;
            }
            phrase = cont.executeStrategy(new int[] {1,1});
        }
        assertTrue(contains[0]);
        assertTrue(contains[1]);
    }
}
