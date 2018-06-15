package com.gospell.aas.repository.hibernate.adv;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdDefaultControll;

/**
 * 素材操作层
 * @author Administrator
 *
 */
@Repository
public class AdDefaultControllDao extends BaseDao<AdDefaultControll> {

	public List<AdDefaultControll> getAdDefaultControllByIds(String[] ids){
		return find("from AdDefaultControll where delFlag = :p1 and id in (:p2)", new Parameter(BaseEntity.DEL_FLAG_NORMAL,ids));
	}
	 
}
