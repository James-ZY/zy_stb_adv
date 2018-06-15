package com.gospell.aas.repository.hibernate.adv;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdChannel;

/**
 * 频道数据操作层
 * 
 * @author Administrator
 *
 */
@Repository
public class AdChannelDao extends BaseDao<AdChannel> {
	

//	public List<AdChannel> findAdChannelByIds(List<String> adTypeIds,String networkId){
//		 return find("from AdChannel where channelId in (:p1) and adNetWork.networkId =:p2 and delFlag = :p3", new Parameter(adTypeIds,networkId,AdType.DEL_FLAG_NORMAL));
//	 }
	
	public AdChannel findAdChannelByChannelId(String channleId,String networkId){
		 return getByHql("select a from AdChannel a left join fetch a.adNetWork n where  a.delFlag = :p1 and a.channelId =:p2 and n.networkId =:p3 ", new Parameter(BaseEntity.DEL_FLAG_NORMAL,channleId,networkId));
	 }

	public List<AdChannel> findAdChannelByNetworkId(String networkId) {
		return find("from AdChannel where  adNetWork.networkId =:p1 and delFlag = :p2",
				new Parameter( networkId, BaseEntity.DEL_FLAG_NORMAL));
	}
 
}
