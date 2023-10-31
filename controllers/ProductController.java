package de.telran.g_280323_m_be_shop.controllers;

import de.telran.g_280323_m_be_shop.domain.entity.common.CommonProduct;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Product;
import de.telran.g_280323_m_be_shop.service.interfaces.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//II. ProductController
//        1) Вывести все продукты
//        2) Получить продукт по ID
//        3) Удалить продукт по ID
//        4) Добавить продукт
//        5) Вывести количество продуктов
//        6) Получить общую цену всех продуктов
//        7) Получить среднюю цену по всем продуктам
//        8) Удалить продукт по названию

@RestController
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAll();
    } // (GET) http://localhost:8080/products

    @GetMapping("/findProductById")
    public Product findProductById(@RequestParam("index") Integer index) {
        return productService.getById(index);
    } // (GET) http://localhost:8080/findProductById?index=2

    @PostMapping("/deleteProductById")
    public void deleteByID(@RequestParam("index") Integer index) {
        System.out.println("Deleting product " + productService.getById(index).getName());
        productService.deleteById(index);
    } // (POST) http://localhost:8080/deleteProductById?index=2

    @PostMapping("/addProduct")
    public void addProduct(@RequestParam("name") String name, @RequestParam("price") Double price) {
        CommonProduct addedProduct = new CommonProduct();
        addedProduct.setName(name);
        addedProduct.setPrice(price);
        productService.add(addedProduct);
        System.out.println("Added the " + addedProduct.getName() + " product worth "+ addedProduct.getPrice());
    } // (POST) http://localhost:8080/addProduct (USE THE "BODY" section!!!)

    @GetMapping("/countProducts")
    public int countProducts() {
        return productService.getCount();
    } // (GET) http://localhost:8080/countProducts

    @GetMapping("/totalPrice")
    public double totalPrice() {
        return productService.getTotalPrice();
    } // (GET) http://localhost:8080/totalPrice

    @GetMapping("/averagePrice")
    public double averagePrice() {
        return productService.getAveragePrice();
    } // (GET) http://localhost:8080/averagePrice

    @PostMapping("/deleteProductByName")
    public void deleteProductByName(@RequestParam("name") String name) {
        System.out.println("Deleting product " + name);
        productService.deleteByName(name);
    } // (POST) http://localhost:8080/deleteProductByName (USE THE "BODY" section!!!)

}
