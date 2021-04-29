package com.coti.assignment.solution.persistence.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "numberedArray")
public class NumberedArrayEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    long value;

    public NumberedArrayEntity(){}

    public NumberedArrayEntity(long numValue)
    {
        this.value = numValue;
    }

    @Override
    public String toString()
    {
        return "{id=" + id + ", value=" + value + "}";
    }
}
