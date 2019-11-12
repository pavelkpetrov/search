package com.softteco.text.service;

import com.softteco.text.model.Sentence;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class SentenceSplitter extends ParagraphSplitter<Sentence> {

    protected String getSplitCharacter(){
        return Pattern.quote(".");
    }

    protected Sentence createSentence(int id, String sentenceStr){
        Sentence sentence = new Sentence();
        sentence.setId(""+id);
        sentence.setSentence(sentenceStr.trim());
        return sentence;
    }

}
