package com.digiex.spring.boot.demo.controller;

/**
 * @author DiGiEx
 */
public interface APIName {

    // Base URL
    String BASE_API_URL = "/api";

    // Authenticate APIs
    String AUTHENTICATE_API = BASE_API_URL + "/auth";
    String AUTHENTICATE_INFO = "/info";
    String AUTHENTICATE_LOGIN = "/login";
    String AUTHENTICATE_LOGOUT = "/logout";

    // User APIs
    String USER_API = BASE_API_URL + "/user";

    String GET_DETAIL = "/detail/{id}";
    String UPDATE = "/update/{id}";
    String DELETE = "/delete/{id}";
    String GET_LIST = "/list";
    String GET_PAGE = "/page";
    String GET_INFO = "/info";

    //Clazz APIs
    String CLAZZ_API = BASE_API_URL + "/clazz";

    //Student APIs
    String STUDENT_API = BASE_API_URL + "/student";
    //Student history APIs
    String STUDENT_HISTORY_API = BASE_API_URL + "/studentHistory";

}
