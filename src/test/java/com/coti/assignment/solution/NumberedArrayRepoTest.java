package com.coti.assignment.solution;

import com.coti.assignment.solution.persistence.domain.NumberedArrayEntity;
import com.coti.assignment.solution.persistence.repository.INumberedArrayEntity;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NumberedArrayRepoTest
{
    @Autowired
    private INumberedArrayEntity repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void whenFindById_thenReturnValue()
    {
        NumberedArrayEntity newEntry = new NumberedArrayEntity(123);
        entityManager.persist(newEntry);
        entityManager.flush();

        NumberedArrayEntity found = repo.findById(1);

        Assertions.assertThat(found.getValue()).isEqualTo(newEntry.getValue());
    }
}
