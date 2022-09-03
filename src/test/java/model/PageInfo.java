package model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
public class PageInfo {
    private int page;
    private int total;
    private int limit;

    public PageInfo() {
    }

    public PageInfo(int page, int total, int limit) {
        this.page = page;
        this.total = total;
        this.limit = limit;
    }
}
