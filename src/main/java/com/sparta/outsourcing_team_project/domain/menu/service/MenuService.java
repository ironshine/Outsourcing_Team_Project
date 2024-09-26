package com.sparta.outsourcing_team_project.domain.menu.service;

import com.sparta.outsourcing_team_project.config.exception.InvalidRequestException;
import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.menu.dto.MenuRequest;
import com.sparta.outsourcing_team_project.domain.menu.dto.MenuResponse;
import com.sparta.outsourcing_team_project.domain.menu.dto.MenuUpdateRequest;
import com.sparta.outsourcing_team_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_team_project.domain.menu.enums.MenuCategory;
import com.sparta.outsourcing_team_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_team_project.domain.store.entity.Store;
import com.sparta.outsourcing_team_project.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    /**
     * 메뉴 생성
     *
     * @param storeId     : 가게 ID
     * @param menuRequest : 메뉴 이름과 메뉴 가격, 메뉴 카테고리가 담긴 DTO
     * @return MenuResponse : 메뉴 이름과 메뉴 가격이 담긴 DTO
     */
    @Transactional
    public MenuResponse create(AuthUser authUser, long storeId, MenuRequest menuRequest) {
        Store store = isValidStore(storeId);

        isStoreOwner(authUser, store);

        Menu newMenu = Menu.builder()
                .menuName(menuRequest.getMenuName())
                .price(menuRequest.getPrice())
                .menuStatus(true)
                .menuCategory(MenuCategory.of(menuRequest.getMenuCategory()))
                .store(store)
                .build();

        Menu savedMenu = menuRepository.save(newMenu);

        return MenuResponse.entityToDto(savedMenu);

    }

    /**
     * 가게에 존재하는 메뉴인지 확인 후
     * 메뉴 업데이트
     *
     * @param storeId           : 가게 ID
     * @param menuId            : 메뉴 ID
     * @param menuUpdateRequest : 메뉴 이름과 메뉴 가격이 담긴 DTO
     * @return MenuResponse : 메뉴 이름과 메뉴 가격이 담긴 DTO
     */
    @Transactional
    public MenuResponse update(AuthUser authUser, long storeId, long menuId, MenuUpdateRequest menuUpdateRequest) {
        Store store = isValidStore(storeId);

        isStoreOwner(authUser, store);

        Menu menu = isExistMenu(menuId);

        if (!menu.getStore().getId().equals(store.getId())) {
            throw new InvalidRequestException("가게에 존재하지 않는 메뉴입니다.");
        }

        menu.update(
                menuUpdateRequest.getMenuName(),
                menuUpdateRequest.getPrice()
        );

        return MenuResponse.entityToDto(menu);
    }

    /**
     * 메뉴 삭제
     *
     * @param menuId : 메뉴 ID
     */
    @Transactional
    public void delete(AuthUser authUser, long menuId) {
        Menu menu = isExistMenu(menuId);

        isStoreOwner(authUser, menu.getStore());

        menu.updateStatus(false);
    }

    /**
     * 가게가 존재하는지 확인 후
     * 폐업한 가게인지 확인
     *
     * @param storeId : 가게 ID
     * @return Store : 가게 ENTITY
     */
    private Store isValidStore(long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new InvalidRequestException("존재하지 않는 가게 입니다."));

        if (!store.getStoreStatus()) {
            throw new InvalidRequestException("폐업한 가게 입니다.");
        }

        return store;
    }

    /**
     * 메뉴가 존재하는지 확인
     *
     * @param menuId : 메뉴 ID
     * @return Menu : 메뉴 ENTITY
     */
    private Menu isExistMenu(long menuId) {
        return menuRepository.findById(menuId).orElseThrow(() ->
                new InvalidRequestException("존재하지 않는 메뉴입니다."));
    }

    /**
     * 가게의 사장인지 확인
     *
     * @param authUser : 사용자 정보가 담긴 객체
     * @param store    : 해당 메뉴를 가지고 있는 가게
     */
    private void isStoreOwner(AuthUser authUser, Store store) {
        if (!authUser.getUserId().equals(store.getUser().getUserId())) {
            throw new InvalidRequestException("해당 가게의 사장님이 아닙니다.");
        }
    }
}
