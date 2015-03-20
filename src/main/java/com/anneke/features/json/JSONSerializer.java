package com.anneke.features.json;

import com.google.gson.JsonElement;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 *
 * @author anneke
 */
public interface JSONSerializer {

    //parse lines from JSON file to java Objects
    public Object fromJSONFile(String fileName, Class classType) throws IOException;

    //parse java objects to JSON format
    public String toJSON(JsonElement jsonElement);
    //parse java objects to JSON formatted form in pretty form
    public String toPrettyJSON(JsonElement jsonElement);

    public void writeToFile(String fileName, String output) throws IOException;


}
