package models;

import com.avaje.ebean.Model;
import exceptions.ProductNotFoundException;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import play.data.validation.Constraints.Required;
import play.mvc.PathBindable;
import validators.annotations.EAN;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(exclude = {"stockItems", "tags"})
@Entity
public class Product extends Model implements PathBindable<Product> {

    public static final Find<Long, Product> find = new Finder<>(Product.class);

    @Id
    public Long id;

    @EAN
    public String ean;

    @Required
    public String name;
    public String description;
    public byte[] picture;

    @OneToMany(mappedBy = "product")
    public List<StockItem> stockItems = new ArrayList<>();

    @ManyToMany
    public List<Tag> tags = new ArrayList<>();

    @Builder
    public Product(String ean, String name, String description, List<Tag> tags) {
        this.ean = ean;
        this.name = name;
        this.description = description;
        this.tags = ofNullable(tags).orElseGet(Collections::emptyList);
    }

    public void linkTags() {
        tags.forEach(tag -> tag.products.add(this));
    }

    @Override
    public Product bind(String key, String value) {
        Product product = find.where().eq("ean", value).findUnique();
        return ofNullable(product)
                .map(prd -> {
                    prd.setTags(emptyList());
                    return prd;
                })
                .orElseThrow(() -> new ProductNotFoundException(value));
    }

    @Override
    public String unbind(String key) {
        return this.ean;
    }

    @Override
    public String javascriptUnbind() {
        return this.ean;
    }
}