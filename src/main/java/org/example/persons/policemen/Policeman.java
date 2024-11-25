package org.example.persons.policemen;

import org.example.persons.Human;

public abstract class Policeman extends Human implements Arrestable {
    private final int id;

    public Policeman(int id, Human human) {
        super(human.getName(), human.getAge());
        this.id = id;
    }

    public Policeman(int id, String name, int age) {
        this(id, new Human(name, age));
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Officer Id: " + id + " " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(!super.equals(o)) {
            return false;
        }
        if(!(o instanceof Policeman p)) {
            return false;
        }

        return id == p.id;
    }
}
