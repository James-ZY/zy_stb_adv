package com.gospell.aas.repository.mybatis.adv;


import java.util.Map;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.AdCategory;

@MyBatisRepository
public interface IAdStbDao extends BaseMybatisDao<AdCategory>{

	public Integer findUploadStringByAdStb(Map<String,Object> map);
}
