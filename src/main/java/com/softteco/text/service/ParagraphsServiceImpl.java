package com.softteco.text.service;

import com.softteco.text.model.Answer;
import com.softteco.text.model.FullResponse;
import com.softteco.text.model.Sentence;
import com.softteco.text.repository.FullResponseRepository;
import com.softteco.text.repository.SentenceRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ParagraphsServiceImpl implements ParagraphsService {

    private SentenceRepository sentenceRepository;
    private FullResponseRepository fullResponseRepository;
    private ParagraphFactory paragraphFactory;

    @Autowired
    public void setSentenceRepository(SentenceRepository sentenceRepository,
                                      FullResponseRepository fullResponseRepository,
                                      ParagraphFactory paragraphFactory) {
        this.sentenceRepository = sentenceRepository;
        this.fullResponseRepository = fullResponseRepository;
        this.paragraphFactory = paragraphFactory;
    }

    @Override
    public Optional<Sentence> findAnswerInParagraph(String question) {
        log.debug("findAnswerInParagraph: question {}", question);
        QueryBuilder qb = QueryBuilders.queryStringQuery(question);
        Pageable pageable = PageRequest.of (0, 1);
        Iterable<Sentence> res = sentenceRepository.search(qb, pageable);
        return res.iterator().hasNext() ? Optional.of(res.iterator().next())  : Optional.empty();
    }

    @Override
    public Optional<Answer> findRelevantAnswer(String paragraphAnswerSentence){
        log.debug("findRelevantAnswer: paragraphAnswerSentence {}", paragraphAnswerSentence);
        fullResponseRepository.deleteAll();
        fullResponseRepository.save(createFullResponse(paragraphAnswerSentence));
        List<Answer> answers = paragraphFactory.getAnswerLines();
        return answers.stream()
                .map(answer -> matchAnswerInFullResponse(answer.getResponse()) != null ? answer : null)
                .filter(Objects::nonNull)
                .findAny();
    }

    protected FullResponse matchAnswerInFullResponse(String answer){
        QueryBuilder qbmm = QueryBuilders.multiMatchQuery(answer, "sentence")
                .type(MultiMatchQueryBuilder.Type.PHRASE).boost(10);
        QueryBuilder qb = QueryBuilders.boolQuery().must(qbmm);
        Iterable<FullResponse> it = fullResponseRepository.search(qb);
        return it.iterator().hasNext() ? it.iterator().next() : null;
    }

    protected FullResponse createFullResponse(String paragraphAnswerSentence){
        FullResponse result = new FullResponse();
        result.setSentence(paragraphAnswerSentence);
        result.setId("1");
        return result;
    }




}