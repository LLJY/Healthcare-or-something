package org.lucas;

public class ExampleUiController {

    ExampleRecord exampleRecord;

    /**
     * Gets the example user
     * @return example user
     */
    public ExampleRecord getExampleUser() {
        // usually you will get the user from the db or network from here

        // replace with creating dummy data as an example
        if(exampleRecord == null) {
            exampleRecord = new ExampleRecord("Lucas Lee", 12, "aaaaa@aaaa.com");
        }
        return exampleRecord;
    }
    public void nextPageCalled(String str){
        System.out.println("You navigated to the next page using option: " + str);
        System.out.println("Continuing in 1 second...");

        // ignore this try catch block, java will scream at me if i dont add it
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
