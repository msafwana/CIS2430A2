package eStoreSearch;

import java.util.*;
import java.util.ArrayList;

/**
 * Book class where getters and setters are defined for following parameters
 * 
 * @param price       indicates the prices of the book
 * @param year        indicates the year the book was published
 * @param description indicates the description of the book
 * @param publisher   indicates the publisher of the book
 * @param authors     indicates the author of the book
 * @param productID   indicates the unique productID of the book
 */

public class Book {
    private String price;
    private String year;
    private String description;
    private String publisher;
    private String authors;
    private String productID;

    public Book(String price, String year, String description, String publisher, String authors, String productID) {
        this.price = price;
        this.year = year;
        this.description = description;
        this.publisher = publisher;
        this.authors = authors;
        this.productID = productID;
    }

    public String getAuthor() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
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

    public void setAuthor(String newAuthor) {
        this.authors = newAuthor;
    }

    public void setPublisher(String newPublisher) {
        this.publisher = newPublisher;
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

    @Override
    public String toString() {
        return "The Book: \n" + "ProductID: " + productID + "\n" + "Description: " + description + "\n" + "Price: "
                + "$" + price + "\n" + "Year: " + year + "\n" + "Authors: " + authors + "\n" + "Publisher: " + publisher
                + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        if (authors == null) {
            if (other.authors != null)
                return false;
        } else if (!authors.equals(other.authors))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        if (productID == null) {
            if (other.productID != null)
                return false;
        } else if (!productID.equals(other.productID))
            return false;
        if (publisher == null) {
            if (other.publisher != null)
                return false;
        } else if (!publisher.equals(other.publisher))
            return false;
        if (year == null) {
            if (other.year != null)
                return false;
        } else if (!year.equals(other.year))
            return false;
        return true;
    }

}