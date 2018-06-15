package com.gospell.aas.repository.mybatis.adv;


import java.util.List;
import java.util.Map;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.AdPosition;

@MyBatisRepository
public interface IAdPositionDao  extends BaseMybatisDao<AdPosition>{

	
	/**
	 * 
	 * @param comboId 广告套餐Id
	 * @return
	 */
	List<AdPosition>  getPositionByComboId(Map<String,Object> map);
	
	/**
	 * 根据广告类型获取坐标
	 * @param Id
	 * @return
	 */
	List<AdPosition>  getPositionByTypeId(Map<String,Object> map);
	 
	/**
	 * 获取当前坐标下面有多少个广告，有广告就不允许删除这条坐标
	 * @param map
	 * @return
	 */
    public Integer getPotionContianAdvCount(Map<String,Object> map);
}
