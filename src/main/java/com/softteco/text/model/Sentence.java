package com.softteco.text.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@Document(indexName = "sentence", type = "sentence")
@Getter
@Setter
@ToString
public class Sentence {

    @Id
    private String id;

    @Field(type = Text, analyzer="english")
    private String sentence;

}
