package geoview.exceptions;

import java.util.ArrayList;
import java.util.Arrays;


public class SchemaException extends Exception {
    ArrayList<String> schemaTags;
    
    public SchemaException(){
        
    }

    public SchemaException(ArrayList<String> missingSchemaTags, String msg) {
        super(msg + "\n Missing Schema Tags: " + Arrays.toString(missingSchemaTags.toArray()));
    }

    public SchemaException(String msg) {
        super(msg);
    }
}
