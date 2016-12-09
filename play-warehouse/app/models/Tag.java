package models;

import com.avaje.ebean.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import play.data.validation.Constraints.Required;

import javax.persistence.ManyToMany;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "products")
public class Tag extends Model {
    public static final Find<Long, Tag> find = new Finder<>(Tag.class);

    public long id;
    @Required
    public String title;

    @ManyToMany(mappedBy = "tags")
    public List<Product> products;

    public Tag(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static Tag findById(Long id) {
        return find.byId(id);
    }
}
