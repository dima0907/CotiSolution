package com.coti.assignment.solution.persistence.repository;


import com.coti.assignment.solution.persistence.domain.NumberedArrayEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INumberedArrayEntity extends CrudRepository<NumberedArrayEntity, Integer>
{
    NumberedArrayEntity findById(int id);

    List<NumberedArrayEntity> findAll();
}
