package com.cydeo.javahedgehogsproject.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseEntity{

    private String description;

}
