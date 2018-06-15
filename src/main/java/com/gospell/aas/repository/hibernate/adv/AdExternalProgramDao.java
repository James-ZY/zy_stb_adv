package com.gospell.aas.repository.hibernate.adv;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdExternalProgram;

/**
 * 外部程序数据操作层
 * @author Administrator
 *
 */
@Repository
public class AdExternalProgramDao extends BaseDao<AdExternalProgram> {

	 public AdExternalProgram findProgramByProgramId(String programId){
		 return getByHql("from AdExternalProgram where delFlag = :p1 and programId = :p2",
				 new Parameter(BaseEntity.DEL_FLAG_NORMAL,programId));
	 }
	 
	 public List<AdExternalProgram> findProgramByProgramName(String programName){
		 return find("from AdExternalProgram where delFlag = :p1 and programName like :p2 order by programVersion desc",
				 new Parameter(BaseEntity.DEL_FLAG_NORMAL,"%"+programName+"%"));
	 }
}
