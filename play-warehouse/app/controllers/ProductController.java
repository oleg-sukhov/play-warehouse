package controllers;

import models.ProductStorage;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.product.list;

public class ProductController extends Controller {

    public Result list() {
        return ok(list.render(ProductStorage.products()));
    }

    public Result create() {
        return TODO;
    }

    public Result details(String ean) {
        return TODO;
    }

    public Result save() {
        return TODO;
    }

}
