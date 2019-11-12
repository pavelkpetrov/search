package com.softteco.text.service;

import com.softteco.text.model.Answer;
import com.softteco.text.model.Question;
import com.softteco.text.model.Sentence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class ParagraphFactory {

    public final static String RANGE_SPLITTER = ":";

    @Value("${search.test.txt.path}")
    private String testPath;
    @Value("${search.test.short.paragraph}")
    private String shortParagraphLines;
    @Value("${search.test.total.questions}")
    private String totalQuestionsLines;
    @Value("${search.test.answers}")
    private String answerLines;
    @Value("${search.test.standard.answers}")
    private String standardAnswerLines;

    private List<String> lines;

    private SentenceSplitter sentenceSplitter;
    private AnswerSplitter answerSplitter;
    private QuestionSplitter questionSplitter;

    @Autowired
    public ParagraphFactory(SentenceSplitter sentenceSplitter,
                            AnswerSplitter answerSplitter,
                            QuestionSplitter questionSplitter){
        this.sentenceSplitter = sentenceSplitter;
        this.answerSplitter = answerSplitter;
        this.questionSplitter = questionSplitter;
    }

    @PostConstruct
    public void init() throws IOException {
        log.debug("Init factory");
        lines = new ArrayList<>();
        File scannedFile = new File(testPath);
        List<String> resultL;
        try (Stream<String> lines = Files.lines(Paths.get(scannedFile.getAbsolutePath()))) {
            log.debug("Init factory try to scan testPath {} exists {}", scannedFile.getAbsolutePath(),  scannedFile.exists());
            resultL = lines.collect(Collectors.toList());
            this.lines = resultL;
            log.debug("Init factory collect {} lines", this.lines.size());
        }
    }

    public List<Sentence> getParagraphLines(){
        List<String> actualLines =  getLines(shortParagraphLines);
        log.debug("Actual paragraphLines \n {}", Arrays.toString(actualLines.toArray()));
        return sentenceSplitter.parse(actualLines);
    }

    public List<Question> getQuestionsLines(){
        List<String> actualLines =  getLines(totalQuestionsLines);
        log.debug("Actual questionsLines \n {}", Arrays.toString(actualLines.toArray()));
        return questionSplitter.parse(actualLines);
    }

    public List<Answer> getAnswerLines(){
        List<String> actualLines =  getLines(answerLines);
        log.debug("Actual answerLines \n {}", Arrays.toString(actualLines.toArray()));
        return answerSplitter.parse(actualLines);
    }

    public List<Question> getStandardAnswerLines(){
        List<String> actualLines =  getLines(standardAnswerLines);
        log.debug("Actual standardAnswerLines \n {}", Arrays.toString(actualLines.toArray()));
        return questionSplitter.parse(actualLines);
    }

    private List<String> getLines(int start, int end) {
        return lines.subList(start, end);
    }

    protected List<String> collectLines(BreakIterator bi, String source) {
        List<String> lines = new ArrayList<>();
        bi.setText(source);
        int lastIndex = bi.first();
        while (lastIndex != BreakIterator.DONE) {
            int firstIndex = lastIndex;
            lastIndex = bi.next();
            if (lastIndex != BreakIterator.DONE) {
                String sentenceStr = source.substring(firstIndex, lastIndex);
                lines.add(sentenceStr);
            }
        }
        return lines;
    }

    private List<String> getLines(String rangeStr) {
        String[] range = rangeStr.split(RANGE_SPLITTER);
        return getLines(Integer.valueOf(range[0]) , Integer.valueOf(range[1]) + 1);
    }

}
