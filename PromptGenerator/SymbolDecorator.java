package PromptGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class SymbolDecorator extends PromptDecorator {
    final String[] symbs = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "{", "}", "[", "]", ":", ";", "'", ",", ".", "?"};
    public SymbolDecorator(PromptInterface wrappee){
        this.wrappee = wrappee;
    }

    @Override
    public String getNextPrompt() {
        String s = wrappee.getNextPrompt();
        ThreadLocalRandom r = ThreadLocalRandom.current();
        if (r.nextInt()%4 == 0 ){
            return s;
        }
        for (int i = 0; i < (r.nextInt(s.length()))/3; i++) {
            int n = r.nextInt(s.length()+1);
            s = s.substring(0, n) + symbs[r.nextInt(symbs.length)] + s.substring(n);
        }

        return s;
    }
}
