package com.example.templater.repo;


import com.example.templater.model.Temp_Full;
import com.example.templater.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Temp_FullRepository extends JpaRepository<Temp_Full, Integer> {
    Temp_Full findTemp_FullByName(String name);
    List<Temp_Full> findAllByUser(User user);
    Temp_Full findTemp_FullById(Integer id);
}
