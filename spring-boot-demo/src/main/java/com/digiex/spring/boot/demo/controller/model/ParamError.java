package com.digiex.spring.boot.demo.controller.model;

public interface ParamError {

    String FIELD_NAME = "{fieldName} is ${validatedValue == null ? 'null' : 'empty'}";
    String MAX_LENGTH = "Maximum length of {fieldName} is {max} characters";
    String MIN_LENGTH = "Min length of {fieldName} is {min} characters";
    String DEFAULT = "{fieldName} default is {value}";

}
