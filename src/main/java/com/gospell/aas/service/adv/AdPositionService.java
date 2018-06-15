package com.gospell.aas.service.adv;

 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import com.gospell.aas.dto.adv.AdPositionDTO;
import com.gospell.aas.dto.adv.Position;
import com.gospell.aas.dto.adv.PositionDTO;
import com.gospell.aas.entity.adv.AdPosition;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.repository.hibernate.adv.AdPositionDao;
import com.gospell.aas.repository.mybatis.adv.IAdPositionDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;

@Service
@Transactional(readOnly = true)
public class AdPositionService extends BaseService {

	@Autowired
	private AdPositionDao thisDao;
	@Autowired
	private IAdPositionDao mybatisDao;

	public AdPosition get(String id) {
		return thisDao.get(id);
	}

	public List<AdPosition> findAll() {
		return thisDao.findAll();
	}

	/**
	 * 根据条件查询广告位置展示形式
	 * 
	 * @param page
	 *            前台分页参数
	 * @param entity
	 * 
	 * @return
	 */
	public Page<AdPosition> find(Page<AdPosition> page, AdPosition entity) {
		if (StringUtils.isEmpty(page.getOrderBy())) {
			page.setOrderBy("p.position_id asc");
		}
		entity.setPage(page);
		List<AdPosition> list = mybatisDao.findList(entity);
		 
		if(null != list && list.size() >0){
			for (int i = 0; i < list.size(); i++) {
				String id = list.get(i).getAdType().getId();
				AdType type = AdTypeUtils.get(id);
				list.get(i).setAdType(type);
			}
		}
		page.setList(list);

		return page;
 
	}

	/**
	 * 保存广告位置
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void save(AdPosition entity) throws Exception {
		thisDao.clear();
		thisDao.save(entity);

	}

	/**
	 * 删除广告类型对应的广告位置信息
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void delete(AdPosition entity) throws ServiceException {
		String id = entity.getId();
		Map<String,Object> map = new HashMap<String,Object>();
		entity.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
		thisDao.save(entity);

	}

	public AdPosition findAdPositionById(String positionId) {
		return thisDao.findAdPositionById(positionId);
	}

	/**
	 * 查询广告套餐对应的广告类型对应的位置坐标
	 * 
	 * @param comboId
	 *            广告套餐Id
	 * @param status
	 *            0 标清 1 高清
	 * @return
	 * @throws ServiceException
	 */
	public AdPositionDTO findAdPostionByComboId(String comboId) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("status", AdPosition.POSITION_SD);
		map.put("id", comboId);
		List<AdPosition> sdPosition = mybatisDao.getPositionByComboId(map);
		map.put("status", AdPosition.POSITION_HD);
		List<AdPosition> hdPosition = mybatisDao.getPositionByComboId(map);
		AdPositionDTO dto = null;
		if (null != sdPosition && sdPosition.size() > 0) {
			dto = new AdPositionDTO();
			List<PositionDTO> sd = Lists.newArrayList();
			for (int i = 0; i < sdPosition.size(); i++) {
				PositionDTO d = new PositionDTO();
				AdPosition p = sdPosition.get(i);
				d.setBeginPointX(p.getBeginPointX());
				d.setBeginPointY(p.getBeginPointY());
				d.setEndPointX(p.getEndPointX());
				d.setEndPointY(p.getEndPointY());
				d.setId(p.getId());
				d.setPointId(p.getPointId());
				d.setPositionName(p.getPositionName());
				d.setStatus(p.getStatus());
				d.setVelocity(p.getVelocity());
				sd.add(d);
			}
			dto.setSdPosition(sd);
		}
		if (null != hdPosition && hdPosition.size() > 0) {
			if (dto == null) {
				dto = new AdPositionDTO();
			}
			List<PositionDTO> hd = Lists.newArrayList();
			for (int i = 0; i < hdPosition.size(); i++) {
				PositionDTO d = new PositionDTO();
				AdPosition p = hdPosition.get(i);
				d.setBeginPointX(p.getBeginPointX());
				d.setBeginPointY(p.getBeginPointY());
				d.setEndPointX(p.getEndPointX());
				d.setEndPointY(p.getEndPointY());
				d.setId(p.getId());
				d.setPointId(p.getPointId());
				d.setPositionName(p.getPositionName());
				d.setStatus(p.getStatus());
				d.setVelocity(p.getVelocity());
				hd.add(d);
			}
			dto.setHdPosition(hd);
		}
		
		return dto;

	}
	
	/**
	 * 查询广告套餐对应的广告类型对应的位置坐标
	 * 
	 * @param comboId
	 *            广告套餐Id
	 * @param status
	 *            0 标清 1 高清
	 * @return
	 * @throws ServiceException
	 */
	public Position findAdPostionByTypeId(String typeId,Integer resolution) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("status", resolution);
		map.put("id", typeId);
		List<AdPosition> position = mybatisDao.getPositionByTypeId(map);
		 
		 Position dto = null;
		if (null != position && position.size() > 0) {
			dto = new Position();
			List<PositionDTO> sd = Lists.newArrayList();
			for (int i = 0; i < position.size(); i++) {
				PositionDTO d = new PositionDTO();
				AdPosition p = position.get(i);
				d.setBeginPointX(p.getBeginPointX());
				d.setBeginPointY(p.getBeginPointY());
				d.setEndPointX(p.getEndPointX());
				d.setEndPointY(p.getEndPointY());
				d.setId(p.getId());
				d.setPointId(p.getPointId());
				d.setPositionName(p.getPositionName());
				d.setStatus(p.getStatus());
				d.setVelocity(p.getVelocity());
				sd.add(d);
			}
			dto.setPosition(sd);
		}
		 
		
		return dto;

	}
	 

}
