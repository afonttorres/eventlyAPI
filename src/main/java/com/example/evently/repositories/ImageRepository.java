package com.example.evently.repositories;

import com.example.evently.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByOrderById();

    @Query("select i from Image i where upper(i.imgUrl) = upper(:imgUrl)")
    List<Image> findByImgUrl(@Param("imgUrl") String imgUrl);
}
