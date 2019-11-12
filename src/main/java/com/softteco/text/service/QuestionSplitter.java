package com.softteco.text.service;

import com.softteco.text.model.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionSplitter extends ParagraphSplitter<Question> {

    protected String getSplitCharacter(){
        return null;
    }

    protected Question createSentence(int id, String sentenceStr){
        Question sentence = new Question();
        sentence.setId(""+id);
        sentence.setQuestion(sentenceStr.trim().replace("?", ""));
        return sentence;
    }

}
