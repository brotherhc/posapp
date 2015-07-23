package me.ethantu.posapp.persistence.hibernate.generic;

import java.util.List;

/**
 * Author: EthanTu
 * Date: 12-6-11
 * Time: 下午3:43
 */
public class Pagination<T> {

    private List<T> data;
    private int count;
    private int page;
    private int pageSize;

    public Pagination(List<T> data, int count, int page, int pageSize) {
        this.data = data;
        this.count = count;
        this.page = page;
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public int getCount() {
        return count;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPage() {
        return page;
    }
}
