package com.dongweima.utils.excel.bean;

import java.util.List;

public interface ExcelIterable<T> {

    int getPageSize();

    boolean hasNext();

    void next(List<T> subList);
}
