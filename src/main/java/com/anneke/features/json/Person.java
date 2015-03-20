package com.anneke.features.json;

import com.anneke.features.json.PhoneNumber;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author anneke
 */
public class Person {
    public Integer id;
    public String firstName;
    public String lastName;
    public int age;
    public String adress;
    public boolean isMarried;
    public PhoneNumber phoneNumber;
    public long[] positions;
//    public List<String> workPlaces;
    public Person husband;
//    public List<Person> friends;

//    @Override
//    public String toString() {
//        return "Person{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName 
//                + ", age=" + age + ", adress=" + adress + ", isMarried=" + isMarried 
//                + ", phoneNumber=" + phoneNumber + ", positions=" + positions + ", workPlaces=" 
//                + workPlaces + ", husband=" + husband + ", friends=" + friends + '}';
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.firstName);
        hash = 47 * hash + Objects.hashCode(this.lastName);
        hash = 47 * hash + this.age;
        hash = 47 * hash + Objects.hashCode(this.adress);
        hash = 47 * hash + (this.isMarried ? 1 : 0);
        hash = 47 * hash + Objects.hashCode(this.phoneNumber);
        hash = 47 * hash + Arrays.hashCode(this.positions);
        hash = 47 * hash + Objects.hashCode(this.husband);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (this.age != other.age) {
            return false;
        }
        if (!Objects.equals(this.adress, other.adress)) {
            return false;
        }
        if (this.isMarried != other.isMarried) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        if (!Arrays.equals(this.positions, other.positions)) {
            return false;
        }
        if (!Objects.equals(this.husband, other.husband)) {
            return false;
        }
        return true;
    }

      
}
