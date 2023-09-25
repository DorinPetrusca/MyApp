package md.dorin.proiect1.service;


import md.dorin.proiect1.dao.PeopleRepository;
import md.dorin.proiect1.entity.Car;
import md.dorin.proiect1.entity.Person;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {return peopleRepository.findAll();}

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatePerson) {
        updatePerson.setId(id);
        peopleRepository.save(updatePerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Optional<Person> getPersonByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }


    public List<Car> getCarsByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()){
            Hibernate.initialize(person.get().getCars());

            person.get().getCars().forEach(car -> {
                long diffInMillies = Math.abs(car.getTakenAt().getTime() - new Date().getTime());

                if(diffInMillies>864000000)
                    car.setExpired(true);
            });
            return person.get().getCars();
        }
        else {
            return Collections.emptyList();
        }
    }

}
