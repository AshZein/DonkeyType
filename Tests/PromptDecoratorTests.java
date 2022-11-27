package Tests;

import PromptGenerator.PromptGenerator;
import PromptGenerator.PromptDecorator;
import PromptGenerator.NumberDecorator;
import PromptGenerator.SymbolDecorator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;


public class PromptDecoratorTests {
    @Test
    void NumberInitTests() {
        try {
            PromptGenerator pg = new PromptGenerator();
            PromptDecorator pd = new NumberDecorator(pg);
        } catch (Exception e) {
            System.out.println("Failed at NumberDecorator init ");
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    void getNumberPrompt() {
        try {
            PromptGenerator pg = new PromptGenerator();
            PromptDecorator pd = new NumberDecorator(pg);
            System.out.println(pd.getNextPrompt());
        } catch (Exception e) {
            System.out.println("Failed at NumberDecorator init ");
            System.out.println(e.getMessage());
            fail();
        }
    }


    @Test
    void getSymbolPrompt() {
        try {
            PromptGenerator pg = new PromptGenerator();
            PromptDecorator pd = new SymbolDecorator(pg);
            System.out.println(pd.getNextPrompt());
        } catch (Exception e) {
            System.out.println("Failed at SymbolDecorator init ");
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    void get200NumberPrompt() {
        try {
            PromptGenerator pg = new PromptGenerator();
            PromptDecorator pd = new SymbolDecorator(pg);
            for (int i = 0; i < 200; i++) {
                System.out.println(pd.getNextPrompt());
            }
        } catch (Exception e) {
            System.out.println("Failed at SymbolDecorator init ");
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    void get200SymbolPrompt() {
        try {
            PromptGenerator pg = new PromptGenerator();
            PromptDecorator pd = new SymbolDecorator(pg);
            for (int i = 0; i < 200; i++) {
                System.out.println(pd.getNextPrompt());
            }
        } catch (Exception e) {
            System.out.println("Failed at SymbolDecorator init ");
            System.out.println(e.getMessage());
            fail();
        }
    }
}
