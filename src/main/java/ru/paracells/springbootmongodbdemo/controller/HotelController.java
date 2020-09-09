package ru.paracells.springbootmongodbdemo.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.paracells.springbootmongodbdemo.model.Hotel;
import ru.paracells.springbootmongodbdemo.model.QHotel;
import ru.paracells.springbootmongodbdemo.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private HotelRepository hotelRepository;

    @Autowired
    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @GetMapping("/all")
    public List<Hotel> getAll() {
        List<Hotel> all = hotelRepository.findAll();
        return all;
    }

    // срабатывает как создание нового объекта, если добавим ID от старого, то скажет, мол дубликат
    @PutMapping
    public void insert(@RequestBody Hotel hotel) {
        hotelRepository.insert(hotel);
    }

    // срабатывает как обновление объекта с существующим ID, если без ID, то создадим новый объект
    @PostMapping
    public void update(@RequestBody Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        hotelRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getById(@PathVariable("id") String id) {
        Optional<Hotel> result = hotelRepository.findById(id);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    @GetMapping("/price/{maxPrice}")
    public List<Hotel> pricePerNight(@PathVariable("maxPrice") int maxPrice) {
        return hotelRepository.findByPricePerNightLessThan(maxPrice);
    }

    @GetMapping("/address/{city}")
    public List<Hotel> getByCity(@PathVariable("city") String city) {
        List<Hotel> byCity = hotelRepository.findByCity(city);
        return byCity;

    }

    @GetMapping("/country/{country}")
    public List<Hotel> getByCountry(@PathVariable("country") String country) {

        // create a query class Hotel
        QHotel qHotel = new QHotel("hotel");

        // using the query class we can create the filters
        BooleanExpression filterByCountry = qHotel.address.country.eq(country);

        // we can pass the filters to the findAll() method
        List<Hotel> hotels = (List<Hotel>) hotelRepository.findAll(filterByCountry);
        return hotels;

    }

    @GetMapping("/recommended")
    public List<Hotel> getRecommend() {
        final int maxPrice = 100;
        final int minRating = 7;

        // create a query class Hotel
        QHotel qHotel = new QHotel("hotel");

        // using the query class we can create the filters
        BooleanExpression filterByPrice = qHotel.pricePerNight.lt(maxPrice);
        BooleanExpression filerByRating = qHotel.reviews.any().rating.gt(minRating);

        // we can pass the filters to the findAll() method
        List<Hotel> hotels = (List<Hotel>) hotelRepository.findAll(filterByPrice.and(filerByRating));
        return hotels;
    }
}
