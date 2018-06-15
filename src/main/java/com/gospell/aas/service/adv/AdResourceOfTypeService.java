package com.gospell.aas.service.adv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import com.gospell.aas.dto.adv.AdResourceOfTypeDTO;
import com.gospell.aas.entity.adv.AdResourceOfType;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.repository.hibernate.adv.AdResourceOfTypeDao;
import com.gospell.aas.repository.mybatis.adv.IAdResourceOfTypeDao;
import com.gospell.aas.service.BaseService;

@Service
@Transactional(readOnly = true)
public class AdResourceOfTypeService extends BaseService {

	@Autowired
	private AdResourceOfTypeDao thisDao;

	@Autowired
	private IAdResourceOfTypeDao mybatisDao;

	public AdResourceOfType get(String id) {
		return thisDao.get(id);
	}

	public Page<AdResourceOfType> findAll(Page<AdResourceOfType> page,
			AdResourceOfType entity) {
		if (org.apache.commons.lang3.StringUtils.isEmpty(page.getOrderBy())) {
			page.setOrderBy("a.enable desc,t.id");
		}
		entity.setPage(page);
		List<AdResourceOfType> list = mybatisDao.findAll(entity);
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				AdType adtype = list.get(i).getAdType();
				AdTypeUtils.getLocaleAdType(adtype);

			}
		}
		page.setList(list);
		return page;
	}

	/**
	 * 广告素材上传获取对应的尺寸大小
	 * 
	 * @param typeId
	 * @param resolution
	 * @return
	 */
	public List<AdResourceOfTypeDTO> findAdResourceOfTypeByTypeId(
			String typeId, Integer resolution,String rollFlag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("typeId", typeId);
		map.put("flag",resolution);			
		if(typeId.equals(AdType.TYPE_ROLL_ADV_ID)){
			map.put("rollFlag", rollFlag);
		}
		List<AdResourceOfType> list= mybatisDao.findByTypeId(map);
 
		List<AdResourceOfTypeDTO> return_list = Lists.newArrayList();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				AdResourceOfType a = list.get(i);
				AdResourceOfTypeDTO d = new AdResourceOfTypeDTO();
				d.setFileMaxSize(a.getFileMaxSize());
				d.setFileMinSize(a.getFileMinSize());
				d.setFlag(a.getFlag());
				d.setFormat(a.getFormat());
				d.setFrameMax(a.getFrameMax());
				d.setFrameMin(a.getFrameMin());
				d.setHighMax(a.getHighMax());
				d.setHighMin(a.getHighMin());
				d.setWidthMin(a.getWidthMin());
				d.setWidthMax(a.getWidthMax());
				d.setRateMax(a.getRateMax());
				d.setRateMin(a.getRateMin());
				if (null != a.getWidthMax() && null != a.getWidthMin()
						&& null != a.getHighMax() && null != a.getHighMin()) {
					int widthMax = a.getWidthMax();
					int widthMin = a.getWidthMin();
					int highMax = a.getHighMax();
					int highMin = a.getHighMin();
					if (widthMax == widthMin && highMax == highMin) {
						d.setIsFixed(1);
					} else {
						d.setIsFixed(0);
					}
				} else {
					d.setIsFixed(0);
				}
				AdType t = a.getAdType();
				d.setStatus(t.getStatus());
				return_list.add(d);
			}
		}
		return return_list;
	}

	/**
	 * 保存
	 */
	@Transactional(readOnly = false)
	public void save(AdResourceOfType entity) {
		thisDao.save(entity);
	}

	/**
	 * 保存
	 */
	@Transactional(readOnly = false)
	public void delete(String id) {
		thisDao.deleteById(id);
	}
}
