package org.jsonschema;

public class Persons {

    public static final Person personValid;
    public static final Person personInvalidAge;

    static {
        personValid = new Person();
        personValid.setFirstName("John");
        personValid.setLastName("Doe");
        personValid.setAge(21);

        personInvalidAge = new Person();
        personInvalidAge.setFirstName("John");
        personInvalidAge.setLastName("Doe");
        personInvalidAge.setAge(-21);
    }
}
