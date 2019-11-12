package com.softteco;

import com.softteco.text.model.Answer;
import com.softteco.text.model.Question;
import com.softteco.text.model.Sentence;
import com.softteco.text.service.ParagraphFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ParagraphSplitterTest {

    @Value("${search.test.txt.path}")
    private String testPath;
    @Value("${search.test.short.paragraph}")
    private String shortParagraphLines;
    @Value("${search.test.total.questions}")
    private String totalQuestionsLines;
    @Value("${search.test.answers}")
    private String answerLines;

    @Autowired
    private ParagraphFactory paragraphFactory;

    @Before
    public void before() throws IOException {
        log.info("Init test");
        paragraphFactory.init();
    }

    @Test
    public void testParagraphSplitter() {
        File f = new File("test.txt");
        log.info("Current path {}", f.getAbsolutePath());

        try {
            String[] tokens =  totalQuestionsLines.split(ParagraphFactory.RANGE_SPLITTER);
            List<Question> qLines = paragraphFactory.getQuestionsLines();
            Assert.assertTrue(qLines.size() == ((Integer.valueOf(tokens[1]) -  Integer.valueOf(tokens[0])) + 1));
            List<Answer> aLines = paragraphFactory.getAnswerLines();
            Assert.assertTrue(!aLines.isEmpty());
            List<Sentence> sLines = paragraphFactory.getParagraphLines();
            Assert.assertTrue(!sLines.isEmpty());

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

}
