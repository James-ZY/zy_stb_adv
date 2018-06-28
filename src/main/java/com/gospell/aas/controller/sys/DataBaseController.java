package com.gospell.aas.controller.sys;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;

import com.gospell.aas.common.utils.*;
import com.gospell.aas.service.quartz.PutEndTask;
import com.gospell.aas.service.sys.QuartzJobService;
import net.sf.ehcache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.sys.DataBaseRecord;
import com.gospell.aas.service.sys.DataBaseRecordService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 数据库备份还原Controller
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/sys/database")
public class DataBaseController extends BaseController {
	@Autowired
	private DataBaseRecordService dataBaseService;
	@Autowired
	private QuartzJobService quartzJobService;
	@Resource(name = "JobMethod")
	private JobMethod jobMethod;

	private static CacheManager cacheManager = ((CacheManager) ApplicationContextHelper.getBean("cacheManager"));


	@ModelAttribute
	public DataBaseRecord get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return dataBaseService.get(id);
		} else {
			return new DataBaseRecord();
		}
	}

	@RequiresPermissions("sys:database:view")
	@RequestMapping(value = { "list", "" })
	public String list(DataBaseRecord database, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<DataBaseRecord> page = dataBaseService.find(new Page<DataBaseRecord>(
				request, response), database);
		if (null != database.getCreateDateStart()) {
			model.addAttribute("createDateStart", DateUtils.formatDate(
					database.getCreateDateStart(), "yyyy-MM-dd"));
		}
		if (null != database.getCreateDateEnd()) {
			model.addAttribute("createDateEnd", DateUtils.formatDate(
					database.getCreateDateEnd(), "yyyy-MM-dd"));
		}
		model.addAttribute("page", page);
		model.addAttribute("message", database.getUploadMessage());

		return "/sys/dataBaseList";
	}

	
	@RequiresPermissions("sys:database:edit")
	@RequestMapping(value = "/backup")
	public String backup(DataBaseRecord database, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		model.addAttribute("database", database);
		addMessage(redirectAttributes, "msg.backup.success");
		return "redirect:/sys/database/?repage";
	}
	
	@RequiresPermissions("sys:database:edit")
	@RequestMapping(value = "/restore")
	public String restore(DataBaseRecord database, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(database.getId())) {
			database =  dataBaseService.get(database.getId());
		}
		String path = request.getSession().getServletContext().getRealPath("");
		String rPath;
		if(path.split("/").length>1){
			rPath = path.substring(0, path.lastIndexOf("/"));			
		}else{
			rPath = path.substring(0, path.lastIndexOf("\\"));		
		}
		DataBaseManage.restore(rPath+database.getRecordPath());
		UserUtils.removeCache(UserUtils.CACHE_ADCATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_ADCATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_ADCATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ADDISTRICTCATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_ADDISTRICTCATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_ADDISTRICTCATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_PROGRAM_CATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_PROGRAM_CATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_PROGRAM_CATEGORY_LIST);
		model.addAttribute("database", database);
		addMessage(redirectAttributes, "msg.restore.success");
		return "redirect:/sys/database/?repage";
	}

	@RequiresPermissions("sys:database:edit")
	@RequestMapping(value = "/runJobNow")
	public String runJobNow(DataBaseRecord database, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		try {
			PutEndTask ta = new PutEndTask();
			ta.dataBaseManage();
			addMessage(redirectAttributes, "msg.backup.success");
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "msg.backup.fail");
		}
		return "redirect:/sys/database/?repage";
	}

	/**
	 * 数据库备份文件上传
	 * @throws IOException
	 */
	@RequiresPermissions("sys:database:import")
	@RequestMapping(value = "import")
	@POST
	public String helpFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request,
								 HttpServletResponse response, Model mode) throws IOException{
		request.getSession().getServletContext().getRealPath("/upload");
		String[] root = com.gospell.aas.common.utils.FileUtils.fileUploadAdr(request.getSession().getServletContext().getRealPath(""));
		String realPath = root[0];
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		// 上传文件的原名(即上传前的文件名字)
		String originalFilename = null;
		String uploadPath=null;
		String addPath = "restore"+"/"+ UserUtils.getUser().getLoginName();
		StringBuilder failureMsg = new StringBuilder();
		// 如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
		// 如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
		// 上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是file,否则参数里的file无法获取到所有上传的文件
		if (file.isEmpty()) {
			out.print(getMessage("please.select.file"));
			out.flush();
			return null;
		} else {
			originalFilename = file.getOriginalFilename();
			uploadPath ="/upload/"+root[1]+"/"+addPath;

			try{
				if(StringUtils.isNotBlank(originalFilename)){
					originalFilename = isExsit(realPath+"/"+addPath+"/", originalFilename, 1);
					org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath+"/"+addPath, originalFilename));
					DataBaseManage.restore(realPath+"/"+addPath+"/"+originalFilename);
				}

			} catch (IOException e) {
				logger.error("文件[" + originalFilename + "]上传失败,堆栈轨迹如下", e);
				out.print(getMessage("msg.restore.fail"));
				out.flush();
				return null;
			}
		}
		out.print(getMessage("msg.restore.success"));
		out.flush();
		return null;
	}

	/**
	 * 判断一个文件是否重复，重复了创建副本，如果副本有了，创建副本的副本，依次叠加，直到没有
	 * @param folder
	 * @param fileName
	 * @return
	 */
	public  String isExsit(String folder,String fileName,int version){
		String f = folder+fileName;
		File isEx=new File(f);
		if(isEx.exists()){
			String message =getMessage("rename.file");

			if(version == 1){
				fileName = fileName.substring(0,fileName.lastIndexOf("."))+"-"+message+"."+fileName.substring(fileName.lastIndexOf(".")+1); // 重新命名上传文件名称
			}else if(version == 2){
				fileName = fileName.substring(0,fileName.lastIndexOf("."))+"("+version+")"+"."+fileName.substring(fileName.lastIndexOf(".")+1); // 重新命名上传文件名称

			}else{
				String format = fileName.substring(fileName.lastIndexOf(".")+1);
				fileName = fileName.substring(0,fileName.lastIndexOf("."));

				fileName = fileName.substring(0,fileName.lastIndexOf("("));
				fileName = fileName+"("+version+")"+"."+format;
			}

			version ++;
			return isExsit(folder, fileName,version);
		}else{
			return fileName;
		}

	}

}
