package models;

import com.avaje.ebean.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Address extends Model {

    @Id
    public Long id;
    public String street;
    public String number;
    public String postalCode;
    public String city;
    public String country;

    @OneToOne(mappedBy = "address")
    public Warehouse warehouse;

}
