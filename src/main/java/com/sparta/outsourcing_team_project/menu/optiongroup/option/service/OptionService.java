package com.sparta.outsourcing_team_project.menu.optiongroup.option.service;

import com.sparta.outsourcing_team_project.common.exception.InvalidRequestException;
import com.sparta.outsourcing_team_project.menu.optiongroup.entity.OptionGroup;
import com.sparta.outsourcing_team_project.menu.optiongroup.option.dto.OptionRequest;
import com.sparta.outsourcing_team_project.menu.optiongroup.option.dto.OptionResponse;
import com.sparta.outsourcing_team_project.menu.optiongroup.option.entity.Option;
import com.sparta.outsourcing_team_project.menu.optiongroup.option.repository.OptionRepository;
import com.sparta.outsourcing_team_project.menu.optiongroup.repository.OptionGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OptionService {

    private final OptionGroupRepository optionGroupRepository;
    private final OptionRepository optionRepository;

    /**
     * 옵션 생성
     *
     * @param optionGroupId : 옵션 그룹 ID
     * @param optionRequest : 옵션 이름과 옵션 가격이 담긴 DTO
     * @return OptionResponse : 옵션 이름과 옵션 가격이 담긴 DTO
     */
    @Transactional
    public OptionResponse create(long optionGroupId, OptionRequest optionRequest) {
        OptionGroup optionGroup = isValidOptionGroup(optionGroupId);

        Option newOption = Option.builder()
                .optionName(optionRequest.getOptionName())
                .optionPrice(optionRequest.getPrice())
                .optionGroup(optionGroup)
                .build();

        Option savedOption = optionRepository.save(newOption);

        return OptionResponse.entityToDto(savedOption);
    }

    /**
     * 옵션 그룹에 옵션이 존재하는지 확인 후
     * 옵션 업데이트
     *
     * @param optionGroupId : 옵션 그룹 ID
     * @param optionId      : 옵션 ID
     * @param optionRequest : 옵션 이름과 옵션 가격이 담긴 DTO
     * @return OptionResponse : 옵션 이름과 옵션 가격이 담긴 DTO
     */
    @Transactional
    public OptionResponse update(long optionGroupId, long optionId, OptionRequest optionRequest) {
        OptionGroup optionGroup = isValidOptionGroup(optionGroupId);

        Option option = isExistOption(optionId);

        if (!option.getOptionGroup().getId().equals(optionGroup.getId())) {
            throw new InvalidRequestException("옵션 그룹에 존재하지 않는 옵션입니다.");
        }

        option.update(
                optionRequest.getOptionName(),
                optionRequest.getPrice());

        return OptionResponse.entityToDto(option);
    }

    /**
     * 옵션 삭제
     *
     * @param optionId : 옵션 ID
     */
    @Transactional
    public void delete(long optionId) {
        Option option = isExistOption(optionId);

        optionRepository.delete(option);
    }

    /**
     * 옵션 그룹이 존재하는지 확인 후
     * 메뉴가 삭제되었는지 확인 후
     * 가게가 폐업중인 상태인지 확인
     *
     * @param optionGroupId : 옵션 그룹 ID
     * @return OptionGroup : 옵션 그룹 ENTITY
     */
    private OptionGroup isValidOptionGroup(long optionGroupId) {
        OptionGroup optionGroup = optionGroupRepository.findById(optionGroupId).orElseThrow(() ->
                new InvalidRequestException("존재하지 않는 옵션 그룹입니다."));

        if (!optionGroup.getMenu().getMenuStatus()) {
            throw new InvalidRequestException("삭제된 메뉴입니다.");
        }

        if (!optionGroup.getMenu().getStore().getStoreStatus()) {
            throw new InvalidRequestException("폐업한 가게입니다.");
        }

        return optionGroup;
    }

    /**
     * 옵션이 존재하는지 확인
     *
     * @param optionId : 옵션 ID
     * @return Option : 옵션 ENTITY
     */
    private Option isExistOption(long optionId) {
        return optionRepository.findById(optionId).orElseThrow(() ->
                new InvalidRequestException("존재하지 않는 옵션입니다."));
    }
}
