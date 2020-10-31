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

public class Electronics extends Product {
    private String maker;

    public Electronics() {

    }

    public Electronics(String maker) {
        this.maker = maker;
    }
    public Electronics(String price, String year, String description, String maker, String productID) {
        super(price, year, description, productID);
        this.maker = maker;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    @Override
    public String toString() {
        return "type = \"electronics\"" + super.toString() + "maker = " + "\"" + maker + "\"" + "\n";
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
      
        if (maker == null) {
            if (other.maker != null)
                return false;
        } else if (!maker.equals(other.maker))
            return false;
        return true;
    }

}
