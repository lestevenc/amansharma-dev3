package com.simon.service;

import com.github.pagehelper.PageInfo;
import com.simon.common.service.BasicService;
import com.simon.dto.DictTypeDto;
import com.simon.dto.EasyUiTreeGridDto;
import com.simon.model.DictTypeGroup;

import java.util.List;

/**
* @author SimonSun
* @date 2018-09-06 10:03:50
**/
public interface DictTypeGroupService extends BasicService<DictTypeGroup, Long> {
    List<DictTypeDto> getDtos(String language, Integer limit, Integer offset);

    PageInfo<EasyUiTreeGridDto> getTreeGridDtos(String name, String code, String language, Integer pageNo, Integer pageSize, String orderBy);

    DictTypeGroup save(DictTypeDto dictTypeDto, String language);

    int countByTypeGroupCode(String typeGroupCode);

    /**
     * 根据id返回字典dto
     * @param id 字典组id
     * @param language 语言
     * @return 字典dto
     */
    DictTypeDto getDtoById(Long id, String language);

    /**
     * 根据id删除记录
     * @param id
     * @return
     */
    int deleteById(Long id);
}