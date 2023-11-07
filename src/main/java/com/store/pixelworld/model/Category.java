package com.store.pixelworld.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="category_id")
    private int id;
    private String name;



}
