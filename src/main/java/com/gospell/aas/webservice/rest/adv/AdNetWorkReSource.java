package com.gospell.aas.webservice.rest.adv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.gospell.aas.service.quartz.PutEndTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.utils.ApplicationContextHelper;
import com.gospell.aas.common.utils.adv.TimeZoneUtil;
import com.gospell.aas.common.web.MediaTypes;
import com.gospell.aas.dto.adv.AdNetWorkDTO;
import com.gospell.aas.dto.adv.ChannelDTO;
import com.gospell.aas.dto.adv.SelectChannelDTO;
import com.gospell.aas.entity.adv.AdDefaultControll;
import com.gospell.aas.entity.adv.AdNetwork;
import com.gospell.aas.entity.adv.AdNetworkDistrict;
import com.gospell.aas.entity.adv.AdOperators;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.entity.sys.SysParam;
import com.gospell.aas.repository.mybatis.adv.IAdDefaultControlDao;
import com.gospell.aas.service.adv.AdDefaultControllService;
import com.gospell.aas.service.adv.AdNetworkService;
import com.gospell.aas.service.adv.AdOperatorsService;
import com.gospell.aas.service.adv.AdTypeService;
import com.gospell.aas.service.sys.SysParamService;
import com.gospell.aas.webservice.netty.dto.AdvNetWorkChannelDTO;
import com.gospell.aas.webservice.netty.dto.AdvNetWorkDistrictDTO;
import com.gospell.aas.webservice.netty.dto.AdvNetWorkTimeSetDTO;
import com.gospell.aas.webservice.netty.dto.AdvNetWorkTypeDTO;
import com.gospell.aas.webservice.netty.dto.AdvNetWorkVideoRateDTO;
import com.gospell.aas.webservice.netty.dto.DeleteChannelDTO;
import com.gospell.aas.webservice.netty.dto.DeleteNetWorkTypeDTO;
import com.gospell.aas.webservice.netty.dto.IsDelete;
import com.gospell.aas.webservice.netty.dto.SystemTimeZoneDTO;
import com.gospell.aas.webservice.rest.RestResult;
import com.gospell.aas.webservice.rest.Result.Status;

@Path(value = "/network")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class AdNetWorkReSource {

	@Autowired
	private AdNetworkService service;

	@Autowired
	private AdOperatorsService opService;

	@Autowired
	private SysParamService paramService;

	@Autowired
	private IAdDefaultControlDao iadControllDao;

	@Autowired
	private AdDefaultControllService controllService;

	@Autowired
	private AdTypeService typeService;



	@POST
	@Path(value = "/add")
	public RestResult getAdNetWork(AdvNetWorkChannelDTO dto,
								   @Context HttpServletRequest request) {

		RestResult result = new RestResult();
		try {
			if (null == dto) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			} else {


				AdNetwork network = service.findByNetworkId(dto
						.getNetworkId());
				if (null == network) {
					result.setStatus(Status.NOT_EXIST_ERROR.getCode());
					return result;

				} else {

					String content = service.saveChannleToNetwork(dto, network);
					result.setContent(content);
					result.setStatus(Status.OK.getCode());
					return result;

				}
			}
		} catch (Exception e) {

			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;

		}

	}

	@POST
	@Path(value = "/deleteChannel")
	public RestResult deleteChannel(AdvNetWorkChannelDTO dto) {
		RestResult result = new RestResult();
		result.setStatus(Status.BAD_REQUEST.getCode());
		try {
			if (null == dto) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			} else {
				PutEndTask ta = new PutEndTask();
				ta.dataBaseManage();
				String networkId = dto.getNetworkId();
				List<String> channelIds = Lists.newArrayList();
				List<SelectChannelDTO> channelList = new ArrayList<SelectChannelDTO>();
				if (StringUtils.isNotBlank(networkId)) {

					AdNetwork network = service.findByNetworkId(dto
							.getNetworkId());
					if (null == network) {
						result.setStatus(Status.NOT_EXIST_ERROR.getCode());
						return result;

					}else{
						for (ChannelDTO model : dto.getChannelList()) {
							channelIds.add(model.getChannelId());
							SelectChannelDTO sd = new SelectChannelDTO();
							sd.setChannelId(model.getChannelId());
							channelList.add(sd);
						}
						if (null != channelIds && channelIds.size()>0) {
							List<String> deleteList = service.getCanDeleteAdChannel(network.getId(),channelIds);
							if(null !=deleteList && deleteList.size()>0){
								service.deleteChannels(network.getId(), deleteList);
								service.deleteChannelList(deleteList);
							}
							DeleteChannelDTO deletedto = new DeleteChannelDTO();
							deletedto.setIsDelete(String.valueOf(true));
							deletedto.setChannelList(channelList);
							result.setContent(JsonMapper.toJsonString(deletedto));
							result.setStatus(Status.OK.getCode());
							result.setMessage("删除发送器ID："+networkId+"下的频道成功！");
						}
					}
				}
				return result;
			}

		} catch (Exception e) {

			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;

		}
	}

	@POST
	@Path(value = "/register")
	public RestResult register(AdNetWorkDTO dto) {
		System.out.println("network register");
		RestResult result = new RestResult();
		try {
			if (null == dto) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			} else {
				AdNetwork network = service.findByNetworkIdAndOperatorId(dto
						.getNetworkId(),dto.getAdOperatorsId());
				dto.setOnlineStatus("1");
				if (null != network) {
					result.setStatus(Status.EXIST_ERROR.getCode());
					result.setMessage("该发送器已经注册过！");
					result.setContent(JsonMapper.toJsonString(getSystemTimeZone()));//更新成功，需要把当前播控的时间和时区发送下去
					service.updateAdNetwork(dto,network);
					initDefaultImage(network.getNetworkId());
					return result;
				} else {
					network = service.findByNetworkId(dto.getNetworkId());
					if(null != network){
						result.setStatus(Status.NETWORK_CONFLICT.getCode());
						result.setMessage("已存在该发送器ID,请重新选择");
						return result;
					}else{
						AdOperators op = opService.findByOperators(
								dto.getAdOperatorsId(), dto.getPassword());
						if (null == op) {
							result.setStatus(Status.NOT_EXIST_ERROR.getCode());
							result.setMessage("找不到对应的电视运营商");
							return result;

						} else {
							service.registerAdNetwork(dto, op);
							result.setStatus(Status.OK.getCode());
							result.setMessage("注册成功！");
							result.setContent(JsonMapper.toJsonString(getSystemTimeZone()));//更新成功，需要把当前播控的时间和时区发送下去
							initDefaultImage(dto.getAdOperatorsId());
							return result;
						}

					}
				}



			}
		} catch (Exception e) {

			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;

		}
	}

	/**
	 * 更新发送器状态
	 * @param dto
	 * @return
	 */
	@POST
	@Path(value = "/update")
	public RestResult update(AdNetWorkDTO dto) {
		RestResult result = new RestResult();
		try {
			if (null == dto) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
			} else {
				AdNetwork network = service.findByNetworkId(dto
						.getNetworkId());
				if (null != network) {
					network.setOnlineStatus(dto.getOnlineStatus());
					service.save(network);
					result.setStatus(Status.OK.getCode());
					result.setMessage("更新发送器状态成功");
				}

			}
		} catch (Exception e) {
			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;
		}
		return result;
	}


	/**
	 * 判断默认文件是否存在
	 * @param adNetworkId
	 */
	private void initDefaultImage(String adNetworkId){
		AdNetwork network = service.findByNetworkId(adNetworkId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("adNetworkId", network.getId());
        //设置默认开机图片
		checkDefault(map,AdType.Type_OPEN_IMGAE,AdDefaultControll.FLAG_SD,network);
		checkDefault(map,AdType.Type_OPEN_IMGAE,AdDefaultControll.FLAG_HD,network);
		//设置默认开机背景图片
		checkDefault(map,AdType.Type_BROCAST,AdDefaultControll.FLAG_SD,network);
		checkDefault(map,AdType.Type_BROCAST,AdDefaultControll.FLAG_HD,network);
		//设置默认换台图片
		checkDefault(map,AdType.Type_CHANGE_CHANNEL,AdDefaultControll.FLAG_SD,network);
		checkDefault(map,AdType.Type_CHANGE_CHANNEL,AdDefaultControll.FLAG_HD,network);
	}

	//判断该发送器是否有默认图片设置
	private void checkDefault(Map<String,Object> map,String adTypeId,Integer flag,AdNetwork network){
		map.put("adTypeId", adTypeId);
		map.put("flag", flag);
		List<AdDefaultControll> list = iadControllDao.getControlByTypeId(map);
		if( null == list || 0 == list.size()){
			saveCon(network,flag,adTypeId);
		}
	}

	private void saveCon(AdNetwork network,Integer flag,String typeId){
		AdDefaultControll model = new AdDefaultControll();
		if (typeId.equals(AdType.Type_CHANGE_CHANNEL)) {
			String imgpath = "";
			String imgpath1 = "";
             if(flag.equals(AdDefaultControll.FLAG_SD)){
				 imgpath = ApplicationContextHelper.getRootRealPath() + "/static/images/homecableSD.bmp";
				 imgpath1 = com.gospell.aas.common.utils.FileUtils.getUploadFileRealPath() + "/upload/advs/admin/image/homecableSD.bmp";
				 model.setWidth(220);
				 model.setHeight(100);
				 model.setFileSize("64");
				 model.setFilePath("/upload/advs/admin/image/homecableSD.bmp");

			 }else{
				 imgpath = ApplicationContextHelper.getRootRealPath() + "/static/images/homecableHD.bmp";
				 imgpath1 = com.gospell.aas.common.utils.FileUtils.getUploadFileRealPath() + "/upload/advs/admin/image/homecableHD.bmp";
				 model.setWidth(133);
				 model.setHeight(180);
				 model.setFileSize("70");
				 model.setFilePath("/upload/advs/admin/image/homecableHD.bmp");

			 }
			com.gospell.aas.common.utils.FileUtils.copyFileCover(imgpath, imgpath1, true);
			model.setAdNetwork(network);
			model.setAdType(typeService.get(typeId));
			model.setPlayOrder(0);
			model.setFlag(flag);
			model.setResourceType(1);
			model.setStatus(AdDefaultControll.STATUS_WAIT);
			model.setFileFormat("bmp");
		} else {
			String imgpath = ApplicationContextHelper.getRootRealPath() + "/static/images/defaultkaiji.jpg";
			String m2vpath = ApplicationContextHelper.getRootRealPath() + "/static/images/defaultkaiji.m2v";
			String imgpath1 = com.gospell.aas.common.utils.FileUtils.getUploadFileRealPath() + "/upload/advs/admin/m2v/defaultkaiji.jpg";
			String m2vpath1 = com.gospell.aas.common.utils.FileUtils.getUploadFileRealPath() + "/upload/advs/admin/m2v/defaultkaiji.m2v";
			com.gospell.aas.common.utils.FileUtils.copyFileCover(imgpath, imgpath1, true);
			com.gospell.aas.common.utils.FileUtils.copyFileCover(m2vpath, m2vpath1, true);
			model.setAdNetwork(network);
			model.setAdType(typeService.get(typeId));
			model.setFilePath("/upload/advs/admin/m2v/defaultkaiji.m2v");
			model.setFileImagePath("/upload/advs/admin/m2v/defaultkaiji.jpg");
			model.setWidth(720);
			model.setHeight(576);
			model.setPlayOrder(0);
			model.setFlag(flag);
			model.setFileSize("38419");
			model.setResourceType(2);
			model.setStatus(AdDefaultControll.STATUS_WAIT);
			model.setDuration(0);
			model.setFileFormat("m2v");
		}
		try {
			controllService.save1(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取当前时区以及当时的时间
	 * @return
	 */
	private SystemTimeZoneDTO getSystemTimeZone(){

		SystemTimeZoneDTO d = new SystemTimeZoneDTO();
		d.setTimeZone(TimeZoneUtil.getTimeZone());
		d.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		return d;
	}



	@POST
	@Path(value = "/addAdType")
	public RestResult addAdType(AdvNetWorkTypeDTO dto) {
		RestResult result = new RestResult();
		result.setStatus(Status.BAD_REQUEST.getCode());
		try {
			if (null == dto) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			} else {
				String networkId = dto.getNetworkId();
				String type = dto.getNotChannelTypeId();
				if (StringUtils.isNotBlank(networkId)) {

					if (StringUtils.isNotBlank(type)) {
						String[] s = type.split(",");
						List<String> typeIdList = Lists.newArrayList();
						for (int i = 0; i < s.length; i++) {
							typeIdList.add(s[i]);
						}
						service.addAdTypeToNetwork(networkId, typeIdList);
						result.setStatus(Status.OK.getCode());
						result.setMessage("给发送器ID："+networkId+"添加广告类型成功！");

					}
				}
				return result;
			}

		} catch (Exception e) {
			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;

		}
	}



	@POST
	@Path(value = "/deleteAdType")
	public RestResult deleteAdType(DeleteNetWorkTypeDTO dto) {
		RestResult result = new RestResult();
		result.setStatus(Status.BAD_REQUEST.getCode());
		try {
			if (null == dto) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			} else {
				String networkId = dto.getNetworkId();
				String type = dto.getTypeId();
				if (StringUtils.isNotBlank(networkId)) {

					if (StringUtils.isNotBlank(type)) {
						Boolean b = service.deleteAdTypeToNetwork(networkId, type);
						if(b == null) b =false;
						IsDelete isDelete = new IsDelete();
						isDelete.setIsDelete(String.valueOf(b));
						result.setContent(JsonMapper.toJsonString(isDelete));
						result.setStatus(Status.OK.getCode());
						result.setMessage("删除发送器ID："+networkId+"的广告类型成功！");

					}
				}
				return result;
			}

		} catch (Exception e) {

			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;

		}
	}

	/**
	 * 增加视频码率设置
	 * @param dto
	 * @param request
	 * @return
	 */
	@POST
	@Path(value = "/addVedioRate")
	public RestResult addVedioRate(AdvNetWorkVideoRateDTO dto,
								   @Context HttpServletRequest request) {

		RestResult result = new RestResult();
		try {
			if (null == dto) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			} else {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
				map.put("paramType", SysParam.NETWORK_VIDEO_RATE);
				map.put("paramKey", dto.getNetworkId());
				SysParam param = paramService.getParam(map);
				if (null == param) {
					param = new SysParam();
					param.setParamType(SysParam.NETWORK_VIDEO_RATE);
					param.setParamKey(dto.getNetworkId());
				}
				param.setParamValue(dto.getVideoRate());
				param.setEnable(SysParam.ENABLE_YES);
				param.setCanUpdate(SysParam.CANUPDATE_NO);
				paramService.save(param);

				/*map.put("paramType", SysParam.NETWORK_PICTURE_RATE);
				map.put("paramKey", dto.getNetworkId());
				param = paramService.getParam(map);
				if (null == param) {
					param = new SysParam();
					param.setParamType(SysParam.NETWORK_PICTURE_RATE);
					param.setParamKey(dto.getNetworkId());

				}
				param.setParamValue(dto.getPictureRate()==null?"10":dto.getPictureRate());
				param.setEnable(SysParam.ENABLE_YES);
				param.setCanUpdate(SysParam.CANUPDATE_NO);
				paramService.save(param);*/

				result.setStatus(Status.OK.getCode());
				return result;
			}
		} catch (Exception e) {

			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;

		}
	}


	/**
	 * 增加插屏滚动 时间限制
	 * @param dto
	 * @param request
	 * @return
	 */
	@POST
	@Path(value = "/addTimeSet")
	public RestResult addTimeSet(AdvNetWorkTimeSetDTO dto,
								 @Context HttpServletRequest request) {

		RestResult result = new RestResult();
		try {
			if (null == dto) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			} else {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
				map.put("paramType", SysParam.ADELEMENT_TIME_SET);
				map.put("paramKey", dto.getNetworkId());
				SysParam param = paramService.getParam(map);
				Integer time = Integer.parseInt(dto.getTimeSet());
				int s = time*6;
				if (null == param) {
					param = new SysParam();
					param.setParamType(SysParam.ADELEMENT_TIME_SET);
					param.setParamKey(dto.getNetworkId());
				}
				param.setParamValue(String.valueOf(s));
				param.setEnable(SysParam.ENABLE_YES);
				param.setCanUpdate(SysParam.CANUPDATE_NO);
				paramService.save(param);
				result.setStatus(Status.OK.getCode());
				return result;
			}
		} catch (Exception e) {

			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;

		}
	}

	/**
	 * 获取发送器的区域设置信息
	 * @param dto
	 * @param request
	 * @return
	 */
	@POST
	@Path(value = "/getAdDistrictInfo")
	public RestResult getAdDistrictInfo(AdvNetWorkTypeDTO dto,
										@Context HttpServletRequest request) {

		RestResult result = new RestResult();
		try {
			if (null == dto) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			} else {

				AdNetwork network = service.findByNetworkId(dto.getNetworkId());
				if (null == network) {
					result.setStatus(Status.NOT_EXIST_ERROR.getCode());
					return result;
				} else {
					AdvNetWorkDistrictDTO disDto = service.getNetDisDto(network);
					result.setContent(JsonMapper.toJsonString(disDto));
					result.setStatus(Status.OK.getCode());
					return result;
				}
			}
		} catch (Exception e) {
			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;

		}
	}
}
