/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package eStoreSearch;

import org.junit.Test;
import static org.junit.Assert.*;

public class ElectronicsTest {

    String VproductID = "012345";
    String Vprice = "830.99";
    String Vyear = "2020";
    String Vdescription = "S9+";
    String Vmaker = "Samsung";

    Electronics classelectronictest = new Electronics(Vprice, Vyear, Vdescription, Vmaker, VproductID);

  @Test public void MakerEmpty(){

    classelectronictest = new Electronics(Vprice, Vyear, Vdescription, "", VproductID);
  }

  @Test public void testProdcutID(){
    classelectronictest.setProductID(VproductID);
    assertEquals("The productID should be 012345",VproductID, classelectronictest.getProductID());
  }

  @Test public void testdescription(){
    classelectronictest.setDescription(Vdescription);
    assertEquals("The description should be S9+",Vdescription, classelectronictest.getDescription());
  }

  @Test public void testmaker(){
    classelectronictest.setMaker(Vmaker);
    assertEquals("The maker should be Samsung",Vmaker, classelectronictest.getMaker());
  }

  @Test public void testprice(){
    classelectronictest.setPrice(Vprice);
    assertEquals("The price should be 830.99",Vprice, classelectronictest.getPrice());
  }

  @Test public void EqualityCheck(){
    Electronics classelectronictestcompar = new Electronics(Vprice, Vyear, Vdescription, Vmaker, VproductID);
    assertTrue("The Electronics match", classelectronictest.equals(classelectronictestcompar));

    classelectronictestcompar = new Electronics(Vprice, Vyear, "Iphone", "Apple", VproductID);
    assertFalse("Different description and maker", classelectronictest.equals(classelectronictestcompar));
  }
}
