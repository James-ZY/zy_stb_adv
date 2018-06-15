package com.gospell.aas.repository.mybatis.adv;


import org.apache.ibatis.annotations.Param;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.AdDistrictCategory;

@MyBatisRepository
public interface IAdDistrictCategoryDao extends BaseMybatisDao<AdDistrictCategory>{
	public Integer findMaxAdDistrictCategoryId(@Param(value="parentId") String parentId);
}
