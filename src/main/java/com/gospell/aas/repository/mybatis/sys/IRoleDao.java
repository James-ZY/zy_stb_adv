package com.gospell.aas.repository.mybatis.sys;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.sys.Office;
import com.gospell.aas.entity.sys.Role;

@MyBatisRepository
public interface IRoleDao extends BaseMybatisDao<Role> {

	int getUserRoleNum(@Param(value ="id") String id);
	
	/**
	 * 用户添加的时候通过用户选择的广告商进行角色的选择
	 * @param list
	 * @return
	 */
	List<Role> getRoleByOfficeId(Office office) throws Exception;
	
}
