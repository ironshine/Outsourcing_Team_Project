package com.sparta.outsourcing_team_project.domain.menu.optiongroup.service;

import com.sparta.outsourcing_team_project.config.exception.InvalidRequestException;
import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.dto.OptionGroupRequest;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.dto.OptionGroupResponse;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.entity.OptionGroup;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.repository.OptionGroupRepository;
import com.sparta.outsourcing_team_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_team_project.domain.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OptionGroupService {

    private final OptionGroupRepository optionGroupRepository;
    private final MenuRepository menuRepository;

    /**
     * 옵션 그룹 생성
     *
     * @param menuId             : 메뉴 ID
     * @param optionGroupRequest : 옵션 그룹의 이름이 담긴 DTO
     * @return OptionGroupResponse : 옵션 그룹의 이름이 담긴 DTO
     */
    @Transactional
    public OptionGroupResponse create(AuthUser authuser, long menuId, OptionGroupRequest optionGroupRequest) {
        Menu menu = isValidMenu(menuId);

        isStoreOwner(authuser, menu.getStore());

        OptionGroup newOptionGroup = OptionGroup.builder()
                .optionGroupName(optionGroupRequest.getOptionGroupName())
                .menu(menu)
                .build();

        OptionGroup savedOptionGroup = optionGroupRepository.save(newOptionGroup);

        return OptionGroupResponse.entityToDto(savedOptionGroup);
    }

    /**
     * 메뉴에 존재하는 옵션 그룹인지 확인 후
     * 옵션 그룹 업데이트
     *
     * @param menuId             : 메뉴 ID
     * @param optionGroupId      : 옵션 그룹 ID
     * @param optionGroupRequest : 옵션 그룹의 이름이 담긴 DTO
     * @return OptionGroupResponse : 옵션 그룹의 이름이 담긴 DTO
     */
    @Transactional
    public OptionGroupResponse update(AuthUser authuser, long menuId, long optionGroupId, OptionGroupRequest optionGroupRequest) {
        Menu menu = isValidMenu(menuId);

        isStoreOwner(authuser, menu.getStore());

        OptionGroup optionGroup = isExistsOptionGroup(optionGroupId);

        if (!menu.getId().equals(optionGroup.getMenu().getId())) {
            throw new InvalidRequestException("메뉴에 존재하지 않는 옵션 그룹입니다.");
        }

        optionGroup.update(optionGroupRequest.getOptionGroupName());

        return OptionGroupResponse.entityToDto(optionGroup);
    }

    /**
     * 옵션 그룹 삭제
     *
     * @param optionGroupId : 옵션 그룹 ID
     */
    @Transactional
    public void delete(AuthUser authuser, long optionGroupId) {
        OptionGroup optionGroup = isExistsOptionGroup(optionGroupId);

        isStoreOwner(authuser, optionGroup.getMenu().getStore());

        optionGroupRepository.delete(optionGroup);
    }

    /**
     * 메뉴가 존재하는지 확인 후
     * 메뉴가 삭제되었는지 확인 후
     * 가게가 폐업중인 상태인지 확인
     *
     * @param menuId : 메뉴 ID
     * @return menu : 메뉴 ENTITY
     */
    private Menu isValidMenu(long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() ->
                new InvalidRequestException("존재하지 않는 메뉴입니다."));

        if (!menu.getMenuStatus()) {
            throw new InvalidRequestException("삭제된 메뉴입니다.");
        }

        if (!menu.getStore().getStoreStatus()) {
            throw new InvalidRequestException("폐업한 가게입니다.");
        }

        return menu;
    }

    /**
     * 옵션 그룹이 존재하는지 확인
     *
     * @param optionGroupId : 옵션 그룹 ID
     * @return OptionGroup : 옵션 그룹 ENTITY
     */
    private OptionGroup isExistsOptionGroup(long optionGroupId) {
        return optionGroupRepository.findById(optionGroupId).orElseThrow(() ->
                new InvalidRequestException("존재하지 않는 옵션 그룹입니다."));
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
