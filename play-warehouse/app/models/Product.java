package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import exceptions.ProductNotFoundException;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import play.data.validation.Constraints.Required;
import play.mvc.PathBindable;
import validators.annotations.EAN;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

import static java.util.Optional.ofNullable;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
public class Product extends Model implements PathBindable<Product> {

    @Id
    public Long id;

    @EAN
    public String ean;

    @Required
    public String name;
    public String description;
    public List<Tag> tags;
    public byte[] picture;

    @Builder
    public Product(String ean, String name, String description, List<Tag> tags) {
        this.ean = ean;
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

    public void linkTags() {
        tags.forEach(tag -> tag.products.add(this));
    }

    @Override
    public Product bind(String key, String value) {
        Product product = Ebean.find(Product.class)
                .fetch("product")
                .where()
                .eq("product.ean", value)
                .findUnique();
        return ofNullable(product)
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