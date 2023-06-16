package fr.mb.bananesexport.exception;

import java.util.HashMap;
import java.util.Map;

public abstract class BananesExportException extends Exception{

    public BananesExportException(String message) {
        super(message);
    }

    public Map<String, String> getErrorMessageResponse(){
        Map<String, String> map = new HashMap<>();
        map.put("message", this.getMessage());
        return map;
    }

}
