package org.example.persons.policemen;

import org.example.jails.Jail;
import org.example.persons.Human;
import org.example.persons.prisoners.Prisoner;
import org.example.persons.prisoners.RegularPrisoner;
import org.example.persons.prisoners.Sentence;

import java.util.Random;

public class PrisonOfficer extends Policeman implements Releasable {
    private Jail jail;

    public PrisonOfficer(int id, Human human, Jail jail) {
        super(id, human);

        this.jail = jail;
        jail.addNewOfficer(this);
    }

    public PrisonOfficer(int id, String name, int age, Jail jail) {
        super(id, name, age);

        this.jail = jail;
        jail.addNewOfficer(this);
    }

    public PrisonOfficer(Policeman policeman, Jail jail) {
        this(policeman.getId(), (Human)policeman, jail);
    }

    @Override
    public Prisoner arrest(Human human) {
        Random rnd = new Random();
        Prisoner p;

        if(human instanceof Prisoner) {
            p = (Prisoner) human;
        }
        else {
            p = new RegularPrisoner(rnd.nextInt(10000), human, new Sentence(7));
        }

        this.jail.addNewPrisoner(p, this);
        return p;
    }

    @Override
    public void release(Prisoner prisoner) {
        this.jail.releasePrisoner(prisoner, this);
    }
}
