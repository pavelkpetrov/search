package com.softteco.text.service;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public abstract class ParagraphSplitter<T> {

    public List<T> parse(List<String> lines) {
        String splitCharacter = getSplitCharacter();
        return StringUtils.isEmpty(splitCharacter)
                ? collectSentence(lines)
                : collectSentence(splitCharacter, StringUtils.join(lines, ""));
    }

    protected List<T> collectSentence(List<String> source) {
        AtomicInteger counter = new AtomicInteger(0);
        return source.stream().map(line -> createSentence(counter.incrementAndGet(), line)).collect(Collectors.toList());
    }

    protected List<T> collectSentence(String splitCharacter, String source) {
        List<T> sentences = new ArrayList<>();
        String[] splited = source.split(splitCharacter);
        int counter = 0;
        for (String token: splited) {
            sentences.add(createSentence(++counter, token));
        }
        return sentences;
    }

    protected abstract T createSentence(int id, String sentenceStr);

    protected abstract String getSplitCharacter();

}