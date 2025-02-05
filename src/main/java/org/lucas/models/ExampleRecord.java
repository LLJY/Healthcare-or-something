package org.lucas.models;

import org.lucas.util.ObjectBase;

public record ExampleRecord(String name, int age, String email) implements ObjectBase {

}
