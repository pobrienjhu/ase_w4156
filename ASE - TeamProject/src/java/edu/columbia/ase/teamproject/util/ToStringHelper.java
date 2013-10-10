package edu.columbia.ase.teamproject.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ToStringHelper {

    private  static String separator = "\n";

    public ToStringHelper(String seperator) {
        super();
        ToStringHelper.separator = seperator;

    }

    public  static String toString(Collection<?> collection) {
    	if(collection == null ) return "";
        StringBuilder sb = new StringBuilder();
        for (Object object :collection) {
            sb.append(separator).append(object);
        }
        return sb.toString();
    }

    public static String toString(Map<?,?> map) {
    	if( map == null ) return "";
        StringBuilder sb = new StringBuilder();
        for (Object object : map.keySet()) {
            sb.append(separator).append(map.get(object));
        }
        return sb.toString();
    }

    public static String toString(Set<?> set) {
    	if(set == null) return "";
        StringBuilder sb = new StringBuilder();
        for (Object object : set) {
            sb.append(separator).append(object);
        }
        return sb.toString();
    }

    public static void print(Collection<?> l) {
        System.out.println(toString(l));    
    }
    public static void print(Map<?,?> m) {
        System.out.println(toString(m));    
    }
    public static void print(Set<?> s) {
        System.out.println(toString(s));    
    }

}
