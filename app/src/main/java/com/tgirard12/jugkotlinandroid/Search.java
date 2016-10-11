package com.tgirard12.jugkotlinandroid;

import java.util.List;

public class Search {

    private int total_count;
    private boolean incomplete_results;
    private List<SearchItem> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Search search = (Search) o;

        if (total_count != search.total_count) return false;
        if (incomplete_results != search.incomplete_results) return false;
        return items != null ? items.equals(search.items) : search.items == null;

    }

    @Override
    public int hashCode() {
        int result = total_count;
        result = 31 * result + (incomplete_results ? 1 : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<SearchItem> getItems() {
        return items;
    }

    public void setItems(List<SearchItem> items) {
        this.items = items;
    }
}
