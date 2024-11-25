package org.example.persons.policemen;

import org.example.persons.Human;
import org.example.persons.prisoners.Prisoner;

public interface Releasable {
    void release(Prisoner prisoner);
}
