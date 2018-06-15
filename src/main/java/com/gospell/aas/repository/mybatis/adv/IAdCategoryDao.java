package com.gospell.aas.repository.mybatis.adv;


import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.AdCategory;

@MyBatisRepository
public interface IAdCategoryDao extends BaseMybatisDao<AdCategory>{
	public Integer findMaxAdCategoryId();
}
