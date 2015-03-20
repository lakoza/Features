package com.anneke.features.json;

import java.util.Objects;

/**
 *
 * @author anneke
 */
public class PhoneNumber {
    
    public enum Type {SELL, WORK, HOME}

    public String number;
    public Type type;

    public PhoneNumber() {
    }
    
    public PhoneNumber(String number, Type type) {
        this.number = number;
        this.type = type;
    }

    @Override
    public String toString() {
        return "number=" + number + ", type=" + type;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.number);
        hash = 97 * hash + Objects.hashCode(this.type);
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
        final PhoneNumber other = (PhoneNumber) obj;
        if (!Objects.equals(this.number, other.number)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }
    
    
    
}
