package com.gospell.aas.service.adv;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.security.SystemAuthorizingRealm;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.adv.AdNetWorkUtils;
import com.gospell.aas.dto.adv.*;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.adv.AdDistrictCategory;
import com.gospell.aas.entity.adv.AdDistrictCategory.TypeEnum;
import com.gospell.aas.entity.adv.AdNetwork;
import com.gospell.aas.entity.adv.AdOperators;
import com.gospell.aas.entity.adv.AdOperatorsDistrict;
import com.gospell.aas.entity.sys.SysParam;
import com.gospell.aas.repository.hibernate.adv.AdDistrictCategoryDao;
import com.gospell.aas.repository.hibernate.adv.AdNetworkDistrictDao;
import com.gospell.aas.repository.hibernate.adv.AdOperatorsDao;
import com.gospell.aas.repository.hibernate.adv.AdOperatorsDistrictDao;
import com.gospell.aas.repository.mybatis.adv.IAdDistrictCategoryDao;
import com.gospell.aas.repository.mybatis.adv.INetworkDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;
import com.gospell.aas.service.sys.SysParamService;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class AdDistrictCategoryService extends BaseService{

	@Autowired
	private AdDistrictCategoryDao thisDao;

	@Autowired
	private IAdDistrictCategoryDao categoryDao;

	@Autowired
	private SystemAuthorizingRealm systemRealm;

	@Autowired
	private AdOperatorsDao adOperatorsDao;

	@Autowired
	private AdOperatorsDistrictDao adOperatorsDistrictDao;

	@Autowired
	private AdNetworkDistrictDao adNetworkDistrictDao;

	@Autowired
	private SysParamService sysParamService;

	@Autowired
	private INetworkDao adNetworkDao;

	@Autowired
	private IAdDistrictCategoryDao adDistrictCategoryDao;


	//private Logger logger = LoggerFactory.getLogger(AdDistrictCategory.class);

	public AdDistrictCategory get(String id) {
		String type = getCurrentSysAreaType();
		List<AdDistrictCategory> list= UserUtils.getDisCateGoryList(type);
		if(null != list && list.size()>0){
			for (int i = 0; i <list.size(); i++) {
				AdDistrictCategory category = list.get(i);
				if(id != null && id.equals(category.getId())){
					return category;
				}
			}
		}
		return null;
	}


	public List<AdDistrictCategory> findAll() {
		return thisDao.findAll();
	}

	public List<AdDistrictCategory> findAllByParent(String type) {
		return thisDao.findAllByParent(AdDistrictCategory.getzeroAdCategoryId(type),type);
	}

	public List<AdDistrictCategory> findAllAdDistrictCategory() {
		//读取系统区域参数配置
		String type = getCurrentSysAreaType();
		return UserUtils.getDisCateGoryList(type);
	}

	/**
	 * 根据条件查询广告类型
	 *
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            广告发送器
	 * @return
	 */
	public Page<AdDistrictCategory> find(Page<AdDistrictCategory> page, AdDistrictCategory entity) {
		DetachedCriteria dc = thisDao.createDetachedCriteria();
		if (StringUtils.isNotBlank(entity.getCategoryId())) {
			dc.add(Restrictions.like("categoryId", "%" + entity.getCategoryId() + "%"));
		}
		if (StringUtils.isNotBlank(entity.getCategoryName())) {
			dc.add(Restrictions.like("categoryName", "%" + entity.getCategoryName()
					+ "%"));
		}
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("categoryId"));
		return thisDao.find(page, dc);
	}

	/**
	 * 保存广告类型
	 *
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void save(AdDistrictCategory entity) throws Exception {

		// thisDao.save(entity);

		entity.setParent(this.get(entity.getParent().getId()));
		if (StringUtils.isBlank(entity.getId())) {

			entity.setId(entity.getCategoryId());

			entity.setCreateBy(UserUtils.getUser());
			entity.setCreateDate(new Date());
		}/*else{
			String id =entity.getId();
			if(!id.equals(entity.getCategoryId())){
				entity.setId(entity.getCategoryId());
			}
		}*/
		String oldParentIds = entity.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
		entity.setParentIds(entity.getParent().getParentIds()
				+ entity.getParent().getId() + ",");
		thisDao.clear();
		thisDao.save(entity);
		// 更新子节点 parentIds
		String type = getCurrentSysAreaType();
		List<AdDistrictCategory> list = thisDao.findByParentIdsLike("%," + entity.getId()
				+ ",%",type);
		for (AdDistrictCategory e : list) {
			e.setParentIds(e.getParentIds().replace(oldParentIds,
					entity.getParentIds()));
		}
		thisDao.save(list);
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_ADDISTRICTCATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_ADDISTRICTCATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_ADDISTRICTCATEGORY_LIST);
	}

	/**
	 * 删除区域数据
	 *
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void delete(AdDistrictCategory entity) throws  Exception {
		thisDao.deleteById(entity.getId(), "%," + entity.getId() + ",%");
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_ADDISTRICTCATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_ADDISTRICTCATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_ADDISTRICTCATEGORY_LIST);

	}

	/**
	 * 根据分类ID查询广告分类
	 * @param categoryId
	 * @return
	 */
	public AdDistrictCategory findAdDistrictCategoryByCateGoryId(String categoryId,String type){
		return thisDao.findAdDistrictCategoryByCateGoryId(categoryId,type);
	}

	public synchronized String getId(AdDistrictCategory parent,boolean param){
		String type = getCurrentSysAreaType();
		Integer categoryId = categoryDao.findMaxAdDistrictCategoryId(parent.getId(),type);
		if(categoryId == null){
			categoryId =1;
		}
		String newCategoryId = "";
		int len = String.valueOf(categoryId).length();
		if(len==1){
			newCategoryId = "0"+categoryId+"0";
		}else if(len==2){
			newCategoryId = categoryId+"0";
		}else if(len==3){
			newCategoryId = categoryId+"";
		}
		TypeEnum tpenum =   AdDistrictCategory.TypeEnum.check(type,parent.getCategoryId());
		if(null == tpenum && (parent.getCategoryId().length()>2)){
			newCategoryId =  parent.getCategoryId() + newCategoryId;
		}
	   /*if(type.equals("China") && !parentId.equals("1")){
		   newCategoryId =  parentId + newCategoryId;
	   }

	   if(type.equals("China") && !parentId.equals("2")){
		   newCategoryId =  parentId + newCategoryId;
	   }

	   if(type.equals("Pakistan") && !parentId.equals("3")){
		   newCategoryId =  parentId + newCategoryId;
	   }*/
		if(param){
			String pre =AdDistrictCategory.TypeEnum.getByName(type).getValue();
			return pre + newCategoryId;
		}else{
			return newCategoryId;
		}

	}

	public String getDistrictJson(Map<String, Object> map){
		List<AdDistrictCategory> sourcelist = new ArrayList<AdDistrictCategory>();
		DistrictCategoryDTO dto = new DistrictCategoryDTO();
		List<AdDistrictCategory> list = Lists.newArrayList();
		Map<String,String> selMap = new HashMap<String, String>();

		Map<String, Object> listMap = Maps.newHashMap();
		Map<String, Object> relationsMap = Maps.newHashMap();
		Map<String, Object> categoryMap = Maps.newHashMap();
		List<String> districtList = new ArrayList<String>();//区域列表
		List<String> provincesList = new ArrayList<String>();//省份列表
		List<String> hotcitiesList = new ArrayList<String>();//热门城市
		String type = getCurrentSysAreaType();
		String seltype = "set";
		if(null != map.get("operatorsId")){
			seltype = "sel";
			String operatorsId = (String) map.get("operatorsId");
			AdOperators op = adOperatorsDao.get(operatorsId);
			List<AdOperatorsDistrict> adDistrictCategorys = op.getAdDistrictCategorys();
			List<String> ls = Lists.newArrayList();
			if(null != adDistrictCategorys &&adDistrictCategorys.size()>0){
				for (AdOperatorsDistrict adOperatorsDistrict : adDistrictCategorys) {
					ls.add(adOperatorsDistrict.getDistrict().getId());
					selMap.put(adOperatorsDistrict.getDistrict().getId(), adOperatorsDistrict.getSelfDistrictId());
					if(op.getSelArea().equals(adOperatorsDistrict.getDistrict().getId())){
						provincesList.add(adOperatorsDistrict.getDistrict().getId());
						hotcitiesList.add(adOperatorsDistrict.getDistrict().getId());
					}
				}
			}else{
				return null;
			}
			sourcelist = thisDao.findByRange(ls,type);
			AdDistrictCategory.sortListRange(list, sourcelist, AdDistrictCategory.getzeroAdCategoryId(type));
		}else{
			sourcelist= thisDao.findAllByType(type);
			AdDistrictCategory.sortList(list, sourcelist, AdDistrictCategory.getzeroAdCategoryId(type));
		}


		for (AdDistrictCategory adDistrictCategory : list) {
			List<String> ls = new ArrayList<String>();
			ls.add(adDistrictCategory.getCategoryName());
			ls.add(adDistrictCategory.getRemarks());
			if(selMap.containsKey(adDistrictCategory.getId())){
				ls.add(selMap.get(adDistrictCategory.getId()));
			}else{
				ls.add(adDistrictCategory.getId());
			}
			listMap.put(adDistrictCategory.getId(), ls.toArray());
			getChild(adDistrictCategory, districtList, hotcitiesList, relationsMap);
			if(seltype.equals("set")){
				String disKey = AdDistrictCategory.TypeEnum.getByName(this.getCurrentSysAreaType()).getKey();
				if(adDistrictCategory.getParent().getId().equals(disKey)){
					provincesList.add(adDistrictCategory.getId());
				}
			}

		}
		categoryMap.put("district", districtList.toArray());
		categoryMap.put("provinces", provincesList.toArray());
		categoryMap.put("hotcities", hotcitiesList.toArray());
		dto.setList(listMap);
		dto.setRelations(relationsMap);
		dto.setCategory(categoryMap);
		System.out.println(JsonMapper.toJsonString(dto));
		return JsonMapper.toJsonString(dto);
	}

	public static void getChild(AdDistrictCategory adDistrictCategory,List<String> districtList,List<String> hotcitiesList,Map<String, Object> relationsMap){
		if(adDistrictCategory.getChildList() != null &&adDistrictCategory.getChildList().size()>0){
			List<String> relationsList = new ArrayList<String>();
			if(!districtList.contains(adDistrictCategory.getId())){
				districtList.add(adDistrictCategory.getId());
			}
			/*if(!hotcitiesList.contains(adDistrictCategory.getId())){
				hotcitiesList.add(adDistrictCategory.getId());
			}*/
			for (AdDistrictCategory re : adDistrictCategory.getChildList()) {
				relationsList.add(re.getId());
			}
			relationsMap.put(adDistrictCategory.getId(), relationsList.toArray());
			for (AdDistrictCategory re : adDistrictCategory.getChildList()) {
				getChild(re,districtList,hotcitiesList,relationsMap);
			}
		}
	}

	/**
	 * 获取运营商/发送器所属区域信息
	 * @param categoryId
	 * @param operatorsId
	 * @return
	 */
	public String getOperatorDistrict(String categoryId,String operatorsId){
		List<AdDistrictCategory> list = Lists.newArrayList();
		List<AdDistrictCategory> sourcelist = new ArrayList<AdDistrictCategory>();
		List<DistrictCategoryModel> adDistrictCategorys = Lists.newArrayList();
		AdDistrictCategory category = thisDao.get(categoryId);
		sourcelist.add(category);
		String type = this.getCurrentSysAreaType();
		AdDistrictCategory.sortListRange(list, sourcelist, AdDistrictCategory.getzeroAdCategoryId(type));
		Map<String,String> selMap = new HashMap<String, String>();
		SelectDistrictDTO dto = new SelectDistrictDTO();
		if(StringUtils.isNotBlank(operatorsId)){
			AdOperators op = adOperatorsDao.get(operatorsId);
			List<AdOperatorsDistrict> adOperatorsDistricts = op.getAdDistrictCategorys();
			if(null != adOperatorsDistricts &&adOperatorsDistricts.size()>0){
				for (AdOperatorsDistrict adOperatorsDistrict : adOperatorsDistricts) {
					selMap.put(adOperatorsDistrict.getDistrict().getId(), adOperatorsDistrict.getSelfDistrictId());
				}
			}

		}
		for (AdDistrictCategory adDistrictCategory : list) {
			DistrictCategoryModel model = new DistrictCategoryModel();
			model.setCategoryId(adDistrictCategory.getId());
			model.setCategoryName(adDistrictCategory.getCategoryName());
			if(StringUtils.isNotBlank(operatorsId) && selMap.containsKey(adDistrictCategory.getId())){
				model.setSelfCategoryId(selMap.get(adDistrictCategory.getId()));
			}else{
				model.setSelfCategoryId("");
			}
			adDistrictCategorys.add(model);
		}

		dto.setAdDistrictCategorys(adDistrictCategorys);
		System.out.println(JsonMapper.toJsonString(dto));
		return JsonMapper.toJsonString(dto);
	}

	public String getOperatorsByDis(String disId){
		List<String> list = adOperatorsDistrictDao.getOperatorsByDis(disId);
		if(null != list && list.size()>0){
			String[]  aa = list.toArray(new String[0]);
			List<AdOperators> ops = adOperatorsDao.findByOperatorsIds(aa);
			List<AdOperatorsDTO> dtos = Lists.newArrayList();
			for (AdOperators adOperators : ops) {
				AdOperatorsDTO dto = new AdOperatorsDTO();
				dto.setOperatorsId(adOperators.getId());
				dto.setOperatorsName(adOperators.getOperatorsName());
				dtos.add(dto);
			}
			System.out.println(JsonMapper.toJsonString(dtos));
			return JsonMapper.toJsonString(dtos);
		}else{
			return null;
		}
	}


	/**
	 * 通过所选区域和运营商获取相应的发送器
	 * 1、针对每个运营商，如果本级和本级的下级没有对应定义的区域号，不能选择发送器。
	 * 2、如果有设置自定义区域号 ：本级和子区域的所有发送器都要查询出来供选择
	 * 3、然后判断本级是否有发送器或者发送器是否是全频道发送器，如果不是就需要去父级查询发送器，如果父级的发送器仍不是、继续查。
	 * @param disIds
	 * @param operatorsId
	 * @return
	 * @throws Exception
	 */
	public String getNetworkByDis(String disIds,String operatorsId,String typeId,String chlidType,String startDate,String endDate,String sendMode,String advertiserId,String type) throws Exception {

		List<AdNetwork> netList = getDisNetwork(disIds, operatorsId);
		if(null == netList){
			return null;
		}
		Map<String, Object> netSelMap = new HashMap<String, Object>();
		netSelMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		netSelMap.put("typeId", typeId);
		netSelMap.put("status", AdNetwork.NETWORK_YES_STATUS);
		netSelMap.put("isValid", AdCombo.ADCOMOBO_YES_VALID);
		netSelMap.put("startDate", startDate);
		netSelMap.put("endDate", endDate);
		netSelMap.put("chlidType", chlidType);
		netSelMap.put("sendMode", sendMode);
		netSelMap.put("advertiserId", advertiserId);

		List<AdComboNetworkDTO> list = Lists.newArrayList();

		List<String> nets = Lists.newArrayList();
		List<SelectAdNetworkDTO> returnList = Lists.newArrayList();
		//  selArea : IND060010-IND060020-IND060030-IND060040;
		//  comboArea : IND060010  IND060020-IND060030;

		String[] selArea = disIds.split("-");

		/*
		针对每个发送器查询出该发送器是否有套餐
		1、如果有套餐 查询出相应套餐所占用的区域
		2、如果套餐所占用的区域以及他的所有父类子类区域集合里面含有选择区域的任一个就不能选择该发送器
		*/

		List<String> selAreaList = Lists.newArrayList();
		//netSelMap.put("networkId",n.getId());
		list = adNetworkDao.findComboNetworkDTO(netSelMap);// 查询当前广告类型已经使用的发送器（区域模式）
		List<AdDistrictCategory> disList = Lists.newArrayList();
		if(list != null && list.size()>0){//获取该发送器不能选择的区域集合
			for (AdComboNetworkDTO dto:list ) {
				String[] dis = dto.getComboArea().split("-");
				for (int j = 0; j <dis.length ; j++) {
					AdDistrictCategory districtCategory = thisDao.get(dis[j]);
					getParent(districtCategory,disList,type);
					getChild(districtCategory,disList);
				}
			}
			for (AdDistrictCategory districtCategory:disList) {
				selAreaList.add(districtCategory.getId());
			}
		}

		for (int i = 0; i < netList.size(); i++) {
			AdNetwork n = netList.get(i);
			SelectAdNetworkDTO dto = new SelectAdNetworkDTO();
			dto.setInvalid(true);
			for (int j = 0; j <selArea.length ; j++) {
				if(selAreaList.contains(selArea[j])){
					dto.setInvalid(false);
					continue;
				}
			}
			dto.setId(n.getId());
			dto.setNetworkName(n.getNetworkName());
			dto.setArea(n.getArea());
			returnList.add(dto);
			nets.add(n.getId());
		}
		System.out.println(JsonMapper.toJsonString(returnList));
		return JsonMapper.toJsonString(returnList);
	}
	/**
	 * @Description:获取不能选择的区域集合
	 * @Param:
	 * @return:
	 * @Author: Mr.Zuo
	 * @Date: 2018/5/17 **
	 */
	public static void getParent(AdDistrictCategory adDistrictCategory,List<AdDistrictCategory> list,String type){
		String key = AdDistrictCategory.TypeEnum.getByName(type).getKey();
		if(!list.contains(adDistrictCategory)){
			list.add(adDistrictCategory);
		}
		if(null != adDistrictCategory.getParent() && !adDistrictCategory.getParent().equals(key)){
			getParent(adDistrictCategory.getParent(),list,type);
		}
	}

	public static void getChild(AdDistrictCategory adDistrictCategory,List<AdDistrictCategory> list){
		if(!list.contains(adDistrictCategory)){
			list.add(adDistrictCategory);
		}
		if(null != adDistrictCategory.getChildList() && adDistrictCategory.getChildList().size()>0){
			List<AdDistrictCategory> childs = adDistrictCategory.getChildList();
			for (AdDistrictCategory adDistrictCategory2 : childs) {
				getChild(adDistrictCategory2, list);
			}
		}
	}


	/**
	 * 获取设置了自定义区域号的发送器
	 * @param disIds
	 * @param operatorsId
	 * @param typeId
	 * @param chlidType
	 * @param startDate
	 * @param endDate
	 * @param sendMode
	 * @param advertiserId
	 * @return
	 * @throws Exception
	 */
	public String getNetworkChannelByDis(String disIds,String operatorsId,String typeId,String chlidType,String startDate,String endDate,String sendMode,String advertiserId) throws Exception {

		List<AdNetwork> netList = getDisNetwork(disIds, operatorsId);
		if(null == netList){
			return null;
		}
		AdOperators a = adOperatorsDao.get(operatorsId);
		AdOperatorsDTO dto = new AdOperatorsDTO();
		dto.setOperatorsId(a.getId());
		dto.setOperatorsName(a.getOperatorsName());
		List<AdNetWorkDTO> networkList = new ArrayList<AdNetWorkDTO>();
		List<String> nets = Lists.newArrayList();
		for (AdNetwork adNetwork : netList) {
			if(null == adNetwork || adNetwork.getStatus() == AdNetwork.NETWORK_NO_STATUS ||nets.contains(adNetwork.getId())){
				continue;
			}
			AdNetWorkDTO ntdto = new AdNetWorkDTO();
			ntdto.setNetworkId(adNetwork.getId());
			ntdto.setNetworkName(adNetwork.getNetworkName());
			networkList.add(ntdto);
			nets.add(adNetwork.getId());
		}
		dto.setNetworkList(networkList);
		System.out.println(JsonMapper.toJsonString(dto));
		return JsonMapper.toJsonString(dto);
	}

	public List<AdNetwork> getDisNetwork(String disIds,String operatorsId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("operatorId", operatorsId);
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		String[] s = disIds.split("-");
		Map<String,Set<AdNetwork>> netMap = new HashMap<String,Set<AdNetwork>>();
		for (String string : s) {
			AdDistrictCategory dis = thisDao.get(string);
			map.put("districtId", string);
			Integer opInt = adDistrictCategoryDao.getOpsByParam(map);
			if(opInt > 0){
				map.put("selType","selLike");
				//查询本级和子区域的所有发送器
				List<AdNetwork> nets = adNetworkDao.getNetworksByParam(map);
				map.put("selType","selAll");
				getNetwork(map, netMap, dis, operatorsId, new HashSet<AdNetwork>(nets));
			}
		}
		if(null != netMap && netMap.size()>0){
			return new ArrayList<>(netMap.get(operatorsId));
		}else{
			return null;
		}

	}

	/**
	 *
	 * @param map
	 * @param netMap
	 * @param dis
	 * @param operatorsId
	 * @param nets
	 */
	private void getNetwork(Map<String, Object> map,Map<String, Set<AdNetwork>> netMap, AdDistrictCategory dis,String operatorsId, Set<AdNetwork> nets) {
		String type = this.getCurrentSysAreaType();
		String disKey = AdDistrictCategory.TypeEnum.getByName(type).getKey();
		List<AdNetwork> allNet = adNetworkDao.getNetworkByDistrictId(map);//获取当级发送器
		nets.addAll(allNet);
		if (null != allNet && allNet.size() > 0) {//判断本级发送器是否存在

			setNetwork(map,netMap,operatorsId,nets);
			//如果发送器存在 && 是否控制全部频道为空或者值为0
			if(StringUtils.isBlank(allNet.get(0).getIsControlAllAD()) && allNet.get(0).getIsControlAllAD().equals("0")){
				//如果发送器是否控制全频道的值不为1 去父级查询
				if (null != dis.getParent() && null != dis.getParent().getId() && !dis.getParent().getId().equals(disKey)) {
					setNetwork(map,netMap,operatorsId,nets);
					map.put("districtId", dis.getParent().getId());
					getNetwork(map, netMap, dis.getParent(), operatorsId, nets);
				}
			}
		}else{//如果发送器不存在 也去父类查询
			if (null != dis.getParent() && null != dis.getParent().getId() && !dis.getParent().getId().equals(disKey)) {
				map.put("districtId", dis.getParent().getId());
				getNetwork(map, netMap, dis.getParent(), operatorsId, nets);
			}else{
				setNetwork(map,netMap,operatorsId,nets);
			}
		}
	}

	private  void setNetwork(Map<String, Object> map,Map<String, Set<AdNetwork>> netMap,String operatorsId,Set<AdNetwork> nets){
		if (netMap.containsKey(operatorsId)) {
			for (AdNetwork net:nets) {
				netMap.get(operatorsId).add(net);
			}
			netMap.put(operatorsId, netMap.get(operatorsId));
		} else {
			netMap.put(operatorsId, nets);
		}
	}

	public String getCurrentSysAreaType(){
		String type = AdDistrictCategory.TYPE_CHINA;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("paramType", SysParam.SYS_AREA_TYPE);
		map.put("paramKey", SysParam.SYS_AREA_KEY);
		SysParam sysParam = sysParamService.getParam(map);
		if(sysParam!=null){
			type = sysParam.getParamValue();
		}
		return type;
	}
}
