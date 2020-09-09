package ru.paracells.springbootmongodbdemo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.paracells.springbootmongodbdemo.model.Address;
import ru.paracells.springbootmongodbdemo.model.Hotel;
import ru.paracells.springbootmongodbdemo.model.Review;
import ru.paracells.springbootmongodbdemo.repository.HotelRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// it will automatically run by Spring when start app
// it will be seed database our data
@Component
public class DbSeeder implements CommandLineRunner {

    private HotelRepository hotelRepository;

    @Autowired
    public DbSeeder(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Hotel marriot = new Hotel(
                "Marriot",
                130,
                new Address("Paris", "France"),
                Arrays.asList(
                        new Review("John", 8, false),
                        new Review("Mary", 7, true))
        );
        Hotel ibis = new Hotel(
                "Ibis",
                90,
                new Address("Bucharest", "Romania"),
                Arrays.asList(
                        new Review("Teddy", 9, true))
        );
        Hotel sofitel = new Hotel(
                "Sofitel",
                200,
                new Address("Rome", "Italy"),
                new ArrayList<>()
        );

        // drop all hotels
        hotelRepository.deleteAll();

        // add hotels to database
        List<Hotel> hotels = Arrays.asList(marriot, ibis, sofitel);
        hotelRepository.saveAll(hotels);
    }
}
