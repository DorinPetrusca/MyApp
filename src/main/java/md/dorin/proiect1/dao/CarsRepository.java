package md.dorin.proiect1.dao;


import md.dorin.proiect1.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarsRepository extends JpaRepository<Car, Integer> {
    List<Car> findByCarBrandStartingWith(String carBrand);
}
