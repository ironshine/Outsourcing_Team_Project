package com.sparta.outsourcing_team_project.order.repository;

import com.sparta.outsourcing_team_project.order.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
}
