package models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    public String ean;
    public String name;
    public String description;
}