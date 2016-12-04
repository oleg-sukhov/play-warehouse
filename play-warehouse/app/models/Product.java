package models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import play.data.validation.Constraints;

@Data
@NoArgsConstructor
public class Product {

    @Constraints.Required
    public String ean;

    @Constraints.Required
    public String name;

    public String description;

    @Builder
    public Product(String ean, String name, String description) {
        this.ean = ean;
        this.name = name;
        this.description = description;
    }
}