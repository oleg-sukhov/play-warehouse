package models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Warehouse {
    public String name;
    public List<StockItem> stockItems = new ArrayList<>();
}
