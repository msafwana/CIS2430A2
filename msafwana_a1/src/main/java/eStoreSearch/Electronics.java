package eStoreSearch;

import java.util.ArrayList;

/**
 * Electronics class where accessors and muttators are defined for following
 * parameters
 * 
 * @param price     indicates the prices of the electronic item
 * @param year      indicates the year the electronic item was made
 * @param maker     indicates the maker of the electronic item
 * @param productID indicates the unique productID of the electronic item
 */

public class Electronics {
    private String price;
    private String year;
    private String description;
    private String maker;
    private String productID;

    public Electronics(String price, String year, String description, String maker, String productID) {

        this.price = price;
        this.year = year;
        this.description = description;
        this.maker = maker;
        this.productID = productID;
    }

    public String getMaker() {
        return maker;
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

    public void setMaker(String maker) {
        this.maker = maker;
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
        return "The Electronic: \n" + "ProductID: " + productID + "\n" + "Description: " + description + "\n" + "Price: "
                + "$" + price + "\n" + "Year: " + year + "\n" + "Maker: " + maker + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Electronics other = (Electronics) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (maker == null) {
            if (other.maker != null)
                return false;
        } else if (!maker.equals(other.maker))
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
        if (year == null) {
            if (other.year != null)
                return false;
        } else if (!year.equals(other.year))
            return false;
        return true;
    }

}
