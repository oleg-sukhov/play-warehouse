package models;

import com.avaje.ebean.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import play.data.validation.Constraints.Required;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"products"})
@ToString(exclude = "products")
@Entity
public class Tag extends Model {
    public static final Find<Long, Tag> find = new Finder<>(Tag.class);

    @Id
    public Long id;
    @Required
    public String title;

    @ManyToMany(mappedBy = "tags")
    public List<Product> products = new ArrayList<>();

    public Tag(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static Tag findById(Long id) {
        return find.byId(id);
    }
}
