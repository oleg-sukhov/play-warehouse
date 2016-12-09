package models;

import com.avaje.ebean.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(of = "name")
@Entity
public class Warehouse extends Model {

    @Id
    public Long id;
    public String name;

    @OneToOne
    public Address address;

    @OneToMany(mappedBy = "warehouse")
    public List<StockItem> stockItems;
}
