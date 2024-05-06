package ch.zhaw.springboot.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {
    private final Map<Integer, Person> persons = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        this.persons.put(idCounter.incrementAndGet(), new Person(1, "Barack Obama"));
        this.persons.put(idCounter.incrementAndGet(), new Person(2, "Donald Trump"));
        this.persons.put(idCounter.incrementAndGet(), new Person(3, "Joe Biden"));
        System.out.println("Initial data loaded");
    }

    // GET request to retrieve a person by ID
    @GetMapping("/person/{id}")
    public Person getPerson(@PathVariable Integer id) {
        return this.persons.get(id);
    }

    // POST request to add a new person
    @PostMapping("/person")
    public Person addPerson(@RequestBody Person person) {
        int newId = idCounter.incrementAndGet();
        person.setId(newId);
        this.persons.put(newId, person);
        return person;
    }

    // PUT request to update an existing person
    @PutMapping("/person/{id}")
    public Person updatePerson(@PathVariable Integer id, @RequestBody Person person) {
        person.setId(id);
        this.persons.put(id, person);
        return person;
    }

    // DELETE request to remove a person
    @DeleteMapping("/person/{id}")
    public Person deletePerson(@PathVariable Integer id) {
        return this.persons.remove(id);
    }
}
