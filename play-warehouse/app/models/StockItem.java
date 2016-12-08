package models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "warehouse")
public class StockItem {
    public Warehouse warehouse;
    public Product product;
    public int quantity;
}
