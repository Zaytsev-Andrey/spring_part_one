package ru.geekbrains.controller;


import java.math.BigDecimal;

public class ProductListParams {
    private String titleFilter;
    private BigDecimal minCostFilter;
    private BigDecimal maxCostFilter;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortOrder;

    public String getTitleFilter() {
        return titleFilter;
    }

    public void setTitleFilter(String titleFilter) {
        this.titleFilter = titleFilter;
    }

    public BigDecimal getMinCostFilter() {
        return minCostFilter;
    }

    public void setMinCostFilter(BigDecimal minCostFilter) {
        this.minCostFilter = minCostFilter;
    }

    public BigDecimal getMaxCostFilter() {
        return maxCostFilter;
    }

    public void setMaxCostFilter(BigDecimal maxCostFilter) {
        this.maxCostFilter = maxCostFilter;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
