package com.mouritech.auth.repo;

import com.mouritech.auth.dao.Screen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface ScreenRepo extends JpaRepository<Screen, Long> {

    @Query("select s from Screen s where s.module.id = :moduleId")
    List<Screen> findByModuleId(@PathParam("moduleId") Long moduleId );

}
