package com.softteco.text.service;


import com.softteco.text.model.Answer;
import com.softteco.text.model.Sentence;

import java.util.Optional;

public interface ParagraphsService {

    Optional<Sentence> findAnswerInParagraph(String question);
    Optional<Answer> findRelevantAnswer(String paragraphAnswerSentence);

}