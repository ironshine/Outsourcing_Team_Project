package com.sparta.outsourcing_team_project.menu.entity;

import com.sparta.outsourcing_team_project.menu.enums.MenuCategory;
import com.sparta.outsourcing_team_project.menu.optiongroup.entity.OptionGroup;
import com.sparta.outsourcing_team_project.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Boolean menuStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuCategory menuCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OptionGroup> optionGroups = new ArrayList<>();

    public void update(String menuName, int price) {
        this.menuName = menuName;
        this.price = price;
    }

    public void updateStatus(boolean menuStatus) {
        this.menuStatus = menuStatus;
    }
}
