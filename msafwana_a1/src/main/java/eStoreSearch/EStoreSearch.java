package eStoreSearch;

import java.util.ArrayList;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * The class EStoreSearch contains all defined methods to add,search print the books list and electronic list
 * @param ArrayList<Book>booksList stores the details of books
 * @param ArrayList<Electronics>electronicsList stores the details of electronics
 */
public class EStoreSearch {
    public ArrayList<Book> booksList = new ArrayList<>();
    public ArrayList<Electronics> electronicsList = new ArrayList<>();

    public static boolean isNullOrEmpty(String str) {
        if (str != null && !str.isEmpty())
            return false;
        return true;
    }

    /**
     * This method adds the book entered by the user into the booksList
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

        boolean productIDlength = false;
        while (isNullOrEmpty(productID)) {
            System.out.println("ProductID cannot be empty. Please enter a valid productID \n");
            productID = userinput.nextLine().trim();
        }

        if(productID.length() > 6 || !productID.matches("[0-9]+")){
            productIDlength = true;
        }

        if(productIDlength){
        System.out.println("The ProductID should be 6 digits only. Please enter a valid productID \n");
        productID = userinput.nextLine().trim();
        }

        System.out.println("Enter the description of the book: ");
        description = userinput.nextLine().trim();
        while (isNullOrEmpty(description)) {
            System.out.println("Description cannot be empty. Please enter a valid description \n");
            description = userinput.nextLine().trim();

        }

        System.out.println("Enter the author(s) of the book: ");
        authors = userinput.nextLine().trim();

        System.out.println("Enter the publisher of the book: ");
        publisher = userinput.nextLine().trim();

        System.out.println("Enter the price of the book: ");
        priceinput = userinput.nextLine().trim();
    
        if(priceinput.isEmpty()) {
            price = "0.00";
        }

        else{
            price = priceinput;
        }

        priceVal = Double.valueOf(price);

        do {
            System.out.println("Enter the year (between 1000 and 9999) the book was published: ");
            year = userinput.nextLine().trim();

            boolean yearcheck = false;
            if(!year.matches("[0-9]+")){
                System.out.println("Invalid input");
                yearcheck = true;
            }

            if(yearcheck){
                System.out.println("Enter the year (between 1000 and 9999) the book was published: ");
            year = userinput.nextLine().trim();
            }

            while(isNullOrEmpty(year)){
                System.out.println("Please enter the year (between 1000 and 9999) the book was published: ");
                year = userinput.nextLine().trim();
            }

            try{
            yearVal = Integer.valueOf(year);
            }catch (Exception e){
                System.out.println("Invalid Input");
                System.out.println("Enter the year (between 1000 and 9999) the book was published: ");
            year = userinput.nextLine().trim();
            }
        } while (yearVal < 1000 || yearVal > 9999);

        boolean isProductIDFoundcheck = false;

        //checks if the product ID is taken by electronic item when no item in book list
        if (booksList.size() == 0) {
            for (int i = 0; i < electronicsList.size(); i++) {
                if (productID.equals(electronicsList.get(i).getProductID())) {
                    System.out.println("The product ID is taken. Please enter another product ID");
                    isProductIDFoundcheck = true;

                }
            }

            //if productID is unique it adds the book to the list
            if (!isProductIDFoundcheck) {
                booksList.add(new Book(price, year, description, publisher, authors, productID));
            }
        }

        //checks if the product ID is taken by electronic item when an item is in book list
        else {
            for (int k = 0; k < electronicsList.size(); k++) {
                if (productID.equals(electronicsList.get(k).getProductID())) {
                    System.out.println("The product ID is taken. Please enter another product ID");
                    isProductIDFoundcheck = true;

                }
            }

            //if productID is not found in electronic list  check its uniqueness in book list
            if (!isProductIDFoundcheck) {
                for (int i = 0; i < booksList.size(); i++) {
                    if (productID.equals(booksList.get(i).getProductID())) {
                        System.out.println("The product ID is taken. Please enter another product ID");
                        isProductIDFoundcheck = true;
                    }
                }
                
                //if productID is unique it adds the book to the list
                if (!isProductIDFoundcheck) {
                    booksList.add(new Book(price, year, description, publisher, authors, productID));
                }
            }

        }
        //prints the booklist afte the item has been added
        printBooksList(booksList);
    }

    /**
     * This method searches the book entered by the user from the booksList
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
            System.out.println("The Product ID is wrong. You can't retrieve the objects information.");

        }

        System.out.println("Enter the description keyword:");
        description = userinput.nextLine().trim();

        if (description.isEmpty()) {
            System.out.println("The keyword is wrong. You can't retrieve the objects information.");

        }

        System.out.println("Enter the year (between 1000 and 9999) or range of year the book to search: ");
        year = userinput.nextLine().trim();

        if (year.isEmpty()) {
            System.out.println("The year or range of year is wrong. You can't retrieve the objects information.");

        }

        // searches for a book when year and productID is given
        if (ProductID.length() != 0 && description.length() == 0 && year.length() != 0) {
            yearandIDSearch(ProductID, year, booksList);

        // searches for a book when year and description is given
        } else if (ProductID.length() != 0 && description.length() != 0 && year.length() == 0) {
         
            boolean idkeymatch = false;
            String[] keyWord = null;

            if (!description.isEmpty())
                keyWord = description.split("[ ,\n]+");

            for (int m = 0; m < booksList.size(); m++) {
                if (ProductID.equals(booksList.get(m).getProductID())
                        && matchKey(keyWord, booksList.get(m).getDescription())) {
                    System.out.println(booksList.get(m).toString());
                    idkeymatch = true;
                }
            }
            if (!idkeymatch) {
                System.out.println("No match found for given ProductID and description");
            }
        }
        // searches for a book when productID is given
        else if (ProductID.length() != 0 && description.length() == 0 && year.length() == 0) {
            boolean idmatch = false;
            for (int j = 0; j < booksList.size(); j++) {
                if (ProductID.equals(booksList.get(j).getProductID())) {
                    System.out.println(booksList.get(j).toString());
                    idmatch = true;
                }
            }
            if (!idmatch) {
                System.out.println("No match found for given ProductID");

            }
        } 
        // searches for a book when year, productID and description is given
        else if (ProductID.length() != 0 && description.length() != 0 && year.length() != 0) {
            YearKeywordIDmatch(description, year, ProductID);

        } 
        // searches for a book when description and year is given
        else if (ProductID.length() == 0 && description.length() != 0 && year.length() != 0) {
            matchKeywordYear(description, year);
        }
        // searches for a book when description is given
        else if (ProductID.length() == 0 && description.length() != 0 && year.length() == 0) {
            boolean wordmatch = false;
            for (int a = 0; a < booksList.size(); a++) {

                String[] word = booksList.get(a).getDescription().split(" ");

                for (int j = 0; j < word.length; j++) {

                    if (description.equalsIgnoreCase(word[j])) {
                        System.out.println(booksList.get(a).toString());
                        wordmatch = true;
                    }
                }
            }

            if (!wordmatch) {
                System.out.println("No match found for description");

            }
        } 
        // searches for a book when year is given
        else if (ProductID.length() == 0 && description.length() == 0 && year.length() != 0) {
            for (int k = 0; k < booksList.size(); k++) {
                if (year.equals(booksList.get(k).getYear())) {
                    System.out.println(booksList.get(k).toString());
                } else if (year.contains("-"))

                {
                    //if year contains a hyphen(-) it will store rightside of '-' as first year and leftside of '-' as second yeay
                    String[] token = year.split("-", -1);
                    String firstYear = token[0];
                    String secondYear = token[1];

                    if (year.charAt(0) == '-') {

                        //iterates through the bookslist to find a product with years less than the given year by user
                        if (Integer.valueOf(booksList.get(k).getYear()) <= Integer.parseInt(secondYear)
                                && firstYear.length() == 0) {
                            System.out.println(booksList.get(k).toString());
                            yearmatch = true;
                        }
                    }
                    //iterates through the bookslist to find a product with years greater than the given year by user
                    else if (Integer.valueOf(booksList.get(k).getYear()) >= Integer.parseInt(firstYear)
                            && secondYear.length() == 0) {

                        System.out.println(booksList.get(k).toString());
                        yearmatch = true;
                    }
                    //iterates through the bookslist to find a product with years greater than the first year given by user 
                    //and less than second year given by user
                    else if (Integer.valueOf(booksList.get(k).getYear()) >= Integer.parseInt(firstYear)
                            && Integer.valueOf(booksList.get(k).getYear()) <= Integer.parseInt(secondYear)) {
                        System.out.println(booksList.get(k).toString());
                        yearmatch = true;
                    } else {
                        //System.out.println("Not a valid year or range of years for the product \n");
                    }
                }

                else {
                    System.out.println("Not a valid year or range of years for the product \n");

                }
            }

        if(!yearmatch){
            System.out.println("No match found for given year range \n");
        }
        } 
        // displays all the books present in the list
        else if (ProductID.length() == 0 && year.length() == 0 && description.length() == 0) {

            printBooksList(booksList);
        }
    }

    //function checks if given keyword is present in the array of stored description words
    public boolean matchKeyToken(String keyWords, String[] desToken) {
        for (int i = 0; i < desToken.length; i++)
            if (keyWords.equalsIgnoreCase(desToken[i]))
                return true;
        return false;
    }

    //function takes in description and stores words seperated by whitespaces in array
    public boolean matchKey(String keyWord[], String description) {
        String[] tokenization = description.split("[ ,\n]+");
        for (int i = 0; i < keyWord.length; i++)
            if (!matchKeyToken(keyWord[i], tokenization))
                return false;
        return true;
    }

    // looks for a match when year and description are given 
    public void YearKeywordIDmatch(String description, String year, String ProductID) {

        boolean YearKeyId = false;
        String[] keyWord = null;

        //if desciprtion is not empty it splits the string by whitespaces and stores in a string array
        if (!description.isEmpty())
            keyWord = description.split("[ ,\n]+");

        for (int i = 0; i < booksList.size(); i++) {
            if (ProductID.equals(booksList.get(i).getProductID()) && year.equals(booksList.get(i).getYear())
                    && matchKey(keyWord, booksList.get(i).getDescription())) {

                System.out.println(booksList.get(i).toString());
                YearKeyId = true;
            } else if (year.contains("-")) {

                //if year contains a hyphen(-) it will store rightside of '-' as first year and leftside of '-' as second yeay
                String[] token = year.split("-", -1);
                String firstYear = token[0];
                String secondYear = token[1];

                if (year.charAt(0) == '-') {
                    //iterates through the bookslist to find a product with years less than the given year by user 
                    //while matching keyword and product ID
                    if (Integer.valueOf(booksList.get(i).getYear()) <= Integer.parseInt(secondYear)
                            && firstYear.length() == 0
                            && Integer.parseInt(ProductID) == Integer.valueOf(booksList.get(i).getProductID())
                            && matchKey(keyWord, booksList.get(i).getDescription())) {
                        System.out.println(booksList.get(i).toString());
                        YearKeyId = true;
                    }
                } 
                //iterates through the bookslist to find a product with years greater than the given year by user 
                //while matching keyword and product ID
                else if ((Integer.valueOf(booksList.get(i).getYear()) >= Integer.parseInt(firstYear)
                        && secondYear.length() == 0)
                        && Integer.parseInt(ProductID) == Integer.valueOf(booksList.get(i).getProductID())
                        && matchKey(keyWord, booksList.get(i).getDescription())) {
                    System.out.println(booksList.get(i).toString());
                    YearKeyId = true;

                } 
                //iterates through the bookslist to find a product with years less than the given frist year and greater second year by user
                //while matching keyword and product ID
                else if ((Integer.valueOf(booksList.get(i).getYear()) >= Integer.parseInt(firstYear)
                        && Integer.valueOf(booksList.get(i).getYear()) <= Integer.parseInt(secondYear))
                        && Integer.parseInt(ProductID) == Integer.valueOf(booksList.get(i).getProductID())
                        && matchKey(keyWord, booksList.get(i).getDescription())) {
                    System.out.println(booksList.get(i).toString());
                    YearKeyId = true;

                } else {

                }
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
        if (!description.isEmpty())
            keyWord = description.split("[ ,\n]+");

        for (int k = 0; k < booksList.size(); k++) {
            if (year.equals(booksList.get(k).getYear()) && matchKey(keyWord, booksList.get(k).getDescription())) {
                System.out.println(booksList.get(k).toString());
                KeyYear = true;
            } else if (year.contains("-"))

            {
                //if year contains a hyphen(-) it will store rightside of '-' as first year and leftside of '-' as second yeay
                String[] token = year.split("-", -1);
                String firstYear = token[0];
                String secondYear = token[1];

                if (year.charAt(0) == '-') {
                    //iterates through the bookslist to find a product with years less than the given year by user 
                    //while matching keyword
                    if (Integer.valueOf(booksList.get(k).getYear()) <= Integer.parseInt(secondYear)
                            && firstYear.length() == 0 && matchKey(keyWord, booksList.get(k).getDescription())) {
                        System.out.println(booksList.get(k).toString());
                        KeyYear = true;

                    }
                } 
                //iterates through the bookslist to find a product with years greater than the given year by user 
                //while matching keyword
                else if (Integer.valueOf(booksList.get(k).getYear()) >= Integer.parseInt(firstYear)
                        && secondYear.length() == 0 && matchKey(keyWord, booksList.get(k).getDescription())) {
                    System.out.println(booksList.get(k).toString());
                    KeyYear = true;

                } 
                //iterates through the bookslist to find a product with years less than the first year and greater than second year by user 
                //while matching keyword 
                else if (Integer.valueOf(booksList.get(k).getYear()) >= Integer.parseInt(firstYear)
                        && Integer.valueOf(booksList.get(k).getYear()) <= Integer.parseInt(secondYear)
                        && matchKey(keyWord, booksList.get(k).getDescription())) {
                    System.out.println(booksList.get(k).toString());
                    KeyYear = true;
                } else {

                }
            }
        }
        if (!KeyYear) {
            System.out.print("No match found \n");

        }
    }

    // looks for a match when year and productID are given 
    public void yearandIDSearch(String ProductID, String year, ArrayList<Book> booksList) {

        boolean checkyearIDs = false;

        for (int i = 0; i < booksList.size(); i++) {
            if (ProductID.equals(booksList.get(i).getProductID()) && year.equals(booksList.get(i).getYear())) {
                System.out.println(booksList.get(i).toString());
                checkyearIDs = true;
            } else if (year.contains("-")) {

                //if year contains a hyphen(-) it will store rightside of '-' as first year and leftside of '-' as second yeay
                String[] token = year.split("-", -1);
                String firstYear = token[0];
                String secondYear = token[1];

                if (year.charAt(0) == '-') {
                    //iterates through the bookslist to find a product with years less than the given year by user 
                    //while matching product ID
                    if (Integer.valueOf(booksList.get(i).getYear()) <= Integer.parseInt(secondYear)
                            && firstYear.length() == 0
                            && Integer.parseInt(ProductID) == Integer.valueOf(booksList.get(i).getProductID())) {
                        System.out.println(booksList.get(i).toString());
                        checkyearIDs = true;

                    }
                }
                //iterates through the bookslist to find a product with years less than the given year by user 
                //while matching product ID
                else if ((Integer.valueOf(booksList.get(i).getYear()) >= Integer.parseInt(firstYear)
                        && secondYear.length() == 0)
                        && Integer.parseInt(ProductID) == Integer.valueOf(booksList.get(i).getProductID())) {
                    System.out.println(booksList.get(i).toString());
                    checkyearIDs = true;

                } 
                //iterates through the bookslist to find a product with years less than the given year by user 
                //while matching product ID
                else if ((Integer.valueOf(booksList.get(i).getYear()) >= Integer.parseInt(firstYear)
                        && Integer.valueOf(booksList.get(i).getYear()) <= Integer.parseInt(secondYear))
                        && Integer.parseInt(ProductID) == Integer.valueOf(booksList.get(i).getProductID())) {
                    System.out.println(booksList.get(i).toString());
                    checkyearIDs = true;
                } else {

                }
            }
        }

        if (!checkyearIDs) {
            System.out.println("\n This Product does not contain in the given year");
        }
    }

    // prints all the books in the list
    public void printBooksList(ArrayList<Book> booksList) {
        if (booksList.isEmpty())
            System.out.println("There are no books in the list");
      
        else {
            for (int i = 0; i < booksList.size(); i++) {
                System.out.println("Total Books" + (i + 1));
                System.out.println(booksList.get(i).toString());

            }
        }

    }

    /**
     * This method adds the electronic entered by the user into the electronicList
     * @param price       indicates the prices of the electronic
     * @param year        indicates the year the electronic was published
     * @param description indicates the description of the electronic
     * @param maker       indicates the maker of the electronic
     * @param productID   indicates the unique productID of the electronic
     * @param yearVal     stores the integer value of year
     * @param priceVal    stores the double value of price
     */

    public void addElectronics() {

        Scanner userinput = new Scanner(System.in);
        String price = "";
        String priceinput;
        String year = "";
        int yearVal = 0;
        double priceVal;
        String description = "";
        String maker = "";
        String productID = "";

        System.out.println("Enter the productID of the electronic: ");
        productID = userinput.nextLine().trim();

        while (isNullOrEmpty(productID)) {
            System.out.println("ProductID cannot be empty. Please enter a valid productID \n");
            productID = userinput.nextLine().trim();

        }
        System.out.println("Enter the description of the electronic: ");
        description = userinput.nextLine().trim();
        while (isNullOrEmpty(description)) {
            System.out.println("Description cannot be empty. Please enter a valid productID \n");
            description = userinput.nextLine().trim();

        }

        System.out.println("Enter the maker of the electronic: ");
        maker = userinput.nextLine().trim();

        
        System.out.println("Enter the price of the electronic: ");
        priceinput = userinput.nextLine().trim();
        
        if(priceinput.isEmpty()) {
            price = "0.00";
        }

        else
        {
            price = priceinput;
        }

        priceVal = Double.valueOf(price);
  
         do {
            System.out.println("Enter the year (between 1000 and 9999) the book was published: ");
            year = userinput.nextLine().trim();

            while(isNullOrEmpty(year)){
                System.out.println("Please enter the year (between 1000 and 9999) the book was published: ");
                year = userinput.nextLine().trim();
            }
            yearVal = Integer.valueOf(year);
        } while (yearVal < 1000 || yearVal > 9999);


        boolean isProductIDFoundcheck = false;

        //checks if the product ID is taken by electronic item when no item in electronic list
        if (electronicsList.size() == 0) {
            for (int i = 0; i < booksList.size(); i++) {
                if (productID.equals(booksList.get(i).getProductID())) {
                    System.out.println("The product ID is taken. Please enter another product ID");
                    isProductIDFoundcheck = true;

                }
            }

            //add the electronic item to the list if product ID is unique
            if (!isProductIDFoundcheck) {
                electronicsList.add(new Electronics(price, year, description, maker, productID));
            }
        }

        //checks if the product ID is taken by electronic item when item is in electronic list
        else {
            for (int k = 0; k < booksList.size(); k++) {
                if (productID.equals(booksList.get(k).getProductID())) {
                    System.out.println("The product ID is taken. Please enter another product ID");
                    isProductIDFoundcheck = true;

                }
            }

             //if product ID is unique in electrnic list it will check in book list for product ID uniqueness
            if (!isProductIDFoundcheck) {
                for (int i = 0; i < electronicsList.size(); i++) {
                    if (productID.equals(electronicsList.get(i).getProductID())) {
                        System.out.println("The product ID is taken. Please enter another product ID");
                        isProductIDFoundcheck = true;
                    }
                }

                //add the electronic item to the list if product ID is unique
                if (!isProductIDFoundcheck) {
                    electronicsList.add(new Electronics(price, year, description, maker, productID));
                }
            }

        }
        //prints all the electronic item entered in the electronic list
        printElectronicsList(electronicsList);
    }
    /**
     * This method searches the electronic item entered by the user from the electronicsList
     * @param year        indicates the year the book was published
     * @param description indicates the description of the book
     * @param productID   indicates the unique productID of the book
     * @param keyWord     splits the given description from the user by whitespaces
     */
    public void searchElectronics() {
        Scanner userinput = new Scanner(System.in);
        String ProductID = "";
        String description = "";
        String year = "";

        System.out.println("Enter the ProductID to search:");
        ProductID = userinput.nextLine().trim();

        if (ProductID.isEmpty()) {
            System.out.println("The Product ID is wrong. You can't retrieve the objects information.");
        }

        System.out.println("Enter the description keyword:");
        description = userinput.nextLine().trim();

        if (description.isEmpty()) {
            System.out.println("The keyword is wrong. You can't retrieve the objects information.");
        }

        System.out.println("Enter the year (between 1000 and 9999) or range of year the book to search: ");
        year = userinput.nextLine().trim();

        if (year.isEmpty()) {
            System.out.println("The year or range of year is wrong. You can't retrieve the objects information.");
        }

        // searches for a electronic when year and productID is given
        if (ProductID.length() != 0 && description.length() == 0 && year.length() != 0) {
            yearandIDESearch(ProductID, year, electronicsList);

        } 
        // searches for a book when description and productID is given
        else if (ProductID.length() != 0 && description.length() != 0 && year.length() == 0) {
            boolean idkeymatch = false;

            String[] keyWord = null;
            if (!description.isEmpty())
                keyWord = description.split("[ ,\n]+");

            for (int m = 0; m < electronicsList.size(); m++) {
                if (ProductID.equals(electronicsList.get(m).getProductID())
                        && matchKey(keyWord, electronicsList.get(m).getDescription())) {
                    System.out.println(electronicsList.get(m).toString());
                    idkeymatch = true;
                }
            }
            if (!idkeymatch) {
                System.out.println("No match found for given ProductID and description");
            }
        }
        // searches for a book when productID is given
        else if (ProductID.length() != 0 && description.length() == 0 && year.length() == 0) {
            boolean idmatch = false;
            for (int j = 0; j < electronicsList.size(); j++) {
                if (ProductID.equals(electronicsList.get(j).getProductID())) {
                    System.out.println(electronicsList.get(j).toString());
                    idmatch = true;
                }
            }
            if (!idmatch) {
                System.out.println("No match found for given ProductID");

            }
        }
        // searches for a book when year, productID and descrition is given
        else if (ProductID.length() != 0 && description.length() != 0 && year.length() != 0) {
            YearKeywordIDEmatch(description, year, ProductID);

        } 
        // searches for a book when description and year is given
        else if (ProductID.length() == 0 && description.length() != 0 && year.length() != 0) {
            EmatchKeywordYear(description, year);
        } 
        // searches for a book when description is given
        else if (ProductID.length() == 0 && description.length() != 0 && year.length() == 0) {
            boolean wordmatch = false;
            for (int a = 0; a < electronicsList.size(); a++) {

                String[] word = electronicsList.get(a).getDescription().split(" ");

                for (int j = 0; j < word.length; j++) {

                    if (description.equalsIgnoreCase(word[j])) {
                        System.out.println(electronicsList.get(a).toString());
                        wordmatch = true;
                    }
                }
            }

            if (!wordmatch) {
                System.out.println("No match found for description");

            }
        } 
        // searches for a book when year is given
        else if (ProductID.length() == 0 && description.length() == 0 && year.length() != 0) {

            boolean yearonly = false;
            for (int k = 0; k < electronicsList.size(); k++) {
                if (year.equals(electronicsList.get(k).getYear())) {
                    System.out.println(electronicsList.get(k).toString());

                } else if (year.contains("-")) {

                    //if year contains a hyphen(-) it will store rightside of '-' as first year and leftside of '-' as second year
                    String[] token = year.split("-", -1);
                    String firstYear = token[0];
                    String secondYear = token[1];

                    if (year.charAt(0) == '-') {
                       //iterates through the electronicslist to find a product with years less than the given year by user 
                        if (Integer.valueOf(electronicsList.get(k).getYear()) <= Integer.parseInt(secondYear)
                                && firstYear.length() == 0) {
                            System.out.println(electronicsList.get(k).toString());
                            yearonly = true;
                        }
                    } 
                    //iterates through the electronicslist to find a product with years greater than the given year by user 
                    else if (Integer.valueOf(electronicsList.get(k).getYear()) >= Integer.parseInt(firstYear)
                            && secondYear.length() == 0) {

                        System.out.println(electronicsList.get(k).toString());
                        yearonly = true;
                    } 
                    //iterates through the electronicslist to find a product with years less than the first year and greater than second year by user 
                    else if (Integer.valueOf(electronicsList.get(k).getYear()) >= Integer.parseInt(firstYear)
                            && Integer.valueOf(electronicsList.get(k).getYear()) <= Integer.parseInt(secondYear)) {
                        System.out.println(electronicsList.get(k).toString());
                        yearonly = true;
                    } else {

                    }
                }
            }

             if (!yearonly) {
                    System.out.println("No match found for given year range \n");
                }
        } 
        // displays all the electronic items in the list if no input is given 
        else if (ProductID.length() == 0 && year.length() == 0 && description.length() == 0) {

            printElectronicsList(electronicsList);
        }
    }

    // looks for a match when year and prouctID are given 
    public void yearandIDESearch(String ProductID, String year, ArrayList<Electronics> electronicsList) {

        boolean checkyearIDs = false;

        for (int i = 0; i < electronicsList.size(); i++) {
            if (ProductID.equals(electronicsList.get(i).getProductID())
                    && year.equals(electronicsList.get(i).getYear())) {
                System.out.println(electronicsList.get(i).toString());
                checkyearIDs = true;
            } else if (year.contains("-")) {

                //if year contains a hyphen(-) it will store rightside of '-' as first year and leftside of '-' as second yeay
                String[] token = year.split("-", -1);
                String firstYear = token[0];
                String secondYear = token[1];

                if (year.charAt(0) == '-') {
                    //iterates through the electronicslist to find a product with years less than the given year by user 
                    //while matching product ID
                    if (Integer.valueOf(electronicsList.get(i).getYear()) <= Integer.parseInt(secondYear)
                            && firstYear.length() == 0
                            && Integer.parseInt(ProductID) == Integer.valueOf(electronicsList.get(i).getProductID())) {
                        System.out.println(electronicsList.get(i).toString());
                        checkyearIDs = true;

                    }
                } 
                //iterates through the electronicslist to find a product with years greater than the given year by user 
                //while matching product ID
                else if ((Integer.valueOf(electronicsList.get(i).getYear()) >= Integer.parseInt(firstYear)
                        && secondYear.length() == 0)
                        && Integer.parseInt(ProductID) == Integer.valueOf(electronicsList.get(i).getProductID())) {
                    System.out.println(electronicsList.get(i).toString());
                    checkyearIDs = true;

                } 
                //iterates through the electronicslist to find a product with years less than the given first year and second year by user 
                //while matching product ID
                else if ((Integer.valueOf(electronicsList.get(i).getYear()) >= Integer.parseInt(firstYear)
                        && Integer.valueOf(electronicsList.get(i).getYear()) <= Integer.parseInt(secondYear))
                        && Integer.parseInt(ProductID) == Integer.valueOf(electronicsList.get(i).getProductID())) {
                    System.out.println(electronicsList.get(i).toString());
                    checkyearIDs = true;
                } else {

                }
            }
        }

        if (!checkyearIDs) {
            System.out.println("\n This Product does not contain in the given year");
        }
    }

    // looks for a match when year and description are given 
    public void EmatchKeywordYear(String description, String year) {
        boolean KeyYear = false;
        String[] keyWord = null;

        //if desciprtion is not empty it splits the string by whitespaces and stores in a string array
        if (!description.isEmpty())
            keyWord = description.split("[ ,\n]+");

        for (int k = 0; k < electronicsList.size(); k++) {
            if (year.equals(electronicsList.get(k).getYear())
                    && matchKey(keyWord, electronicsList.get(k).getDescription())) {
                System.out.println(electronicsList.get(k).toString());
                KeyYear = true;

            } else if (year.contains("-")) {
                
                //if year contains a hyphen(-) it will store rightside of '-' as first year and leftside of '-' as second yeay
                String[] token = year.split("-", -1);
                String firstYear = token[0];
                String secondYear = token[1];

                if (year.charAt(0) == '-') {
                    //iterates through the electronicslist to find a product with years less than the given year by user 
                    //while matching keyword and year
                    if (Integer.valueOf(electronicsList.get(k).getYear()) <= Integer.parseInt(secondYear)
                            && firstYear.length() == 0 && matchKey(keyWord, electronicsList.get(k).getDescription())) {
                        System.out.println(electronicsList.get(k).toString());
                        KeyYear = true;

                    }
                } 
                //iterates through the electronicslist to find a product with years greater than the given year by user 
                //while matching keyword and year
                else if (Integer.valueOf(electronicsList.get(k).getYear()) >= Integer.parseInt(firstYear)
                        && secondYear.length() == 0 && matchKey(keyWord, electronicsList.get(k).getDescription())) {
                    System.out.println(electronicsList.get(k).toString());
                    KeyYear = true;

                } 
                //iterates through the electronicslist to find a product with years less than the given year by user 
                //while matching keyword and year
                else if (Integer.valueOf(electronicsList.get(k).getYear()) >= Integer.parseInt(firstYear)
                        && Integer.valueOf(electronicsList.get(k).getYear()) <= Integer.parseInt(secondYear)
                        && matchKey(keyWord, electronicsList.get(k).getDescription())) {
                    System.out.println(electronicsList.get(k).toString());
                    KeyYear = true;
                } else {

                }
            }
        }
        if (!KeyYear) {
            System.out.print("No match found \n");

        }
    }

    // looks for a match when year, description and ProductID are given 
    public void YearKeywordIDEmatch(String description, String year, String ProductID) {

        boolean YearKeyId = false;
        String[] keyWord = null;

        //if desciprtion is not empty it splits the string by whitespaces and stores in a string array
        if (!description.isEmpty())
            keyWord = description.split("[ ,\n]+");

        for (int i = 0; i < electronicsList.size(); i++) {
            if (ProductID.equals(electronicsList.get(i).getProductID()) && year.equals(electronicsList.get(i).getYear())
                    && matchKey(keyWord, electronicsList.get(i).getDescription())) {

                System.out.println(electronicsList.get(i).toString());
                YearKeyId = true;
            } else if (year.contains("-")) {

                //if year contains a hyphen(-) it will store rightside of '-' as first year and leftside of '-' as second yeay
                String[] token = year.split("-", -1);
                String firstYear = token[0];
                String secondYear = token[1];

                if (year.charAt(0) == '-') {
                    //iterates through the electronicslist to find a product with years less than the given year by user 
                    //while matching product ID, year and keyword
                    if (Integer.valueOf(electronicsList.get(i).getYear()) <= Integer.parseInt(secondYear)
                            && firstYear.length() == 0
                            && Integer.parseInt(ProductID) == Integer.valueOf(electronicsList.get(i).getProductID())
                            && matchKey(keyWord, electronicsList.get(i).getDescription())) {
                        System.out.println(electronicsList.get(i).toString());
                        YearKeyId = true;
                    }
                }
                //iterates through the electronicslist to find a product with years less than the given year by user 
                //while matching product ID, year and keyword
                else if ((Integer.valueOf(electronicsList.get(i).getYear()) >= Integer.parseInt(firstYear)
                        && secondYear.length() == 0)
                        && Integer.parseInt(ProductID) == Integer.valueOf(electronicsList.get(i).getProductID())
                        && matchKey(keyWord, electronicsList.get(i).getDescription())) {
                    System.out.println(electronicsList.get(i).toString());
                    YearKeyId = true;

                } 
                //iterates through the electronicslist to find a product with years less than the given year by user 
                //while matching product ID, year and keyword
                else if ((Integer.valueOf(electronicsList.get(i).getYear()) >= Integer.parseInt(firstYear)
                        && Integer.valueOf(electronicsList.get(i).getYear()) <= Integer.parseInt(secondYear))
                        && Integer.parseInt(ProductID) == Integer.valueOf(electronicsList.get(i).getProductID())
                        && matchKey(keyWord, electronicsList.get(i).getDescription())) {
                    System.out.println(electronicsList.get(i).toString());
                    YearKeyId = true;

                } else {

                }
            }
        }

        if (!YearKeyId) {
            System.out.println("No match found \n");
        }
    }

    //Displays the electronic from electronic list
    public void printElectronicsList(ArrayList<Electronics> electronicsList) {
        if (electronicsList.isEmpty())
            System.out.println("There are no electronics in the list");

        else {
            for (int i = 0; i < electronicsList.size(); i++) {
                System.out.println("\n");
                System.out.println("Total Electronics " + (i + 1));
                System.out.println(electronicsList.get(i).toString());

            }
        }

    }

    public static void main(String[] args) {
        // Initializations
        EStoreSearch product = new EStoreSearch();
        Scanner input = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        String one = "(1) Enter 1 to add a product. \n";
        String two = "(2) Enter 2 search a product. \n";
        String three = "(3) Enter 3 to quit. \n";

        int inputvalue = 0;
        while (inputvalue != 3) {
            System.out.println(one + two + three);
            inputvalue = input.nextInt();

            switch (inputvalue) {
                case 1:

                    System.out.println("Choose which product you would like to add (book or electronic?): ");
                    String check = input2.nextLine().trim();

                    if (check.toLowerCase().equals("book") || check.toLowerCase().equals("b"))
                        product.addBook();

                    else if (check.toLowerCase().equals("electronic") || check.toLowerCase().equals("e"))
                        product.addElectronics();
                    else
                        System.out.println("Book or electronic wasn't properly specified");
                    break;

                case 2:

                    System.out.println("Choose which product you would like to search (book or electronic?): ");
                    String check2 = input2.nextLine().trim();

                    if (check2.toLowerCase().equals("book") || check2.toLowerCase().equals("b"))
                        try {
                            product.searchBook();
                        } catch (Exception e) {
                        }
                    else if (check2.toLowerCase().equals("electronic") || check2.toLowerCase().equals("e"))
                        
                          try {
                            product.searchElectronics();
                        } catch (Exception e) {
                        }
                    else
                        System.out.println("Book or electronic wasn't properly specified");
                    break;

                case 3:
                
                    System.out.println("Goodbye :)");
                    break;
            }
        }
    }
}
