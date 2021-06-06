package com.digiex.spring.boot.demo.common.validator;

import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import com.digiex.spring.boot.demo.controller.model.request.CreateStudentHistoryRequest;
import com.digiex.spring.boot.demo.controller.model.request.CreateStudentRequest;

import java.util.List;
import java.util.Optional;

public class StudentHistoryValidator {
    public static void checkValidStudentHistory(List<CreateStudentHistoryRequest> createStudentHistoryRequests) {
        Optional<CreateStudentHistoryRequest> checkNameSchool = createStudentHistoryRequests.stream()
                .filter(x -> x.getNameSchool() == null || x.getNameSchool().trim().isEmpty())
                .findAny();
        if (checkNameSchool.isPresent()) {
            throw new ApplicationException(APIStatus.BAD_REQUEST, "Name school is null or empty");
        }
        Optional<CreateStudentHistoryRequest> checkAddress = createStudentHistoryRequests.stream()
                .filter(x -> x.getAddress() == null || x.getAddress().trim().isEmpty())
                .findAny();
        if (checkAddress.isPresent()) {
            throw new ApplicationException(APIStatus.BAD_REQUEST, "Address is null or empty");
        }
        Optional<CreateStudentHistoryRequest> checkStartDate = createStudentHistoryRequests.stream()
                .filter(x -> x.getStartDate() == null || x.getStartDate().trim().isEmpty())
                .findAny();
        if (checkStartDate.isPresent()) {
            throw new ApplicationException(APIStatus.BAD_REQUEST, "Start date is null or empty");
        }
        Optional<CreateStudentHistoryRequest> checkEndDate = createStudentHistoryRequests.stream()
                .filter(x -> x.getEndDate() == null || x.getEndDate().trim().isEmpty())
                .findAny();
        if (checkEndDate.isPresent()) {
            throw new ApplicationException(APIStatus.BAD_REQUEST, "End date is null or empty");
        }
    }

}
