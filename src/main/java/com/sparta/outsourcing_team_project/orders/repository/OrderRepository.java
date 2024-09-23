package com.sparta.outsourcing_team_project.orders.repository;

import com.sparta.outsourcing_team_project.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
