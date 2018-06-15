package com.gospell.aas.service.adv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.IdGen;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import com.gospell.aas.dto.adv.AdRangeDTO;
import com.gospell.aas.entity.adv.AdRange;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.repository.hibernate.adv.AdRangeDao;
import com.gospell.aas.repository.mybatis.adv.IAdRangeDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;

@Service
@Transactional(readOnly = true)
public class AdRangeService extends BaseService {

	@Autowired
	private AdRangeDao thisDao;
	@Autowired
	private IAdRangeDao mybatisDao;

	public AdRange get(String id) {
		return thisDao.get(id);
	}

	public List<AdRange> findAll() {
		return thisDao.findAll();
	}

	/**
	 * 根据条件查询坐标范围
	 * 
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            广告发送器
	 * @return
	 */
	public Page<AdRange> find(Page<AdRange> page, AdRange entity) {
		page.setOrderBy(" r.ad_type_id asc,r.flag asc");
		entity.setPage(page);
		List<AdRange> list = mybatisDao.findList(entity);
		 
		if(null != list && list.size() >0){
			for (int i = 0; i < list.size(); i++) {
				String id = list.get(i).getType().getId();
				AdType type = AdTypeUtils.get(id);
				list.get(i).setType(type);
			}
		}
		page.setList(list);
		return page;
	}

	/**
	 * 保存坐标范围
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void save(AdRange entity) throws Exception {
		entity.setCreateBy(UserUtils.getUser());
		entity.setId(IdGen.uuid());
		mybatisDao.save(entity);
		UserUtils.removeCache(UserUtils.CACHE_ADRANGE_LIST);

	}

	@Transactional(readOnly = false)
	public void update(AdRange entity) throws Exception {
		entity.setUpdateBy(UserUtils.getUser());
		mybatisDao.update(entity);
		UserUtils.removeCache(UserUtils.CACHE_ADRANGE_LIST);
	}

	@Transactional(readOnly = false)
	public void delete(String id) throws Exception {
		mybatisDao.deleteById(id);
		UserUtils.removeCache(UserUtils.CACHE_ADRANGE_LIST);
	}

	/**
	 * 删除坐标范围
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void delete(AdRange entity) throws Exception {
		entity.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
		thisDao.save(entity);
		UserUtils.removeCache(UserUtils.CACHE_ADRANGE_LIST);

	}

	/**
	 * 保存坐标范围
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void saveStatus(AdRange entity) throws Exception {

		/**
		 * 启用时应该把当前相同广告类型相同分辨率已启用的置为不启用
		 */
		if (AdRange.RANGE_START_STAUTS == entity.getStatus()) {
		/*	if (null != entity.getType()) {
				String typeId = entity.getType().getId();
				Integer flag = entity.getFlag();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("typeId", typeId);
				map.put("flag", flag);
				map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
				map.put("status", AdRange.RANGE_END_STATUS);
				mybatisDao.updateRangeStatus(map);
			}*/
			update(entity);
		} else {
			thisDao.save(entity);
		}

	}
	
	/**
	 * 根据广告类型获取广告坐标的范围
	 * @param typeId
	 * @param flag
	 * @return
	 */
	public List<AdRange> getAdRanges(String typeId,Integer flag){
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		 map.put("typeId", typeId);
		 map.put("flag", flag);
		 map.put("status", AdRange.RANGE_START_STAUTS);
		 List<AdRange> range = mybatisDao.getUseInAdRange(map);
		 return range;
	}
	
	/**
	 * 根据广告类型获取广告坐标的范围
	 * @param typeId
	 * @param flag
	 * @return
	 */
	public List<AdRangeDTO> getUseInAdRange(String typeId,Integer flag){
		List<AdRangeDTO> list = new ArrayList<AdRangeDTO>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("typeId", typeId);
		map.put("flag", flag);
		map.put("status", AdRange.RANGE_START_STAUTS);
		List<AdRange> range = mybatisDao.getUseInAdRange(map);
		for (AdRange adRange : range) {
			AdRangeDTO dto = this.rangeToDto(adRange);
			list.add(dto);
		}
		return list;
	}
	
	public AdRangeDTO rangeToDto(AdRange adRange){
		AdRangeDTO dto = new AdRangeDTO();
		 dto.setId(adRange.getId());
		 dto.setRangeName(adRange.getRangeName());
		 dto.setBeginX(adRange.getBeginX());
		 dto.setBeginY(adRange.getBeginY());
		 dto.setEndX(adRange.getEndX());
		 dto.setEndY(adRange.getEndY());
		 dto.setFlag(adRange.getFlag());
		 dto.setRange(adRange.getRangeName()+"("+adRange.getBeginX()+","+adRange.getBeginY()+")~("+adRange.getEndX()+","+adRange.getEndY()+")");
		return dto;
		
	}

}
