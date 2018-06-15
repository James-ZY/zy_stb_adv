package com.gospell.aas.service.adv;

import java.util.ArrayList;
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
import com.gospell.aas.common.utils.IdGen;
import com.gospell.aas.common.utils.adv.RegexUtil;
import com.gospell.aas.dto.adv.AdNetWorkDTO;
import com.gospell.aas.dto.adv.AdOperatorsDTO;
import com.gospell.aas.entity.adv.AdChannel;
import com.gospell.aas.entity.adv.AdDistrictCategory;
import com.gospell.aas.entity.adv.AdNetwork;
import com.gospell.aas.entity.adv.AdOperators;
import com.gospell.aas.entity.adv.AdOperatorsDistrict;
import com.gospell.aas.repository.hibernate.adv.AdOperatorsDao;
import com.gospell.aas.repository.hibernate.adv.AdOperatorsDistrictDao;
import com.gospell.aas.repository.mybatis.adv.IAdOperatorsDao;
import com.gospell.aas.repository.mybatis.adv.INetworkDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;

@Service
@Transactional(readOnly = true)
public class AdOperatorsService extends BaseService {

	@Autowired
	private AdOperatorsDao thisDao;
	
	@Autowired
	private IAdOperatorsDao mybatisDao;

	@Autowired
	private AdChannelService channelService;
	
	@Autowired
	private INetworkDao networkDao;
	
	@Autowired
	private AdOperatorsDistrictDao adOperatorsDistrictDao;

	public AdOperators get(String id) {
		return thisDao.get(id);
	}

	public List<AdOperators> findAll() {
		AdOperators a = new AdOperators();
		return mybatisDao.findAll(a);
	}
	
	public void clear(){
		thisDao.clear();
	}

	/**
	 * 根据条件查询电视运营商列表
	 * 
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            电视运营商实体
	 * @return
	 */
	public Page<AdOperators> find(Page<AdOperators> page, AdOperators entity) {
		 page.setOrderBy("a.ad_operators_id desc");
		 entity.setPage(page);
		 List<AdOperators> list = mybatisDao.findList(entity);
		
		 page.setList(list);
		return page;
	}

	/**
	 * 保存电视运营商信息
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void save(AdOperators entity) throws ServiceException {
		thisDao.clear();
		String selArea = entity.getSelArea();
		adOperatorsDistrictDao.deleteOpDis(entity.getId());
		if(StringUtils.isNotBlank(selArea)){
			
			String[] list = selArea.split("-");
			List<AdOperatorsDistrict> adDistrictCategorys = new ArrayList<AdOperatorsDistrict>();
			for (String string : list) {
				AdOperatorsDistrict aod = new AdOperatorsDistrict();
				aod.setId(IdGen.uuid());
				aod.setOperators(entity);
				aod.setDistrict(new AdDistrictCategory(string.split(":")[0]));
				if(string.split(":").length>1){
					aod.setSelfDistrictId(string.split(":")[1]);					
				}
				adDistrictCategorys.add(aod);
			}
			adOperatorsDistrictDao.save(adDistrictCategorys);
		}
		
		thisDao.save(entity);
	}
	
	/**
	 * 保存电视运营商信息
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void saveList(List<AdOperators> entityList) throws Exception {
		thisDao.save(entityList);
	}


	/**
	 * 通过运营商ID获取电视运营商信息
	 * 
	 * @param operatorsId
	 * @return
	 */
	public AdOperators findByOperatorsId(String operatorsId) {
		return thisDao.findByOperatorsId(operatorsId);
	}

	/**
	 * 通过运营商ID获取电视运营商信息
	 * 
	 * @param operatorsId
	 * @return
	 */
	public AdOperators findByOperators(String operatorsId, String password) {
		return thisDao.findByOperators(operatorsId, password);
	}

	@Transactional(readOnly = false)
	public void delete(AdOperators entity) throws ServiceException {
		List<AdNetwork> networks = entity.getNetworkList();
		if (null != networks && networks.size()>0) {
			for (AdNetwork adNetwork : networks) {
				adNetwork.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
				List<AdChannel> list = adNetwork.getChannelList();
				if (null != list && list.size() > 0) {
					channelService.deleteList(list);
				}
			}
		}
		entity.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
	}

	
	public List<AdOperatorsDTO> getAdOperatorsDTO() {
		List<AdOperatorsDTO> list = Lists.newArrayList();
		List<AdOperators> allList = findAll();
		if (null != allList && allList.size() > 0) {
			for (int i = 0; i < allList.size(); i++) {
		
				AdOperators a = allList.get(i);
				List<AdNetwork> netl = a.getNetworkList();
				AdOperatorsDTO dto = new AdOperatorsDTO();
				dto.setOperatorsId(a.getId());
				dto.setOperatorsName(a.getOperatorsName());
				List<AdNetWorkDTO> networkList = new ArrayList<AdNetWorkDTO>();
				if(null != netl && netl.size()>0){
					for (AdNetwork adNetwork : netl) {
						if(null == adNetwork || adNetwork.getStatus() == AdNetwork.NETWORK_NO_STATUS){
							continue;
						}
						AdNetWorkDTO ntdto = new AdNetWorkDTO();
						ntdto.setNetworkId(adNetwork.getId());
						ntdto.setNetworkName(adNetwork.getNetworkName());
						networkList.add(ntdto);
					}
					dto.setNetworkList(networkList);
				}
/*				
				if(null == a.getNetwork() || a.getNetwork().getStatus() == AdNetwork.NETWORK_NO_STATUS){
					continue;
				}
					AdOperatorsDTO dto = new AdOperatorsDTO();
					dto.setOperatorsId(a.getId());
					dto.setOperatorsName(a.getOperatorsName());
					dto.setNetworkId(a.getNetwork().getId());
					dto.setNetworkName(a.getNetwork().getNetworkName());
				 */
				list.add(dto);
			}
			
		}
		return list;
	}
	
	/**
	 * 判断是否可以删除电视运营商
	 * 条件：如果电视运营商下有广告发送器则不可以删除
	 * @param entity
	 * @return
	 */
	public Boolean isCanDeleteOperator(AdOperators entity){
		AdNetwork network = networkDao.getAdNetWorkByOpreators(entity);
		if(null == network){
			return true;
		}
		return false;
	}
	
	/**
	 * 检查导入的数据字段是否为null  
	 * 
	 * @param list
	 * @return
	 */
	public List<AdOperators> checkImportAdOperatorsIsNul(List<AdOperators> list) {
		  
		List<AdOperators> availableList = Lists.newArrayList();
		for (AdOperators adOperators : list) {
			
			String operatorsId = adOperators.getOperatorsId();// 运营商Id
			String name = adOperators.getOperatorsName(); // 营业执照注册号
			String password = adOperators.getPassword();
			String contact = adOperators.getContact();
			String mobilePhone = adOperators.getMobilephone();
 
			
			if(StringUtils.isBlank(operatorsId) || StringUtils.isBlank(name) ||
					StringUtils.isBlank(password) || StringUtils.isBlank(contact) 
					||StringUtils.isBlank(mobilePhone)  ){
				 
			}else{
				availableList.add(adOperators);
			}
		}
		return availableList;

	}
	/**
	 * 导入的是空白数据
	 * @param list
	 * @return
	 */
	public boolean checkImportAdOperatorsIsNotData(List<AdOperators> list) {
		int i =0;
		for (AdOperators adOperators : list) {
			
			String operatorsId = adOperators.getOperatorsId();// 运营商Id
			String name = adOperators.getOperatorsName(); // 营业执照注册号
			String password = adOperators.getPassword();
			String contact = adOperators.getContact();
			String mobilePhone = adOperators.getMobilephone();
			String telPhone = adOperators.getTelphone();
			String area = adOperators.getArea();
			String number = adOperators.getNumber();
			if(StringUtils.isBlank(operatorsId) || StringUtils.isBlank(name) ||
					StringUtils.isBlank(password) || StringUtils.isBlank(contact) 
					||StringUtils.isBlank(mobilePhone) 	||StringUtils.isBlank(telPhone)
					||StringUtils.isBlank(area)
					||StringUtils.isBlank(number)){
				 i++;
			} 
		}
		if(i == list.size()){
			return false;
		}
		return true;

	}
	
	/**
	 * 导入的广告商数据的电话，手机是否正确，验证广告商类型
	 * @param list
	 * @return
	 */
	public List<AdOperators>  checkImportAdOperatorsDataFormat(List<AdOperators> list) {
		List<AdOperators> formatList = Lists.newArrayList();
		for (AdOperators adOperators : list) {
			
			 String number = adOperators.getNumber();
			 if(StringUtils.isNotBlank(number)){//验证类型
				 boolean b_number = RegexUtil.checkOperatorNumber(number);
				if(!b_number){
					continue;
				}
						 
			 }
			 String phone= adOperators.getTelphone();
			 String mobile = adOperators.getMobilephone();
			 boolean b = RegexUtil.isTelPhone(phone);
			 boolean b1 = RegexUtil.isMobilePhone(mobile);
			 if(b && b1){
				 formatList.add(adOperators);
			 }
			 
		}
		 
 	 return formatList;

	}
	
	/**
	 * 如果excel中有重复的电视运营商ID，默认只取第一条
	 * @param list
	 * @return
	 */
	public List<AdOperators>   distinctAdvtiserId(List<AdOperators> list) {
	 
		Map<String,AdOperators> map = new HashMap<String,AdOperators>();
		List<AdOperators> distinctList = Lists.newArrayList();
		for (AdOperators a : list) {
			 String advId= a.getOperatorsId();
			 if(!map.containsKey(advId)){
				 map.put(advId, a);
			 }
		}
		
		for (String key:map.keySet()) {
			distinctList.add(map.get(key));
		}
 	  return distinctList;

	}
	/**
	 * 数据库中已经存在的电视运营商ID，需要排除
	 * @return
	 */
	public List<AdOperators> databaseAlreadyExist(List<AdOperators> list){
		Map<String,AdOperators> map = new HashMap<String,AdOperators>();
		List<AdOperators> norepeatList = Lists.newArrayList();
		List<String> id_list = Lists.newArrayList();
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		for (AdOperators adOperators : list) {
			 String advId= adOperators.getOperatorsId();
			 map.put(advId, adOperators);
			 id_list.add(advId);
		}
		queryMap.put("idList", id_list);
		List<AdOperators> queryList = mybatisDao.findAdOperatorsByOpId(queryMap);
		if (queryList !=null && queryList.size() >0) {
			for (int i = 0; i < queryList.size(); i++) {
				String id = queryList.get(i).getOperatorsId();
				if(map.containsKey(id)){
					map.remove(id);
				}
			}
		}
		if(!map.isEmpty()){
			for (String key : map.keySet()) {
				norepeatList.add(map.get(key));
			}
		}
		return norepeatList;
	}
	/**
	 * 
	 * @param list
	 * @return
	 */
	public int compareListSize(List<AdOperators> sourceList,List<AdOperators> newList) {
		 int size = sourceList.size();
		 int newSize = newList.size();
		 int compare = size-newSize;
		 return compare;

	}
	
	 public synchronized Integer findMaxAdOperatorId(){
		return mybatisDao.findMaxAdOperatorId();
		 
	 }

}
