package com.example.java_app.unko;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnkoRepository extends JpaRepository<Unko, Long> {

}
