package md.dorin.proiect1.service;


import md.dorin.proiect1.dao.CarsRepository;
import md.dorin.proiect1.entity.Car;
import md.dorin.proiect1.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CarsService {
    private final CarsRepository carsRepository;

    @Autowired
    public CarsService(CarsRepository carsRepository){
        this.carsRepository = carsRepository;
    }

    public List<Car> findAll(boolean sortByYear) {
        if(sortByYear)
            return carsRepository.findAll(Sort.by("year"));
        else
            return carsRepository.findAll();
    }

    public List<Car> findWithPagination(Integer page, Integer carsPerPage, boolean sortByYear) {
        if (sortByYear)
            return carsRepository.findAll(PageRequest.of(page, carsPerPage, Sort.by("year"))).getContent();
        else
            return carsRepository.findAll(PageRequest.of(page, carsPerPage)).getContent();
    }

    public Car findOne(int id) {
        Optional<Car> foundCar = carsRepository.findById(id);
        return foundCar.orElse(null);
    }

    public List<Car> searchByTitle(String query) {
        return carsRepository.findByCarBrandStartingWith(query);
    }

    @Transactional
    public void save (Car car){
        carsRepository.save(car);
    }

    @Transactional
    public void update(int id, Car updatedCar) {
        Car carToBeUpdated = carsRepository.findById(id).get();
        updatedCar.setId(id);
        updatedCar.setOwner(carToBeUpdated.getOwner());
        carsRepository.save(updatedCar);
    }

    @Transactional
    public void delete(int id) {
        carsRepository.deleteById(id);
    }

    @Transactional
    public Person getCarOwner(int id) {
        return  carsRepository.findById(id).map(Car::getOwner).orElse(null);
    }

    @Transactional
    public void release(int id) {
        carsRepository.findById(id).ifPresent(
                car -> {
                    car.setOwner(null);
                    car.setTakenAt(null);
                }

        );
            }
    @Transactional
    public void assign(int id, Person selectedPerson){
        carsRepository.findById(id).ifPresent(
                car -> {
                    car.setOwner(selectedPerson);
                    car.setTakenAt(new Date());
                }
        );
    }

}
