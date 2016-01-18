package model.business;

/**
 *
 * @author Kevin GUTIERREZ
 * @author Pierre DAUTREY
 * @author Corentin BECT
 */
public class Person {

    private final String id;
    private final String name;
    private final String firstname;
    private final int age;
    
    public Person(String id, String name, String firstname, int age) {
        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public int getAge() {
        return age;
    }  

    @Override
    public String toString() {
        return name + " " + firstname;
    }
    
}
