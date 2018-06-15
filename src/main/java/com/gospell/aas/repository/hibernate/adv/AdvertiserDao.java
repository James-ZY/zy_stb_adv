package com.gospell.aas.repository.hibernate.adv;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.Advertiser;

/**
 * 广告类型数据操作层
 * @author Administrator
 *
 */
@Repository
public class AdvertiserDao extends BaseDao<Advertiser> {

	  public Advertiser findAdvertiserById(String advertiserId){
		  return getByHql("from Advertiser where advertiserId = :p1 and delFlag = :p2", new Parameter(advertiserId,BaseEntity.DEL_FLAG_NORMAL));
	  }
	  
	  @Override
	public List<Advertiser> findAll(){
		  return find("from Advertiser where delFlag = :p1", new Parameter(BaseEntity.DEL_FLAG_NORMAL));
	  }
}
