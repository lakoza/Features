package com.anneke.features.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author anneke
 */
public class JSONObjectSerializer implements JSONSerializer {

    @Override
    public Object fromJSONFile(String fileName, Class classType) throws IOException {
        BufferedReader reader = null;
        Object o = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            Gson gson = new Gson();
            o = gson.fromJson(reader, classType);

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return o;
    }

    @Override
    public String toJSON(JsonElement jsonElement) {
        Gson gson = new Gson();
        String output = gson.toJson(jsonElement);
        return output;
    }

    @Override
    public String toPrettyJSON(JsonElement jsonElement) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String output = gson.toJson(jsonElement);
        return output;
    }

    @Override
    public void writeToFile(String fileName, String output) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(output);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private JsonElement makeJsonPrimitive(Object object) throws IllegalArgumentException, IllegalAccessException {
        Class type = object.getClass();
        JsonPrimitive primitive;
        if (type.equals(byte.class) || type.equals(short.class)
                || type.equals(int.class) || type.equals(long.class)
                || type.equals(float.class) || type.equals(double.class)) {
            primitive = new JsonPrimitive((Number) object);
        } else if (type.equals(boolean.class)) {
            primitive = new JsonPrimitive((Boolean) object);
        } else if (type.equals(char.class)) {
            primitive = new JsonPrimitive((Character) object);
        } else {
            // failover case: return as String
            primitive = new JsonPrimitive((String) object);
        }
        return primitive;
    }

    private JsonElement makeJsonArray(Object array) throws IllegalArgumentException, IllegalAccessException {
        JsonArray jsonArray = new JsonArray();
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            Object el = Array.get(array, i);
            JsonElement element = serialize(el);
            jsonArray.add(element);
        }
        return jsonArray;
    }

    private JsonElement makeJsonCollection(Object collection) throws IllegalArgumentException, IllegalAccessException {
        JsonArray jsonArray = new JsonArray();
        for (Object item : (Collection) collection) {
            JsonElement element = serialize(item);
            jsonArray.add(element);
        }
        return jsonArray;
    }

    private JsonElement serialize(Object object, String fieldName) throws IllegalArgumentException, IllegalAccessException {
        //JsonObject mainObject = new JsonObject();
        JsonElement jsonElement = null;
        Class objectClass = object.getClass();
        String objectClassName = objectClass.getName();
        System.out.println("Main object class " + objectClassName);
        if (objectClass.isPrimitive()) {
            jsonElement = makeJsonPrimitive(object);
            //jsonElement.addProperty(fieldName, object.toString());
        } else if (Number.class.isAssignableFrom(objectClass)) {
            jsonElement = new JsonPrimitive((Number) object);
            //mainObject.add(fieldName, jsonElement);
        } else if (Boolean.class.equals(objectClass)) {
            jsonElement = new JsonPrimitive((Boolean) object);
        } else if (Character.class.equals(objectClass)) {
            jsonElement = new JsonPrimitive((Character) object);
        } else if (objectClass.equals(String.class)) {
            jsonElement = new JsonPrimitive((String) object);
        } else if (objectClass.isArray()) {
            jsonElement = makeJsonArray(object);
        } else if (Collection.class.isAssignableFrom(objectClass)) {
            jsonElement = makeJsonCollection(object);
        } else if (Map.class.isAssignableFrom(objectClass)) {
            System.out.println(objectClass.toGenericString());
            //System.out.println(objectClass.getGenericType());
        } else if (objectClass.isEnum()) {
            jsonElement = new JsonPrimitive(object.toString());
        } else {
            Field[] fields = objectClass.getDeclaredFields();
            JsonObject jsonObject = new JsonObject();
            for (Field field : fields) {
                Object fieldValue = field.get(object);
                if (fieldValue != null) {
                    JsonElement element = serialize(fieldValue, field.getName());
                    jsonObject.add(field.getName(), element);
                } //otherwise skip the filed TODO: add null to json? 
            }
            jsonElement = jsonObject;
        }
        return jsonElement;

    }

    public JsonElement serialize(Object object) throws IllegalArgumentException, IllegalAccessException {
        if (object == null) {
            throw new IllegalArgumentException("Input object is null");
        }
        return serialize(object, null);
    }

     public <T> T deserialize(String jsonString, Class<T> type) throws InstantiationException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonString);
        return deserialize(element, type);
    }

    
//        Type type = new TypeToken<List<String>>(){}.getType();
//        Class c = new TypeToken<List<String>>(){}.getRawType();
    public <T> T deserialize(JsonElement element, Class<T> type) throws InstantiationException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        if (element.isJsonPrimitive()) { 
            return makePrimitive(element.getAsJsonPrimitive(), type);
        } else if (element.isJsonNull()) {
            // 
        } else if (element.isJsonArray()) {
            JsonArray jsonArray = element.getAsJsonArray();
            int size = jsonArray.size();
            Class componentType = type.getComponentType();
            if (type.isArray()) {
                if (componentType.isPrimitive()) {
                    componentType = boxPrimitiveType(componentType);
                }
                T[] array = (T[]) Array.newInstance(componentType, size);
                for (int i = 0; i < size; i++) {
                    T result = (T) deserialize(jsonArray.get(i), componentType);
                    array[i] = result;
                }
                return (T) array;
            } else if (Collection.class.isAssignableFrom(type)) {
                Collection collection = (Collection) type.cast(type.newInstance());
                for (int i = 0; i < size; i++) {
                    T result = (T) deserialize(jsonArray.get(i), componentType);
                    collection.add(result);
                }
                return (T) collection;
            }
        } else if (element.isJsonObject()) {
            JsonObject jsonObject = element.getAsJsonObject();
            Set entrySet = jsonObject.entrySet();
            T newInstance = (T) type.newInstance();
            for (Object entryElement : entrySet) {
                Map.Entry entry = (Map.Entry) entryElement;
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                Field field = type.getField(key);
                T object = (T) deserialize(value, field.getType());
                field.set(newInstance, object);
                System.out.println(key + " " + object);
            }
            return newInstance;
        }
        return (T) new Object();

    }

    private <T> Class boxPrimitiveType(Class<T> primitiveType) {
        if (primitiveType.equals(int.class)) {
            return Integer.class;
        } else if (primitiveType.equals(long.class)){
            return Long.class;
        } // TODO: continue list
        return null;
    }

    private <T> T makePrimitive(JsonPrimitive primitive, Class<T> type) throws ClassNotFoundException {
        if (type == null) {
            if (primitive.isNumber()) {
                return (T) primitive.getAsNumber();
            } else if (primitive.isBoolean()) {
                return (T) Boolean.valueOf(primitive.getAsBoolean());
            } else {
                return (T) primitive.getAsString();
            }
        } else if (type.isEnum()) {
            Class enumType = Class.forName(type.getName());
            return (T) Enum.valueOf(enumType, primitive.getAsString());
        }else { 
            if (type.equals(byte.class) || Byte.class.isAssignableFrom(type)) {
                return (T) new Byte(primitive.getAsByte());
            } else if (type.equals(short.class) || Short.class.isAssignableFrom(type)) {
                return (T) new Short(primitive.getAsShort());
            } else if (type.equals(int.class) || Integer.class.isAssignableFrom(type)) {
                return (T) new Integer(primitive.getAsInt());
            } else if (type.equals(long.class) || Long.class.isAssignableFrom(type)) {
                return (T) new Long(primitive.getAsLong());
            } else if (type.equals(float.class) || type.equals(double.class)) {
                return (T) new Double(primitive.getAsDouble());
            } else if (type.equals(boolean.class)) {
                return (T) new Boolean(primitive.getAsBoolean());
            } else if (type.equals(char.class)) {
                return (T) new Character(primitive.getAsCharacter());
            } else if (String.class.equals(type)) {
                return (T) primitive.getAsString();
            } else {
                return (T) primitive.getAsString();
            }
        }
    }
// TODO: Map
// TODO: Class w/o default constructor
// TODO: Method with argument list a(int ...)    
}
