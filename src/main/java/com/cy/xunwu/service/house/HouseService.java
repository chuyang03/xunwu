package com.cy.xunwu.service.house;

import com.cy.xunwu.web.dto.HouseDTO;
import com.cy.xunwu.web.dto.ServiceResult;
import com.cy.xunwu.web.form.HouseForm;

/**
 * 房屋管理服务接口
 */
public interface HouseService {

    ServiceResult<HouseDTO> save(HouseForm houseForm);
}
