package org.lucas.util;

import static org.lucas.Globals.gson;
import static org.lucas.Globals.gsonPrettyPrint;

public interface ObjectBase {
    public boolean equals(Object obj);
    /**
     * Returns an efficiently formatted JSON string, relies on static gson object defined in Globals.java
     * @return Efficiently formatted JSON string
     */
    default String toJsonString(){
        return gson.toJson(this);
    }

    /**
     * Returns a PRETTY formatted JSON string, relies on static gson object defined in Globals.java
     * Useful for debugging and inspecting object data
     * @return Pretty formatted JSON string
     */
    default String toPrettyJsonString(){
        return gsonPrettyPrint.toJson(this);
    }
}
