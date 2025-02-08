package org.lucas.models;

import org.lucas.util.ObjectBase;

public record PatientInfo(String id, String name, String email) implements ObjectBase {}