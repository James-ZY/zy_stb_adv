package com.gospell.aas.service.adv;

import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.IdGen;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import com.gospell.aas.dto.adv.AdTrackDTO;
import com.gospell.aas.entity.adv.AdTrack;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.repository.hibernate.adv.AdTrackDao;
import com.gospell.aas.repository.mybatis.adv.IAdTrackDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AdTrackService extends BaseService {

	@Autowired
	private AdTrackDao thisDao;
	@Autowired
	private IAdTrackDao mybatisDao;

	public AdTrack get(String id) {
		return thisDao.get(id);
	}

	public List<AdTrack> findAll() {
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
	public Page<AdTrack> find(Page<AdTrack> page, AdTrack entity) {
		page.setOrderBy(" r.ad_type_id asc,r.flag asc");
		entity.setPage(page);
		List<AdTrack> list = mybatisDao.findList(entity);
		 
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
	 * 保存轨迹
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void save(AdTrack entity) throws Exception {
		entity.setCreateBy(UserUtils.getUser());
		entity.setId(IdGen.uuid());
		mybatisDao.save(entity);
		UserUtils.removeCache(UserUtils.CACHE_ADTRACK_LIST);

	}

	@Transactional(readOnly = false)
	public void update(AdTrack entity) throws Exception {
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
	public void delete(AdTrack entity) throws Exception {
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
	public void saveStatus(AdTrack entity) throws Exception {

		/**
		 * 启用时应该把当前相同广告类型相同分辨率已启用的置为不启用
		 */
		if (AdTrack.TRACK_START_STAUTS == entity.getStatus()) {
		/*	if (null != entity.getType()) {
				String typeId = entity.getType().getId();
				Integer flag = entity.getFlag();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("typeId", typeId);
				map.put("flag", flag);
				map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
				map.put("status", AdTrack.RANGE_END_STATUS);
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
	public List<AdTrack> getAdTracks(String typeId, Integer flag){
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		 map.put("typeId", typeId);
		 map.put("flag", flag);
		 map.put("status", AdTrack.TRACK_START_STAUTS);
		 List<AdTrack> range = mybatisDao.getUseInAdTrack(map);
		 return range;
	}
	
	/**
	 * 根据广告类型获取广告坐标的范围
	 * @param typeId
	 * @param flag
	 * @return
	 */
	public List<AdTrackDTO> getUseInAdTrack(String typeId, Integer flag){
		List<AdTrackDTO> list = new ArrayList<AdTrackDTO>();
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		 map.put("typeId", typeId);
		 map.put("flag", flag);
		 map.put("status", AdTrack.TRACK_START_STAUTS);
		 List<AdTrack> trackList = mybatisDao.getUseInAdTrack(map);
		 for (AdTrack adTrack	 : trackList) {
			 AdTrackDTO dto = this.rangeToDto(adTrack);
			 list.add(dto); 
		}
		 return list;
	}
	
	public AdTrackDTO rangeToDto(AdTrack adTrack){
		AdTrackDTO dto = new AdTrackDTO();
		 dto.setId(adTrack.getId());
		 dto.setTrackName(adTrack.getTrackName());
		 dto.setCoordinates(adTrack.getCoordinates());
		 dto.setBgWidth(adTrack.getBgWidth());
		 dto.setBgHeight(adTrack.getBgHeight());
		 dto.setShowTime(adTrack.getShowTime());
		 dto.setFlag(adTrack.getFlag());
		 dto.setTrack(adTrack.getTrackName()+"("+adTrack.getBgWidth()+"x"+adTrack.getBgHeight()+")");
     	return dto;
		
	}

}
