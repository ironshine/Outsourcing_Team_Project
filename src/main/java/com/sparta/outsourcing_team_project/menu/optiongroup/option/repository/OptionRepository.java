package com.sparta.outsourcing_team_project.menu.optiongroup.option.repository;

import com.sparta.outsourcing_team_project.menu.optiongroup.option.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByIdIn(List<Long> ids);
}
