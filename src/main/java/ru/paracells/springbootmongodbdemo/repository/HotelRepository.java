package ru.paracells.springbootmongodbdemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.paracells.springbootmongodbdemo.model.Hotel;

import java.util.List;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String>, QuerydslPredicateExecutor<Hotel> {

    List<Hotel> findByPricePerNightLessThan(int maxPrice);

    /* можно задавать кастомные запросы с помощью mongodb query
       можно обращаться к подспискам базы
       здесь мы получем город, который находится в таблице адреса
       который находится в таблице отелей
       в том же postgresql нужно было создавать onetomany
       а здесь просто добавили как обычные объекты
       обязательно указывать '' для запроса
     */
    @Query(value = "{'address.city':?0}")
    List<Hotel> findByCity(String city);


}
