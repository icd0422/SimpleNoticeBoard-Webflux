package com.example.simplenoticeboardwebflux.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = -2079884000812529703L;

    private Long id;

    private String name;

    private Integer age;

    private Character gender;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();
}
