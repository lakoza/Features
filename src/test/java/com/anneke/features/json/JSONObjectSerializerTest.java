package com.anneke.features.json;

import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author anneke
 */
public class JSONObjectSerializerTest {

    public static JSONObjectSerializer instance;

    public JSONObjectSerializerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        instance = new JSONObjectSerializer();

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull() throws Exception {
        Object testObject = null;
        JsonElement result = instance.serialize(testObject);
    }

    //@Ignore
    @Test
    public void testPrimitive() throws Exception {
        int testObject = 123;
        String expectedResult = "123";
        JsonElement result = instance.serialize(testObject);
        String jsonResult = instance.toPrettyJSON(result);
        System.out.println(jsonResult);
        assertEquals(expectedResult, jsonResult);

        int objectResult = instance.deserialize(expectedResult, int.class);
        assertEquals(objectResult, testObject);
    }

    //@Ignore
    @Test
    public void testString() throws Exception {
        String testObject = "new String";
        String expectedResult = "\"new String\"";
        JsonElement result = instance.serialize(testObject);
        String jsonResult = instance.toPrettyJSON(result);
        System.out.println(jsonResult);
        assertEquals(expectedResult, jsonResult);

        String objectResult = instance.deserialize(expectedResult, String.class);
        assertEquals(objectResult, testObject);
    }
    
    //@Ignore
    @Test
    public void testInteger() throws Exception {
        Integer testObject = 1234;
        String expectedResult = "1234";
        JsonElement result = instance.serialize(testObject);
        String jsonResult = instance.toPrettyJSON(result);
        System.out.println(jsonResult);
        assertEquals(expectedResult, jsonResult);

        Integer objectResult = instance.deserialize(expectedResult, Integer.class);
        assertEquals(objectResult, testObject);
    }

    //@Ignore
    @Test
    public void testPrimitiveArray() throws Exception {
        int[] testObject = new int[]{1, 2, 3, 4};
        String expectedResult = "[1,2,3,4]";
        JsonElement result = instance.serialize(testObject);
        String jsonResult = instance.toJSON(result);
        String jsonResultPretty = instance.toPrettyJSON(result);
        System.out.println(jsonResultPretty);
        assertEquals(expectedResult, jsonResult);

        int[] objectResult = instance.deserialize(expectedResult, int[].class);
        assertArrayEquals(objectResult, testObject);
    }

    //@Ignore
    @Test
    public void testIntegerArray() throws Exception {
        Integer[] testObject = new Integer[]{1, 2, 3, 4};
        String expectedResult = "[1,2,3,4]";
        JsonElement result = instance.serialize(testObject);
        String jsonResult = instance.toJSON(result);
        String jsonResultPretty = instance.toPrettyJSON(result);
        System.out.println(jsonResultPretty);
        assertEquals(expectedResult, jsonResult);

        Integer[] objectResult = instance.deserialize(expectedResult, Integer[].class);
        assertArrayEquals(objectResult, testObject);
    }

    @Ignore
    @Test
    public void testStringArray() throws Exception {
        String[] testObject = new String[]{"one", "two", "three"};
        String expectedResult = "[\"one\",\"two\",\"three\"]";
        JsonElement result = instance.serialize(testObject);
        String jsonResult = instance.toJSON(result);
        String jsonResultPretty = instance.toPrettyJSON(result);
        System.out.println(jsonResultPretty);
        assertEquals(expectedResult, jsonResult);

        String[] objectResult = instance.deserialize(expectedResult, String[].class);
        assertArrayEquals(objectResult, testObject);
    }
  
    //@Ignore
    @Test
    public void testPrimitiveList() throws Exception {
        List testObject = new ArrayList();
        testObject.add(1);
        testObject.add(2);
        testObject.add(3);
        testObject.add(4);
        String expectedResult = "[1,2,3,4]";
        JsonElement result = instance.serialize(testObject);
        String jsonResult = instance.toJSON(result);
        String jsonResultPretty = instance.toPrettyJSON(result);
        System.out.println(jsonResultPretty);
        assertEquals(expectedResult, jsonResult);

        List objectResult = instance.deserialize(expectedResult, ArrayList.class);
        assertEquals(objectResult, testObject);
    }

    //@Ignore
    @Test
    public void testIntegerGenericList() throws Exception {
        List<Integer> testObject = new ArrayList<Integer>();
        testObject.add(1);
        testObject.add(2);
        testObject.add(3);
        testObject.add(4);
        String expectedResult = "[1,2,3,4]";
        JsonElement result = instance.serialize(testObject);
        String jsonResult = instance.toJSON(result);
        String jsonResultPretty = instance.toPrettyJSON(result);
        System.out.println(jsonResultPretty);
        assertEquals(expectedResult, jsonResult);

        List<Integer> objectResult = instance.deserialize(expectedResult, new ArrayList<Integer>().getClass());
        assertEquals(objectResult, testObject);
    }

    //@Ignore
    @Test
    public void testCustomGenericList() throws Exception {
        List<Integer> testObject = new ArrayList<Integer>();
        testObject.add(1);
        testObject.add(2);
        testObject.add(3);
        testObject.add(4);
        String expectedResult = "[1,2,3,4]";
        JsonElement result = instance.serialize(testObject);
        String jsonResult = instance.toJSON(result);
        String jsonResultPretty = instance.toPrettyJSON(result);
        System.out.println(jsonResultPretty);
        assertEquals(expectedResult, jsonResult);

        List<Integer> objectResult = instance.deserialize(expectedResult, new ArrayList<Integer>().getClass());
        assertEquals(objectResult, testObject);
    }

    //@Ignore
    @Test
    public void testEnum() throws Exception {
        PhoneNumber testObject = new PhoneNumber("12345", PhoneNumber.Type.SELL);

        String expectedResult = "{\"number\":\"12345\",\"type\":\"SELL\"}";
        JsonElement result = instance.serialize(testObject);
        String jsonResult = instance.toJSON(result);
        String jsonResultPretty = instance.toPrettyJSON(result);
        System.out.println(jsonResultPretty);
        assertEquals(expectedResult, jsonResult);

        PhoneNumber objectResult = instance.deserialize(expectedResult, PhoneNumber.class);
        assertEquals(objectResult, testObject);
    }

    //@Ignore
    @Test
    public void testCustomClass() throws Exception {
        Person testObject= new Person();
        testObject.id = 1;
        testObject.firstName = "Lisa";
        testObject.lastName = "Levenhuk";
        testObject.age = 17;
        testObject.adress = "Khimki";
        testObject.isMarried = false;
        testObject.phoneNumber = new PhoneNumber("12345", PhoneNumber.Type.SELL);
        testObject.husband = new Person();
        testObject.positions = new long[]{55,77};
//        List<String> works = new ArrayList<String>();
//        works.add("one");
//        works.add("two");
//        testObject.workPlaces = works;
//        List<Person> friends = new ArrayList<Person>();
//        Person friend = new Person();
//        friend.firstName = "Lisa uulu";
//        friends.add(friend);
//        testObject.friends = friends;

        String expectedResult = "{\"id\":1,\"firstName\":\"Lisa\",\"lastName\":\"Levenhuk\",\"age\":17,\"adress\":\"Khimki\",\"isMarried\":false,"
                + "\"phoneNumber\":{\"number\":\"12345\",\"type\":\"SELL\"},"
                + "\"positions\":[55,77],"
                + "\"husband\":{\"age\":0,\"isMarried\":false}}";
        JsonElement result = instance.serialize(testObject);
        String jsonResult = instance.toJSON(result);
        String jsonResultPretty = instance.toPrettyJSON(result);
        System.out.println(jsonResultPretty);
        assertEquals(expectedResult, jsonResult);

        Person objectResult = instance.deserialize(expectedResult, Person.class);
        assertEquals(objectResult, testObject);
    }

}
