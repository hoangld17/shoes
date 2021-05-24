package com.digiex.spring.boot.demo.controller;

import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.common.util.ApplicationValueConfigure;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import com.digiex.spring.boot.demo.controller.model.ResponseUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author DiGiEx
 */
public abstract class AbstractBaseController {

    Gson gson = new Gson();

    @Autowired
    public ResponseUtil responseUtil;

    @Autowired
    ApplicationValueConfigure applicationValueConfigure;

    public void validatePaging(int pageNumber, int pageSize) {
        if (pageNumber < 1 || pageSize < 1) {
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Invalid paging request");
        }
    }
}
