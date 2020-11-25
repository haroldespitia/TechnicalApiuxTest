package com.apiux.technicaltest.TechnicalTest.model.dao;

import com.apiux.technicaltest.TechnicalTest.model.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Provee los métodos necesarios para realizar operaciones sobre la entidad {@link Task}
 */
@Repository
public interface ITaskRepositoryDao extends JpaRepository<Task, Integer> {
}
