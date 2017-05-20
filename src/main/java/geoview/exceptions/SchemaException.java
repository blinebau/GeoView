package geoview.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SchemaException extends Exception {
    ArrayList<String> schemaTags;
    
    public SchemaException(){
        
    }

    public SchemaException(List<String> missingSchemaTags, String msg) {
        super(msg + "\n Missing Schema Tags: " + Arrays.toString(missingSchemaTags.toArray()));
    }

    public SchemaException(String msg) {
        super(msg);
    }
}
