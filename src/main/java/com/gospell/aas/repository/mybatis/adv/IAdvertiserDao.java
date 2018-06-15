package com.gospell.aas.repository.mybatis.adv;


import java.util.List;
import java.util.Map;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.Advertiser;

@MyBatisRepository
public interface IAdvertiserDao extends BaseMybatisDao<Advertiser> {

	/**
	 * 获取当前广告商用户下具有哪些用户ID
	 * @param advertiser
	 * @return
	 */
		Integer findUserIdByAdvertiserId(Advertiser advertiser) throws Exception;
	 /**
	  * <!-- 查询广告商购买了哪些广告套餐，当前用户判断是否可以删除广告商，所以只查询一条数据提高效率 -->
	  * @param advertiser
	  * @return
	  */
	 String findComboIdByAdvertiserId(Advertiser advertiser);
	 /**
	  * 页面需要广告商的时候查询这个方法，用hibernate的太慢
	  * @param adv
	  * @return
	  */
	 public List<Advertiser> findAllAdvertiser(Advertiser adv); 
	 /**
	  * 通过广告商ID查询广告商
	  * @param idList
	  * @return
	  */
	 public List<Advertiser> findAdvertiserByAdvId(Map<String,Object> map);
	 /**
	  * 通过一个广告商ID查询广告商
	  * @param advId
	  * @return
	  */
	 public Advertiser findSingelAdvertiserByAdvId(Map<String,Object> map);
	 
	 public Integer findMaxAdvertiserId();
 	
}
