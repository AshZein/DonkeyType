package Tests;

import Model.PhraseCorrectness;
import Model.PhraseState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PhraseCorrectnessTests {
    @Test
    void initTests(){
        PhraseCorrectness phraseCorrectness = new PhraseCorrectness();
        PhraseState ps = phraseCorrectness.getPhraseState();
        assertEquals(0, ps.getCursorPos());
        assertEquals("", ps.getPhrase());
        assertEquals(0, ps.getCorrectness().length);

        phraseCorrectness = new PhraseCorrectness("The Quick Brown");
        ps = phraseCorrectness.getPhraseState();
        assertEquals(0, ps.getCursorPos());
        assertEquals("The Quick Brown", ps.getPhrase());
    }


    @Test
    void addCharacter(){
        PhraseState ps;
        PhraseCorrectness phraseCorrectness = new PhraseCorrectness("The Quick Brown");
        phraseCorrectness.addCharacter('T');
        phraseCorrectness.addCharacter('H');
        phraseCorrectness.addCharacter('e');
        phraseCorrectness.addCharacter(' ');
        ps = phraseCorrectness.getPhraseState();

        assertEquals(4, ps.getCursorPos());

        boolean[] correctness = ps.getCorrectness();
        assertTrue(correctness[0]);
        assertFalse(correctness[1]);
        assertTrue(correctness[2]);
        assertTrue(correctness[3]);
        for (int i = 4; i < correctness.length; i++) {
            assertFalse(correctness[i]);
        }
    }

    @Test
    void removeCharacter(){
        PhraseState ps;
        PhraseCorrectness phraseCorrectness = new PhraseCorrectness("The Quick Brown");
        phraseCorrectness.addCharacter('T');
        phraseCorrectness.addCharacter('H');
        phraseCorrectness.addCharacter('e');
        phraseCorrectness.addCharacter(' ');
        ps = phraseCorrectness.getPhraseState();

        assertEquals(4, ps.getCursorPos());

        phraseCorrectness.removeCharacter();
        ps = phraseCorrectness.getPhraseState();

        assertEquals(3, ps.getCursorPos());
    }



}
