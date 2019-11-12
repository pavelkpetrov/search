package com.softteco.text.service;

import com.softteco.text.model.Answer;
import org.springframework.stereotype.Component;

@Component
public class AnswerSplitter extends ParagraphSplitter<Answer> {

    protected String getSplitCharacter(){
        return ";";
    }

    protected Answer createSentence(int id, String sentenceStr){
        Answer sentence = new Answer();
        sentence.setId(""+id);
        sentence.setResponse(sentenceStr.trim());
        return sentence;
    }

}
