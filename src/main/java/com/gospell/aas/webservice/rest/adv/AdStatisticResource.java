package com.gospell.aas.webservice.rest.adv;

import com.gospell.aas.common.utils.ApplicationContextHelper;
import com.gospell.aas.common.utils.ZipUtils;
import com.gospell.aas.common.web.MediaTypes;
import com.gospell.aas.dto.adv.UploadAdStatisticDTO;
import com.gospell.aas.entity.adv.AdStatistic;
import com.gospell.aas.service.adv.AdStatisticService;
import com.gospell.aas.webservice.rest.RestResult;
import com.gospell.aas.webservice.rest.Result.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipException;

@Path(value = "/statistics")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class AdStatisticResource {
 

	private AdStatisticService thisService = ApplicationContextHelper
			.getBean(AdStatisticService.class);
	private Logger logger = LoggerFactory.getLogger(AdStatisticResource.class);

	/**
	 * 保存当前广告的统计数据
	 * 
	 * @param
	 * @return
	 */
	@POST
	@Path(value = "/add")
	public RestResult advStatistic(Map<String,String> map) {
		UploadAdStatisticDTO pushDto = new UploadAdStatisticDTO();
		String uploadData = map.get("uploadData");
		String system = map.get("system");
		String userCode = map.get("userCode");
		System.out.println(uploadData);
		pushDto.setUploadData(uploadData);
		pushDto.setSystem(Integer.parseInt(system));
		pushDto.setUserCode(userCode);
		RestResult rest = new RestResult();
		try{

		if(null == pushDto || org.apache.commons.lang3.StringUtils.isBlank(uploadData)){
			rest.setStatus(Status.BLANK_PARAMETER.getCode());
			rest.setMessage(Status.BLANK_PARAMETER.getReason());
			return rest;
		}				
		String unzipData= ZipUtils.gunzip(uploadData);
		pushDto.setUploadData(unzipData);//避免后面又重新解析
		System.out.println(unzipData);
		Integer b = thisService.checkIsUploadCheck(uploadData,unzipData);
		rest.setStatus(Status.BAD_REQUEST.getCode());
		if(b==2){
			//System.out.println("二维码格式错误");
			rest.setMessage("二维码格式错误");
			return rest;
		}else if(b==1){//当前机顶盒上传过相同的数据，防止用户多次扫描同一个二维码
			//System.out.println("重复上传");
			rest.setStatus(Status.OK.getCode());
			rest.setMessage(Status.OK.getReason());
			rest.setContent(thisService.getUrl(unzipData));
			return rest;
		}
		List<AdStatistic> insert_list = thisService.checkString(pushDto,rest);
		if(null != insert_list && insert_list.size()>0){
			thisService.save(insert_list,uploadData);
			//System.out.println("数据格式验证通过！");
			logger.info("数据格式验证通过！");
			rest.setStatus(Status.OK.getCode());
			rest.setMessage(Status.OK.getReason());
		} 
		}catch(ZipException e){
			e.printStackTrace();
			rest.setStatus(Status.BAD_REQUEST.getCode());
			rest.setMessage(Status.INTERNAL_SERVER_ERROR.getReason());
		}catch(Exception e){
			e.printStackTrace();
			rest.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			rest.setMessage(Status.INTERNAL_SERVER_ERROR.getReason());
		}
		return rest;
	}

	 
	
}
