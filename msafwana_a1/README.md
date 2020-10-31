# CIS2430-A1

## INTRODUCTION
The intention of the program is to hold multiple kind of products in particular books and electronic items. It allows the user to add and search for them online. To identify each product in the eStore uniquely they have unique product IDs. The store will typically display ID, price, author, year, description and publisher for book whereas for electronic item it will display maker, price, year, ID and description.

## LIMITATIONS OF THE PROGRAM
- When entering the price the input should be in the form of integer or double values. Eg 1234 or 123.45 but NOT $123 or $12.45. ONLY numbers as input.

- The command loop will display as
(1) Enter 1 to add a product
(2) Enter 2 search a product
(3) Enter 3 to quit

You should enter 1 to add, 2 to search or 3 to quit. You cannot for example one or ONE and so on as it will terminate the program.
- To quit the program you should enter 3 to quite and not 'exit' or 'q' or 'quit'.

## USER GUIDE/INFORMATION
All required gradle files as well as java files have been zipped under the name msafwan_a1.zip
- Unzip the folder
- The pacakage name is eStoreSearch
- Run gradle build 
- It should build successfully with no errors or warnings
- Execute gradle run

## JUNIT

There are couple of Junit tests to check if accessors and mutators work. It explicitly adds each string (description, author, price, productID, publisher) one at a time to test if a match is found or not. There are couple test to see if a new book is added if publisher and author is empty. The same test follows for electronic items while the maker is empty.
There are couple of test done to find if the keywords from the description is split and stored in token and to see if a match is found to display all the books with the required keyword, that happens for assertTrue when the match is found and assertFalse when the match is not found.

Ex. String Vdescription = "Java programming"; 
    The Vdescription is the description of the book

    String keyWord[] = Vdescription.split("");
    We split the description by white spaces to store keyWord[0] as Java and keyWord[1] as programming

    String falsedescription = "Different description";
    String keyWord2[] = falsedescription.split("");

    The same procedure followings for the above two parameters.

    assertFalse(testObj.matchKey(keyWord2,Vdescription));
    KeyWord2 stores "different" "description" which finds not match in Vdescription
    assertTrue(testObj.matchKey(keyWord,Vdescription));
    KeyWord stores "Java" "programming" which finds  match in Vdescription

To run the tests
```java
gradle build
gradle test
```

## RUN PROGRAM

```java
gradle build
gradle run
```
## FUTURE IMPROVMENTS

- Give more chances to user incase of a mistake instead of program crashing
- More efficiency in searching for an product in arraylist
- Better modularity in coding style
- Robust test for EStoreSearch class