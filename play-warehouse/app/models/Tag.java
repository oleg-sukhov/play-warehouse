package models;

import lombok.Data;
import lombok.ToString;
import play.data.validation.Constraints.Required;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "products")
public class Tag {

    public long id;
    @Required
    public String title;
    public List<Product> products = new ArrayList<>();

    public Tag(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
