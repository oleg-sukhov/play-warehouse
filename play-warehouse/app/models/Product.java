package models;

import exceptions.ProductNotFoundException;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import play.data.validation.Constraints.Required;
import play.mvc.PathBindable;
import services.ProductService;

import java.util.List;

@Data
@NoArgsConstructor
public class Product implements PathBindable<Product> {

    @Required
    public String ean;
    @Required
    public String name;
    public String description;
    public List<Tag> tags;

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
        return ProductService.findByEan(value)
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