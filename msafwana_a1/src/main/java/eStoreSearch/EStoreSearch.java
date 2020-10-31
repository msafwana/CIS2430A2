package eStoreSearch;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;
import javax.lang.model.util.ElementScanner6;

/**
 * The class EStoreSearch contains all defined methods to add,search print the books list and electronic list
 * @param ArrayList<Product>productsList stores the details of books
 * @param ArrayList<Electronics>electronicsList stores the details of electronics
 */
public class EStoreSearch {
  public static ArrayList<Product> productsList = new ArrayList<>();

  public static boolean isNullOrEmpty(String str) {
    if (str != null && !str.isEmpty()) return false;
    return true;
  }

  public static boolean isProductID(String productid) {
    boolean productIDlength = false;

    if (
      productid.length() > 6 || !productid.matches("[0-9]+")
    ) productIDlength = true;
    return productIDlength;
  }

  public static boolean isYearcheck(String year) {
    boolean yearcheck = false;
    if (!year.matches("[0-9]+")) {
      System.out.println("Invalid input");
      yearcheck = true;
    }
    return yearcheck;
  }

  /**
   * This method adds the book entered by the user into the productsList
   * @param price       indicates the prices of the book
   * @param year        indicates the year the book was published
   * @param description indicates the description of the book
   * @param publisher   indicates the publisher of the book
   * @param authors     indicates the author of the book
   * @param productID   indicates the unique productID of the book
   * @param yearVal     stores the integer value of year
   * @param priceVal    stores the double value of price
   */
  public void addBook() {
    Scanner userinput = new Scanner(System.in);
    String price = "";
    String year = "";
    int yearVal = 0;
    double priceVal = 0.00;
    String description = "";
    String publisher = "";
    String authors = "";
    String productID = "";
    String priceinput;

    System.out.println("Enter the productID of the book: ");
    productID = userinput.nextLine().trim();

    while (isNullOrEmpty(productID)) {
      System.out.println(
        "ProductID cannot be empty. Please enter a valid productID \n"
      );
      productID = userinput.nextLine().trim();
    }

    if (isProductID(productID)) {
      System.out.println(
        "The ProductID should be 6 digits only. Please enter a valid productID \n"
      );
      productID = userinput.nextLine().trim();
    }

    System.out.println("Enter the description of the book: ");
    description = userinput.nextLine().trim();

    while (isNullOrEmpty(description)) {
      System.out.println(
        "Description cannot be empty. Please enter a valid description \n"
      );
      description = userinput.nextLine().trim();
    }

    System.out.println("Enter the author(s) of the book: ");
    authors = userinput.nextLine().trim();

    System.out.println("Enter the publisher of the book: ");
    publisher = userinput.nextLine().trim();

    System.out.println("Enter the price of the book: ");
    priceinput = userinput.nextLine().trim();

    if (priceinput.isEmpty()) {
      price = "0.00";
    } else {
      price = priceinput;
    }

    priceVal = Double.valueOf(price);

    do {
      System.out.println(
        "Enter the year (between 1000 and 9999) the book was published: "
      );
      year = userinput.nextLine().trim();

      if (isYearcheck(year)) {
        System.out.println(
          "Enter the year (between 1000 and 9999) the book was published: "
        );
        year = userinput.nextLine().trim();
      }

      while (isNullOrEmpty(year)) {
        System.out.println(
          "Please enter the year (between 1000 and 9999) the book was published: "
        );
        year = userinput.nextLine().trim();
      }

      try {
        yearVal = Integer.valueOf(year);
      } catch (Exception e) {
        System.out.println("Invalid Input");
        System.out.println(
          "Enter the year (between 1000 and 9999) the book was published: "
        );
        year = userinput.nextLine().trim();
      }
    } while (yearVal < 1000 || yearVal > 9999);

    boolean isProductIDFoundcheck = false;

    //checks if the product ID is taken by electronic item when no item in book list
    for (int i = 0; i < productsList.size(); i++) {
      if (productID.equals(productsList.get(i).getProductID())) {
        System.out.println(
          "The product ID is taken. Please enter another product ID"
        );
        isProductIDFoundcheck = true;
      }
    }

    //if productID is unique it adds the book to the list
    if (!isProductIDFoundcheck) {
      productsList.add(
        new Book(price, year, description, publisher, authors, productID)
      );
    }

    //prints the booklist afte the item has been added
    printproductsList(productsList);
  }

  /**
   * This method searches the book entered by the user from the productsList
   * @param year        indicates the year the book was published
   * @param description indicates the description of the book
   * @param productID   indicates the unique productID of the book
   * @param keyWord     splits the given description from the user by whitespaces
   */

  public void searchBook() {
    Scanner userinput = new Scanner(System.in);
    String ProductID = "";
    String description = "";
    String year = "";
    boolean yearmatch = false;

    System.out.println("Enter the ProductID to search:");
    ProductID = userinput.nextLine().trim();

    if (ProductID.isEmpty()) {
      System.out.println(
        "The Product ID is wrong. You can't retrieve the objects information."
      );
    }

    System.out.println("Enter the description keyword:");
    description = userinput.nextLine().trim();

    if (description.isEmpty()) {
      System.out.println(
        "The keyword is wrong. You can't retrieve the objects information."
      );
    }

    System.out.println(
      "Enter the year (between 1000 and 9999) or range of year the book to search: "
    );
    year = userinput.nextLine().trim();

    if (year.isEmpty()) {
      System.out.println(
        "The year or range of year is wrong. You can't retrieve the objects information."
      );
    }

    // searches for a book when year and productID is given
    if (
      ProductID.length() != 0 && description.length() == 0 && year.length() != 0
    ) {
      yearandIDSearch(ProductID, year, productsList);
      // searches for a book when year and description is given
    } else if (
      ProductID.length() != 0 && description.length() != 0 && year.length() == 0
    ) {
      boolean idkeymatch = false;
      String[] keyWord = null;

      if (!description.isEmpty()) keyWord = description.split("[ ,\n]+");

      for (int m = 0; m < productsList.size(); m++) {
        if (
          ProductID.equals(productsList.get(m).getProductID()) &&
          matchKey(keyWord, productsList.get(m).getDescription())
        ) {
          System.out.println(productsList.get(m).toString());
          idkeymatch = true;
        }
      }
      if (!idkeymatch) {
        System.out.println(
          "No match found for given ProductID and description"
        );
      }
    }
    // searches for a book when productID is given
    else if (
      ProductID.length() != 0 && description.length() == 0 && year.length() == 0
    ) {
      boolean idmatch = false;
      for (int j = 0; j < productsList.size(); j++) {
        if (ProductID.equals(productsList.get(j).getProductID())) {
          System.out.println(productsList.get(j).toString());
          idmatch = true;
        }
      }
      if (!idmatch) {
        System.out.println("No match found for given ProductID");
      }
    }
    // searches for a book when year, productID and description is given
    else if (
      ProductID.length() != 0 && description.length() != 0 && year.length() != 0
    ) {
      YearKeywordIDmatch(description, year, ProductID);
    }
    // searches for a book when description and year is given
    else if (
      ProductID.length() == 0 && description.length() != 0 && year.length() != 0
    ) {
      matchKeywordYear(description, year);
    }
    // searches for a book when description is given
    else if (
      ProductID.length() == 0 && description.length() != 0 && year.length() == 0
    ) {
      boolean wordmatch = false;
      for (int a = 0; a < productsList.size(); a++) {
        String[] word = productsList.get(a).getDescription().split(" ");

        for (int j = 0; j < word.length; j++) {
          if (description.equalsIgnoreCase(word[j])) {
            System.out.println(productsList.get(a).toString());
            wordmatch = true;
          }
        }
      }

      if (!wordmatch) {
        System.out.println("No match found for description");
      }
    }
    // searches for a book when year is given
    else if (
      ProductID.length() == 0 && description.length() == 0 && year.length() != 0
    ) {
      for (int k = 0; k < productsList.size(); k++) {
        if (year.equals(productsList.get(k).getYear())) {
          System.out.println(productsList.get(k).toString());
        } else if (year.contains("-")) {
          //if year contains a hyphen(-) it will store rightside of '-' as first year and leftside of '-' as second yeay
          String[] token = year.split("-", -1);
          String firstYear = token[0];
          String secondYear = token[1];

          if (year.charAt(0) == '-') {
            //iterates through the productsList to find a product with years less than the given year by user
            if (
              Integer.valueOf(productsList.get(k).getYear()) <=
              Integer.parseInt(secondYear) &&
              firstYear.length() == 0
            ) {
              System.out.println(productsList.get(k).toString());
              yearmatch = true;
            }
          }
          //iterates through the productsList to find a product with years greater than the given year by user
          else if (
            Integer.valueOf(productsList.get(k).getYear()) >=
            Integer.parseInt(firstYear) &&
            secondYear.length() == 0
          ) {
            System.out.println(productsList.get(k).toString());
            yearmatch = true;
          }
          //iterates through the productsList to find a product with years greater than the first year given by user
          //and less than second year given by user
          else if (
            Integer.valueOf(productsList.get(k).getYear()) >=
            Integer.parseInt(firstYear) &&
            Integer.valueOf(productsList.get(k).getYear()) <=
            Integer.parseInt(secondYear)
          ) {
            System.out.println(productsList.get(k).toString());
            yearmatch = true;
          } else {
            //System.out.println("Not a valid year or range of years for the product \n");
          }
        } else {
          System.out.println(
            "Not a valid year or range of years for the product \n"
          );
        }
      }

      if (!yearmatch) {
        System.out.println("No match found for given year range \n");
      }
    }
    // displays all the books present in the list
    else if (
      ProductID.length() == 0 && year.length() == 0 && description.length() == 0
    ) {
      printproductsList(productsList);
    }
  }

  //function checks if given keyword is present in the array of stored description words
  public boolean matchKeyToken(String keyWords, String[] desToken) {
    for (int i = 0; i < desToken.length; i++) if (
      keyWords.equalsIgnoreCase(desToken[i])
    ) return true;
    return false;
  }

  //function takes in description and stores words seperated by whitespaces in array
  public boolean matchKey(String keyWord[], String description) {
    String[] tokenization = description.split("[ ,\n]+");
    for (int i = 0; i < keyWord.length; i++) if (
      !matchKeyToken(keyWord[i], tokenization)
    ) return false;
    return true;
  }

  // looks for a match when year and description are given
  public void YearKeywordIDmatch(
    String description,
    String year,
    String ProductID
  ) {
    boolean YearKeyId = false;
    String[] keyWord = null;

    //if desciprtion is not empty it splits the string by whitespaces and stores in a string array
    if (!description.isEmpty()) keyWord = description.split("[ ,\n]+");

    for (int i = 0; i < productsList.size(); i++) {
      if (
        ProductID.equals(productsList.get(i).getProductID()) &&
        year.equals(productsList.get(i).getYear()) &&
        matchKey(keyWord, productsList.get(i).getDescription())
      ) {
        System.out.println(productsList.get(i).toString());
        YearKeyId = true;
      } else if (year.contains("-")) {
        //if year contains a hyphen(-) it will store rightside of '-' as first year and leftside of '-' as second yeay
        String[] token = year.split("-", -1);
        String firstYear = token[0];
        String secondYear = token[1];

        if (year.charAt(0) == '-') {
          //iterates through the productsList to find a product with years less than the given year by user
          //while matching keyword and product ID
          if (
            Integer.valueOf(productsList.get(i).getYear()) <=
            Integer.parseInt(secondYear) &&
            firstYear.length() == 0 &&
            Integer.parseInt(ProductID) ==
            Integer.valueOf(productsList.get(i).getProductID()) &&
            matchKey(keyWord, productsList.get(i).getDescription())
          ) {
            System.out.println(productsList.get(i).toString());
            YearKeyId = true;
          }
        }
        //iterates through the productsList to find a product with years greater than the given year by user
        //while matching keyword and product ID
        else if (
          (
            Integer.valueOf(productsList.get(i).getYear()) >=
            Integer.parseInt(firstYear) &&
            secondYear.length() == 0
          ) &&
          Integer.parseInt(ProductID) ==
          Integer.valueOf(productsList.get(i).getProductID()) &&
          matchKey(keyWord, productsList.get(i).getDescription())
        ) {
          System.out.println(productsList.get(i).toString());
          YearKeyId = true;
        }
        //iterates through the productsList to find a product with years less than the given frist year and greater second year by user
        //while matching keyword and product ID
        else if (
          (
            Integer.valueOf(productsList.get(i).getYear()) >=
            Integer.parseInt(firstYear) &&
            Integer.valueOf(productsList.get(i).getYear()) <=
            Integer.parseInt(secondYear)
          ) &&
          Integer.parseInt(ProductID) ==
          Integer.valueOf(productsList.get(i).getProductID()) &&
          matchKey(keyWord, productsList.get(i).getDescription())
        ) {
          System.out.println(productsList.get(i).toString());
          YearKeyId = true;
        } else {}
      }
    }

    if (!YearKeyId) {
      System.out.println("No match found \n");
    }
  }

  // looks for a match when year and description are given
  public void matchKeywordYear(String description, String year) {
    boolean KeyYear = false;
    String[] keyWord = null;

    //if desciprtion is not empty it splits the string by whitespaces and stores in a string array
    if (!description.isEmpty()) keyWord = description.split("[ ,\n]+");

    for (int k = 0; k < productsList.size(); k++) {
      if (
        year.equals(productsList.get(k).getYear()) &&
        matchKey(keyWord, productsList.get(k).getDescription())
      ) {
        System.out.println(productsList.get(k).toString());
        KeyYear = true;
      } else if (year.contains("-")) {
        //if year contains a hyphen(-) it will store rightside of '-' as first year and leftside of '-' as second yeay
        String[] token = year.split("-", -1);
        String firstYear = token[0];
        String secondYear = token[1];

        if (year.charAt(0) == '-') {
          //iterates through the productsList to find a product with years less than the given year by user
          //while matching keyword
          if (
            Integer.valueOf(productsList.get(k).getYear()) <=
            Integer.parseInt(secondYear) &&
            firstYear.length() == 0 &&
            matchKey(keyWord, productsList.get(k).getDescription())
          ) {
            System.out.println(productsList.get(k).toString());
            KeyYear = true;
          }
        }
        //iterates through the productsList to find a product with years greater than the given year by user
        //while matching keyword
        else if (
          Integer.valueOf(productsList.get(k).getYear()) >=
          Integer.parseInt(firstYear) &&
          secondYear.length() == 0 &&
          matchKey(keyWord, productsList.get(k).getDescription())
        ) {
          System.out.println(productsList.get(k).toString());
          KeyYear = true;
        }
        //iterates through the productsList to find a product with years less than the first year and greater than second year by user
        //while matching keyword
        else if (
          Integer.valueOf(productsList.get(k).getYear()) >=
          Integer.parseInt(firstYear) &&
          Integer.valueOf(productsList.get(k).getYear()) <=
          Integer.parseInt(secondYear) &&
          matchKey(keyWord, productsList.get(k).getDescription())
        ) {
          System.out.println(productsList.get(k).toString());
          KeyYear = true;
        } else {}
      }
    }
    if (!KeyYear) {
      System.out.print("No match found \n");
    }
  }

  // looks for a match when year and productID are given
  public void yearandIDSearch(
    String ProductID,
    String year,
    ArrayList<Product> productsList
  ) {
    boolean checkyearIDs = false;

    for (int i = 0; i < productsList.size(); i++) {
      if (
        ProductID.equals(productsList.get(i).getProductID()) &&
        year.equals(productsList.get(i).getYear())
      ) {
        System.out.println(productsList.get(i).toString());
        checkyearIDs = true;
      } else if (year.contains("-")) {
        //if year contains a hyphen(-) it will store rightside of '-' as first year and leftside of '-' as second yeay
        String[] token = year.split("-", -1);
        String firstYear = token[0];
        String secondYear = token[1];

        if (year.charAt(0) == '-') {
          //iterates through the productsList to find a product with years less than the given year by user
          //while matching product ID
          if (
            Integer.valueOf(productsList.get(i).getYear()) <=
            Integer.parseInt(secondYear) &&
            firstYear.length() == 0 &&
            Integer.parseInt(ProductID) ==
            Integer.valueOf(productsList.get(i).getProductID())
          ) {
            System.out.println(productsList.get(i).toString());
            checkyearIDs = true;
          }
        }
        //iterates through the productsList to find a product with years less than the given year by user
        //while matching product ID
        else if (
          (
            Integer.valueOf(productsList.get(i).getYear()) >=
            Integer.parseInt(firstYear) &&
            secondYear.length() == 0
          ) &&
          Integer.parseInt(ProductID) ==
          Integer.valueOf(productsList.get(i).getProductID())
        ) {
          System.out.println(productsList.get(i).toString());
          checkyearIDs = true;
        }
        //iterates through the productsList to find a product with years less than the given year by user
        //while matching product ID
        else if (
          (
            Integer.valueOf(productsList.get(i).getYear()) >=
            Integer.parseInt(firstYear) &&
            Integer.valueOf(productsList.get(i).getYear()) <=
            Integer.parseInt(secondYear)
          ) &&
          Integer.parseInt(ProductID) ==
          Integer.valueOf(productsList.get(i).getProductID())
        ) {
          System.out.println(productsList.get(i).toString());
          checkyearIDs = true;
        } else {}
      }
    }

    if (!checkyearIDs) {
      System.out.println("\n This Product does not contain in the given year");
    }
  }

  // prints all the books in the list
  public void printproductsList(ArrayList<Product> productsList) {
    if (productsList.isEmpty()) System.out.println(
      "There are no books in the list"
    ); else {
      for (int i = 0; i < productsList.size(); i++) {
        System.out.println("Total Producats" + (i + 1));
        System.out.println(productsList.get(i).toString());
      }
    }
  }

  public void saveToFile(String filename, ArrayList<Product> productsList) { // this function prints and saves the list's information to a text file before the program quits
    Scanner user = new Scanner(System.in);
    boolean fileExists;
    try {
      fileExists = new File(filename).isFile();

      if (fileExists) {
        System.out.println("File already exists. Press y to overwrite the exisisting file or n to discard \n");
        String yorn = user.nextLine().trim();

        if (yorn.equalsIgnoreCase("yes") || yorn.equalsIgnoreCase("y")) {
          PrintWriter writer = new PrintWriter(filename, "UTF-8");

          for (Product contents : productsList) {
            writer.println(contents.toString());
          }
          writer.close();
        } else if (yorn.equalsIgnoreCase("no") || yorn.equalsIgnoreCase("n")) {
          System.out.println("Not writing to file - file exists \n");
        }
      } else {
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        for (Product contents : productsList) {
          writer.println(contents.toString());
        }
        writer.close();
      }
    } catch (Exception e) {
      System.out.println("ERROR: Could not write to file \n");
    }
  }

  public void fileToList(String filename, ArrayList<Product> productsList) {
      
  }
  public static void main(String[] args) {
    // Initializations
    EStoreSearch product = new EStoreSearch();
    Scanner input = new Scanner(System.in);
    Scanner input2 = new Scanner(System.in);
    Scanner input3 = new Scanner(System.in);

    String one = "(1) Enter 1 to add a product. \n";
    String two = "(2) Enter 2 search a product. \n";
    String three = "(3) Enter 3 to quit. \n";
    String four = "(4) Enter 4 to print all products to a file \n";

    int inputvalue = 0;
    while (inputvalue != 4) {
      System.out.println(one + two + three + four);
      inputvalue = input.nextInt();

      switch (inputvalue) {
        case 1:
          System.out.println(
            "Choose which product you would like to add (book or electronic?): "
          );
          String check = input2.nextLine().trim();

          if (
            check.toLowerCase().equals("book") ||
            check.toLowerCase().equals("b")
          ) product.addBook(); else if (
            check.toLowerCase().equals("electronic") ||
            check.toLowerCase().equals("e")
          ) System.out.println("shouldn't be here \n"); else System.out.println( //  product.addElectronics();
            "Book or electronic wasn't properly specified"
          );
          break;
        case 2:
          System.out.println(
            "Choose which product you would like to search (book or electronic?): "
          );
          String check2 = input2.nextLine().trim();

          if (
            check2.toLowerCase().equals("book") ||
            check2.toLowerCase().equals("b")
          ) try {
            product.searchBook();
          } catch (Exception e) {} else if (
            check2.toLowerCase().equals("electronic") ||
            check2.toLowerCase().equals("e")
          ) try {
            System.out.println("shouldn't be here \n");
            //    product.searchElectronics();
          } catch (Exception e) {} else System.out.println(
            "Book or electronic wasn't properly specified"
          );
          break;

          case 3:
          System.out.println("Goodbye :)");
          break;

          case 4: 
          System.out.println("Input file \n");
          String fileinput = input3.nextLine().trim();
          product.saveToFile(fileinput, productsList);
          break;
      }
    }
  }
}
