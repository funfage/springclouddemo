package com.example.zrf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@Document(indexName = "springboot-logstash-logs")
public class LogEntity implements Serializable {

    private static final long serialVersionUID = 7773012536972676552L;

    @Id
    private String id;

    private String profile;

    private String app;

    private Integer port;

    private String traceId;

    private String method;

    private String host;

    private String level;

    private String message;

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "GMT+8")
    private Date dateTime;
//    @JsonProperty(value = "@timestamp")

}
