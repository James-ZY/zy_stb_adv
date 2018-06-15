package com.gospell.aas.repository.hibernate.adv;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdOperatorsDistrict;

/**
 * 频道数据操作层
 * 
 * @author Administrator
 * 
 */
@Repository
public class AdOperatorsDistrictDao extends BaseDao<AdOperatorsDistrict> {

	public void deleteOpDis(String operatorsId) {
		update("delete AdOperatorsDistrict where operators.id = :p1",
				new Parameter(operatorsId));
	}

	public List<String> getOperatorsByDis(String disId) {
		String[] dis = disId.split("-");
		StringBuffer hql = new StringBuffer();
		String[] p = new String[dis.length];
		hql.append("select DISTINCT operators.id  from AdOperatorsDistrict where district.id like :p1");
		p[0] = dis[0] + "%";
		if (dis.length > 1) {
			for (int i = 1; i < dis.length; i++) {
				hql.append(" or district.id like :p" + (i + 1));
				p[i] = dis[i] + "%";
			}
		}
		return find(hql.toString(), new Parameter(p));
	}
	
}
