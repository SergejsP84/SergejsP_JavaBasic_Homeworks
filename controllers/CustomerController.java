package de.telran.g_280323_m_be_shop.controllers;

import de.telran.g_280323_m_be_shop.domain.entity.common.CommonCustomer;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Customer;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Product;
import de.telran.g_280323_m_be_shop.service.common.CommonCustomerService;
import de.telran.g_280323_m_be_shop.service.interfaces.CustomerService;
import de.telran.g_280323_m_be_shop.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//I. CustomerController
//        1) Вывести всех клиентов
//        2) Получить клиента по ID
//        3) Удалить клиента по ID
//        4) Добавить клиента
//        5) Вывести количество клиентов
//        6) Получить общую цену корзины по АйДи
//        7) Получить среднюю цену корзины по АйДи
//        8) Удалить клиента по имени
//        9) Добавить товар в корзину по АйДи
//        10) Удалить товар из корзины по АйДи
//        11) Очистить корзину по АйДи

@RestController
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAll();
    } // (GET) http://localhost:8080/customers

    @GetMapping("/findCustomerById")
    public Customer findCustomerById(@RequestParam("index") Integer index) {
        return customerService.getById(index);
    } // (GET) http://localhost:8080/findCustomerById?index=1

    @PostMapping("/deleteCustomerById")
    public void deleteByID(@RequestParam("index") Integer index) {
        System.out.println("Deleting customer " + customerService.getById(index).getName());
        customerService.deleteById(index);
    } // (POST) http://localhost:8080/deleteProductById?index=2

    @PostMapping("/addCustomer")
    public void addCustomer(@RequestParam("index") Integer index) {
        CommonCustomer commonCustomer = new CommonCustomer();
        System.out.println("Adding a new customer");
        customerService.add(commonCustomer);
    } // (POST) http://localhost:8080/addCustomer

    @GetMapping("/countCustomers")
    public int countCustomers() {
        return customerService.getCount();
    } // (GET) http://localhost:8080/countCustomers

    @GetMapping("/cartPriceById")
    public double cartPriceById(@RequestParam("id") Integer id) {
        return customerService.getTotalPriceById(id);
    } // (GET) http://localhost:8080/cartPriceById

    @GetMapping("/averageCartPriceById")
    public double averageCartPriceById(@RequestParam("id") Integer id) {
        return customerService.getAveragePriceById(id);
    } // (GET) http://localhost:8080/averageCartPriceById

    @PostMapping("/deleteCustomerByName")
    public void deleteCustomerByName(@RequestParam("name") String name) {
        System.out.println("Deleting customer " + name);
        customerService.deleteByName(name);
    } // (POST) http://localhost:8080/deleteCustomerByName (USE THE "BODY" section!!!)

    @PostMapping("/addProductToCartById")
    public void addProductToCartById(@RequestParam("customerId") Integer customerId, @RequestParam("productId") Integer productId) {
        customerService.addToCartById(customerId, productId);
    } // (POST) http://localhost:8080/addProductToCartById (USE THE "BODY" section!!!)

    @PostMapping("/removeProductFromCartById")
    public void removeProductFromCartById(@RequestParam("customerId") Integer customerId, @RequestParam("productId") Integer productId) {
        customerService.deleteFromCartById(customerId, productId);
    } // (POST) http://localhost:8080/removeProductFromCartById (USE THE "BODY" section!!!)

    @PostMapping("/clearCart")
    public void clearCart(@RequestParam("customerId") Integer customerId) {
        customerService.clearCartById(customerId);
    } // (POST) http://localhost:8080/clearCart?customerId=1

}

