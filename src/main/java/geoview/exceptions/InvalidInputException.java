package geoview.exceptions;


public class InvalidInputException extends Exception {

    public InvalidInputException(){
        
    }

    public InvalidInputException(String tag, String value, String msg) {
        super(msg + "/n Invalid input type for tag: " + tag + " value: " + value);
    }

    public InvalidInputException(String msg) {
        super(msg);
    }
}
