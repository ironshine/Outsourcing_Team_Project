package com.sparta.outsourcing_team_project.domain.orders.repository;

import com.sparta.outsourcing_team_project.domain.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
