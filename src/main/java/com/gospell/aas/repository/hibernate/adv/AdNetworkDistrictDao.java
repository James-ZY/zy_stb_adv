package com.gospell.aas.repository.hibernate.adv;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdNetworkDistrict;

/**
 * 频道数据操作层
 * 
 * @author Administrator
 * 
 */
@Repository
public class AdNetworkDistrictDao extends BaseDao<AdNetworkDistrict> {

	public void deleteNtwDis(String networkId) {
		update("delete AdNetworkDistrict where adNetwork.id = :p1",
				new Parameter(networkId));
	}

	public List<AdNetworkDistrict> getNetDis(String networkId) {
	    return  find("from AdNetworkDistrict where adNetwork.id = :p1 order by district.id",
					new Parameter(networkId));
	}
	
	public int updateNetwork(String networkId,String districtId,String selfDisId){
		return update("update AdNetworkDistrict set selfDistrictId = :p1 where adNetwork.id = :p2 and district.id = :p3",new Parameter(selfDisId,networkId,districtId));
	}
	
	public List<AdNetworkDistrict> getNetsByParam(String disId,String networkId) {
		String[] dis = disId.split("-");
		StringBuffer hql = new StringBuffer();
		String[] p = new String[dis.length+1];
		hql.append("from AdNetworkDistrict where adNetwork.id = :p1 ");
		p[0] = networkId;
		if(dis.length ==1){
			hql.append(" and district.id like :p2");	
			p[1] = dis[0] + "%";
		}
		if (dis.length > 1) {
			hql.append(" and ( district.id like :p2");
			p[1] = dis[0] + "%";
			for (int i = 1; i < dis.length; i++) {
				hql.append(" or district.id like :p" + (i + 2));
				p[i+1] = dis[i] + "%";
			}
			hql.append(")");
		}
		return find(hql.toString(), new Parameter(p));
	}

}
