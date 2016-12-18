package controllers;

import models.StockItem;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class StockItemController extends Controller {

    public Result index() {
        List<StockItem> stockItems = StockItem.find.findList();
        return ok(stockItems.toString());
    }
}
