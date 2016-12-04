package controllers;

import models.Product;
import models.ProductStorage;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.products.details;
import views.html.products.list;

import javax.inject.Inject;

public class ProductController extends Controller {

    private final Form<Product> productForm;

    @Inject
    public ProductController(FormFactory formFactory) {
        this.productForm = formFactory.form(Product.class);
    }

    public Result list() {
        return ok(list.render(ProductStorage.products()));
    }

    public Result create() {
        return ok(details.render(productForm));
    }

    public Result details(String ean) {
        return ProductStorage.findByEan(ean)
                .map(product -> ok(details.render(productForm.fill(product))))
                .orElse(notFound("Can't find product with ean=" + ean));
    }

    public Result save() {
        Form<Product> bindedProductForm = productForm.bindFromRequest(request());
        if (bindedProductForm.hasErrors()) {
            flash("error", "Please correct form below!!!");
            return badRequest(details.render(productForm));
        }

        Product product = bindedProductForm.get();
        ProductStorage.saveOrUpdate(product);
        flash("success", "Product < " + product + " > has successfully saved!!!");
        return redirect(routes.ProductController.list());
    }

    public Result delete(String ean) {
        return ProductStorage.findByEan(ean)
                .map(product -> {
                    ProductStorage.delete(product);
                    return redirect(routes.ProductController.list());
                })
                .orElse(notFound("Can't find product with ean=" + ean));
    }
}
