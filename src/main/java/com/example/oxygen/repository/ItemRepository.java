package com.example.oxygen.repository;

import com.example.oxygen.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAll();

}
