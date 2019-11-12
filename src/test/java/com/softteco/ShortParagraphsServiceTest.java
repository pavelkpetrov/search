package com.softteco;

import com.softteco.text.model.Answer;
import com.softteco.text.model.Question;
import com.softteco.text.model.Sentence;
import com.softteco.text.repository.FullResponseRepository;
import com.softteco.text.repository.SentenceRepository;
import com.softteco.text.service.ParagraphFactory;
import com.softteco.text.service.ParagraphsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ShortParagraphsServiceTest {

    @Autowired
    private FullResponseRepository fullResponseRepository;

    @Autowired
    private SentenceRepository sentenceRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    @Autowired
    private ParagraphFactory paragraphFactory;

    @Autowired
    private ParagraphsService paragraphsService;


    @Before
    public void before() {
//        if (elasticsearchTemplate.indexExists(Sentence.class)) {
//            elasticsearchTemplate.deleteIndex(Sentence.class);
//        }
//        elasticsearchTemplate.createIndex(Sentence.class);
//        elasticsearchTemplate.putMapping(Sentence.class);
//        elasticsearchTemplate.refresh(Sentence.class);
//        loadSentences();
//        loadResponses();
//        if (elasticsearchTemplate.indexExists(FullResponse.class)) {
//            elasticsearchTemplate.deleteIndex(FullResponse.class);
//        }
//        elasticsearchTemplate.createIndex(FullResponse.class);
//        elasticsearchTemplate.putMapping(FullResponse.class);
//        elasticsearchTemplate.refresh(FullResponse.class);
    }

    private void loadSentences() {
        List<Sentence> sentences = paragraphFactory.getParagraphLines();
        sentenceRepository.saveAll(sentences);
    }

    //
    @Test
    public void testQuestionary() {
        List result = paragraphFactory.getQuestionsLines().stream()
            .map(question ->
                paragraphsService.findAnswerInParagraph(question.getQuestion())
                .map(sentence -> {
                    log.debug("Found sentence: {}", sentence);
                    return paragraphsService.findRelevantAnswer(sentence.getSentence())
                    .map(Answer::getResponse).orElse(null);
                }).orElse(null)
            ).collect(Collectors.toList());
        log.info("Sample Output");
        result.forEach(outStr -> log.info("{}", outStr));
        List<Question> standardAnswerLines = paragraphFactory.getStandardAnswerLines();
        Assert.assertEquals(standardAnswerLines.size(), result.size());
        for (int i = 0; i < standardAnswerLines.size(); i++) {
            String standardLine = standardAnswerLines.get(i) != null ? standardAnswerLines.get(i).getQuestion() : "";
            String answerLine = result.get(i) != null ? (String)result.get(i) : "";
            Assert.assertEquals(standardLine.toLowerCase(), answerLine.toLowerCase());
        }
    }



}
