package com.softteco.text.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@Document(indexName = "full_response", type = "full_response")
@Getter
@Setter
@ToString
public class FullResponse {

    @Id
    private String id;

    @Field(type = Text, analyzer="english")
    private String sentence;

}
