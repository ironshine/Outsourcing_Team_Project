package com.sparta.outsourcing_team_project.domain.order.repository;

import com.sparta.outsourcing_team_project.domain.order.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
}
