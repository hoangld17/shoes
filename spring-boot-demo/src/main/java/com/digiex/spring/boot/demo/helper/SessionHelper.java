package com.digiex.spring.boot.demo.helper;

import com.digiex.spring.boot.demo.entity.Session;
import com.digiex.spring.boot.demo.common.enums.SessionType;
import com.digiex.spring.boot.demo.common.util.Constant;
import com.digiex.spring.boot.demo.common.util.DateUtil;
import com.digiex.spring.boot.demo.common.util.UniqueID;
import com.digiex.spring.boot.demo.entity.User;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Quy Duong
 */
@Component
public class SessionHelper {

    SimpleDateFormat sdf = new SimpleDateFormat(Constant.API_FORMAT_DATE);

    public Session createSession(User user) {
        //Create new session
        Session session = new Session();
        session.setId(UniqueID.getUUID());
        session.setUserId(user.getId());
        session.setCreatedDate(DateUtil.convertToUTC(new Date()));
        session.setType(SessionType.USER);
        try {
            session.setExpiryDate(sdf.parse("12/31/9999 00:00:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return session;
    }

}
