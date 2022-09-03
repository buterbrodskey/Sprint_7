package model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderList {
    private PageInfo pageInfo;
    private List<Station> availableStations;
    private List<OrderDto> orders;

    public OrderList() {}

    public OrderList(PageInfo pageInfo, List<Station> availableStations, List<OrderDto> orders) {
        this.pageInfo = pageInfo;
        this.availableStations = availableStations;
        this.orders = orders;
    }
}
