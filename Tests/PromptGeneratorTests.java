package Tests;

import PromptGenerator.PromptGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PromptGeneratorTests {
    @Test
    void initTests(){
        try {
            PromptGenerator pg = new PromptGenerator();
        } catch (Exception e){
            System.out.println("Failed at PromptGenerator init ");
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    void GenerateWords(){
        try {
            PromptGenerator pg = new PromptGenerator();
            System.out.println(pg.getNextPrompt());
        } catch (Exception e){
            System.out.println("Failed at PromptGenerator init ");
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    void GenerateWordsHeavy(){
        try {
            PromptGenerator pg = new PromptGenerator();
            for (int i = 0; i < 10000; i++) {
                pg.getNextPrompt();
            }
        } catch (Exception e){
            System.out.println("Failed at PromptGenerator init ");
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
}
