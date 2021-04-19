package com.epam.esm.dao.helper;

import org.springframework.stereotype.Component;

@Component
public class GiftCertificateDaoQueryHelperImpl implements GiftCertificateDaoQueryHelper {

    @Override
    public String createQueryFindAllSortedByParam(String param, boolean orderDesc) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select * from gift_certificate order by ");
        stringBuilder.append(param);
        if (orderDesc) {
            stringBuilder.append(" desc;");
        } else {
            stringBuilder.append(" asc;");
        }
        return new String(stringBuilder);
    }

}