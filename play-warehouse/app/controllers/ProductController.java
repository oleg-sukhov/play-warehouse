package controllers;

import actions.ExceptionLoggingAction;
import exceptions.ProductNotFoundException;
import models.Product;
import services.ProductService;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.products.details;
import views.html.products.list;

import javax.inject.Inject;

public class ProductController extends Controller {

    private final Form<Product> productForm;

    @Inject
    public ProductController(FormFactory formFactory) {
        this.productForm = formFactory.form(Product.class);
    }

    public Result index() {
        return redirect(routes.ProductController.list(0));
    }

    public Result list(Integer page) {
        return ok(list.render(ProductService.products()));
    }

    public Result create() {
        return ok(details.render(productForm));
    }

    @With(ExceptionLoggingAction.class)
    public Result details(String ean) {
        return ProductService.findByEan(ean)
                .map(product -> ok(details.render(productForm.fill(product))))
                .orElseThrow(() -> new ProductNotFoundException(ean));
    }

    public Result save() {
        Form<Product> bindedProductForm = productForm.bindFromRequest(request());
        if (bindedProductForm.hasErrors()) {
            flash("error", "Please correct form below!!!");
            return badRequest(details.render(productForm));
        }

        Product product = bindedProductForm.get();
        ProductService.saveOrUpdate(product);
        flash("success", "Product < " + product + " > has successfully saved!!!");
        return redirect(routes.ProductController.list(1));
    }

    @With(ExceptionLoggingAction.class)
    public Result delete(String ean) {
        return ProductService.findByEan(ean)
                .map(product -> {
                    ProductService.delete(product);
                    return redirect(routes.ProductController.list(1));
                })
                .orElseThrow(() -> new ProductNotFoundException(ean));
    }
}
