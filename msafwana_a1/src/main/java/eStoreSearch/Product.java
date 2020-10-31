package eStoreSearch;

import java.util.*;
import java.util.ArrayList;

/**
 * Product class which is a parent class where getters and setters are defined for following parameters
 * 
 * @param price       indicates the prices of the book
 * @param year        indicates the year the book was published
 * @param description indicates the description of the book
 * @param productID   indicates the unique productID of the book
 */

 public class Product {

    private String price;
    private String year;
    private String description;
    private String productID;

    public Product() {

    }
    public Product(String price, String year, String description, String productID) {

        this.price = price;
        this.year = year;
        this.description = description;
        this.productID = productID;
    }

    public String getDescription() {
        return description;
    }

    public String getProductID() {
        return productID;
    }

    public String getYear() {
        return year;
    }

    public String getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setYear(String year) {
        this.year = year;

    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String toString() {
        return "productID = " + "\"" + productID + "\"" + "\n" + "description = " + "\"" + description + "\"" + "\n" + "price = "
                + "\"" + price + "\"" + "\n" + "year = " + "\"" + year + "\"" + "\n";
    }
}
   
