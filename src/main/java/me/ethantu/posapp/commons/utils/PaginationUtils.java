package me.ethantu.posapp.commons.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 13-11-8
 * Time: PM3:33
 */
public class PaginationUtils {

    public static final int MAX_PAGE_SIZE = 200;

    public static final int DEFAULT_PAGE = 1;

    public static final int verifyPage(String pageString) {
        int page;
        if (StringUtils.isEmpty(pageString)) {
            page = DEFAULT_PAGE;
        } else {
            try {
                page = Integer.parseInt(pageString);
                if (page < 1) {
                    page = DEFAULT_PAGE;
                }
            } catch (NumberFormatException ex) {
                page = DEFAULT_PAGE;
            }
        }
        return page;
    }

    public static final int verifyPageSize(String pageSizeString, int defaultValue) {
        int pageSize;
        if (StringUtils.isEmpty(pageSizeString)) {
            pageSize = defaultValue;
        } else {
            try {
                pageSize = Integer.parseInt(pageSizeString);
                if (pageSize < 1) {
                    pageSize = defaultValue;
                }
            } catch (NumberFormatException ex) {
                pageSize = defaultValue;
            }
        }
        return Math.min(pageSize, MAX_PAGE_SIZE);
    }
}
