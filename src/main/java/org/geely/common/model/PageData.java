package org.geely.common.model;

import lombok.Data;

/**
 * @Description: 分页数据
 */
@Data
public class PageData<T> {

    private T data;

    private long current;

    private long size;

    private long total;

    public PageData() {
    }

    public PageData(T data, long current, long size, long total) {
        this.data = data;
        this.current = current;
        this.size = size;
        this.total = total;
    }

    public PageData(long current, long size, long total) {
        this.current = current;
        this.size = size;
        this.total = total;
    }

}
