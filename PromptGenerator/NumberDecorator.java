package PromptGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class NumberDecorator extends PromptDecorator {
    public NumberDecorator(PromptInterface wrappee){
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
            s = s.substring(0, n) + r.nextInt(10) + s.substring(n);
        }

        return s;
    }
}
