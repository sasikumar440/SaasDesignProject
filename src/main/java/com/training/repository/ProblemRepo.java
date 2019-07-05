package com.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.entity.Data;

@Repository
public interface ProblemRepo extends JpaRepository<Data, Integer> {
}
