package models;

import com.avaje.ebean.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
public class StockItem extends Model {
    public static final Find<Long, StockItem> find = new Finder<>(StockItem.class);

    @Id
    public Long id;
    public int quantity;

    @ManyToOne
    public Product product;

    @ManyToOne
    public Warehouse warehouse;
}
