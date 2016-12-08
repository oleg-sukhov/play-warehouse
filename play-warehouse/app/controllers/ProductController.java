package controllers;

import actions.ExceptionLoggingAction;
import com.avaje.ebean.Ebean;
import com.google.common.io.Files;
import exceptions.ProductNotFoundException;
import models.Product;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Result;
import play.mvc.With;
import views.html.products.details;
import views.html.products.list;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static java.util.Optional.ofNullable;

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
        return ok(list.render(Ebean.find(Product.class).findList()));
    }

    public Result create() {
        return ok(details.render(productForm));
    }

    @With(ExceptionLoggingAction.class)
    public Result details(Product product) {
        return ok(details.render(productForm.fill(product)));
    }

    public Result save() {
        Form<Product> bindedProductForm = productForm.bindFromRequest(request());
        if (bindedProductForm.hasErrors()) {
            flash("error", "Please correct form below!!!");
            return badRequest(details.render(bindedProductForm));
        }

        Product product = bindedProductForm.get();
        MultipartFormData body = request().body().asMultipartFormData();
        MultipartFormData.FilePart<File> part = body.getFile("picture");
        if (part != null) {
            File picture = part.getFile();
            try {
                product.picture = Files.toByteArray(picture);
            } catch (IOException e) {
                return internalServerError("Error reading file upload");
            }
        }

        Ebean.save(product);
        flash("success", "Product < " + product + " > has successfully saved!!!");
        return redirect(routes.ProductController.list(1));
    }

    public Result picture(String ean) {
        final Product product = findProductByEan(ean).orElseThrow(() -> new ProductNotFoundException(ean));
        if (product == null) return notFound("Can't load picture for product with ean" + ean);
        return ok(product.picture);
    }

    @With(ExceptionLoggingAction.class)
    public Result delete(String ean) {
        return findProductByEan(ean)
                .map(product -> {
                    Ebean.delete(product);
                    return redirect(routes.ProductController.list(1));
                })
                .orElseThrow(() -> new ProductNotFoundException(ean));
    }

    private Optional<Product> findProductByEan(String ean) {
        return ofNullable(Ebean.find(Product.class)
                .where()
                .eq("ean", ean)
                .findUnique());
    }
}
