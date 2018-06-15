//package com.gospell.aas.interceptor;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.lang.reflect.Type;
//import java.util.Date;
//
//import javax.persistence.Id;
//import javax.servlet.http.HttpServletRequest;
//
//import javassist.ClassClassPath;
//import javassist.ClassPool;
//import javassist.CtClass;
//import javassist.CtMethod;
//import javassist.Modifier;
//import javassist.NotFoundException;
//import javassist.bytecode.CodeAttribute;
//import javassist.bytecode.LocalVariableAttribute;
//import javassist.bytecode.MethodInfo;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import com.gospell.aas.common.persistence.IdEntity;
//import com.gospell.aas.common.utils.ApplicationContextHelper;
//import com.gospell.aas.common.utils.StringUtils;
//import com.gospell.aas.common.utils.UserUtils;
//import com.gospell.aas.entity.adv.AdOperators;
//import com.gospell.aas.entity.sys.Log;
//import com.gospell.aas.entity.sys.User;
//import com.gospell.aas.repository.hibernate.sys.LogDao;
//import com.gospell.aas.service.sys.SysLogService;
//
///**
// * 
// * @author arthur.paincupid.lee
// * @since 2016.04.17
// */
//@Aspect
//@Component
//public class SysLogWithOutAnn {
//	private static final Logger logger = LoggerFactory
//			.getLogger(SysLogWithOutAnn.class);
//	private static SysLogService logDao = ApplicationContextHelper.getBean(SysLogService.class);
//	private static String[] types = { "java.lang.Integer", "java.lang.Double",
//			"java.lang.Float", "java.lang.Long", "java.lang.Short",
//			"java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
//			"java.lang.String", "int", "double", "long", "short", "byte",
//			"boolean", "char", "float" };
//
//	@Pointcut("execution(* com.gospell.aas.controller.adv.AdOperatorsController.save(..))")
//	public void searchControllerCall() {
//	}
//
//	@AfterReturning(value = "searchControllerCall()", argNames = "rtv", returning = "rtv")
//	public void searchControllerCallCalls(JoinPoint joinPoint, Object rtv)
//			throws Throwable {
//		System.out.println("---------------");
//		String classType = joinPoint.getTarget().getClass().getName();
//		Class<?> clazz = Class.forName(classType);
//		String clazzName = clazz.getName();
//		String clazzSimpleName = clazz.getSimpleName();
//		String methodName = joinPoint.getSignature().getName();
//		
//		String[] paramNames = getFieldsName(this.getClass(), clazzName, methodName);
//		
//		String logContent = writeLogInfo(paramNames, joinPoint,methodName);
//		
//		Logger logger = LoggerFactory.getLogger(clazzName);
// 
//		
//	}
//	private static String writeLogInfo(String[] paramNames, JoinPoint joinPoint,String methodName){
//		Object[] args = joinPoint.getArgs();
//		if(args == null || args.length == 0){
//			return "";
//		}
//		Log log = new Log();
//		log.setLogInfo(methodName);
//		StringBuilder sb = new StringBuilder();
//		boolean clazzFlag = true;
//		for(int k=0; k<args.length; k++){
//			Object arg = args[k];
//			if(arg == null){
//				continue;
//			}
//			  if(arg instanceof HttpServletRequest){
//				 setLog(log, (HttpServletRequest)arg);
//			 }else{
//				  if (arg instanceof AdOperators) {
//					 
//					
//				 
//				 String info = getFieldsValue(arg, "logName");
//				 User user = UserUtils.getUser();
//				 System.out.println(info);
//				 System.out.println(("手动输出："+((AdOperators) arg).getLogName()));
//				 
//				 log.setLogInfo(user.getId()+methodName+info);
//				  }
//			 }
//		}
//		logDao.save(log);
//		return sb.toString();
//	}
//	
//	public static void setLog(Log log,HttpServletRequest request){
//		 String requestRri = request.getRequestURI();
//       String uriPrefix = request.getContextPath();
//    
//
// 
//
//           User user = UserUtils.getUser();
//           if (user != null && user.getId() != null) {
//
//               StringBuilder params = new StringBuilder();
//               int index = 0;
//               for (Object param : request.getParameterMap().keySet()) {
//                   params.append((index++ == 0 ? "" : "&") + param + "=");
//                   params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase((String) param, "password") ? ""
//                           : request.getParameter((String) param), 100));
//               }
//
//             
//               
//               log.setType( Log.TYPE_EXCEPTION);
//               log.setCreateBy(user);
//               log.setCreateDate(new Date());
//               log.setRemoteAddr(StringUtils.getRemoteAddr(request));
//               log.setUserAgent(request.getHeader("user-agent"));
//               log.setRequestUri(request.getRequestURI());
//               log.setMethod(request.getMethod());
//               log.setParams(params.toString());
//              
//           }
// 
//	}
//	/**
//	 * 得到方法参数的名称
//	 * @param cls
//	 * @param clazzName
//	 * @param methodName
//	 * @return
//	 * @throws NotFoundException
//	 */
//	private static String[] getFieldsName(Class cls, String clazzName, String methodName) throws NotFoundException{
//		ClassPool pool = ClassPool.getDefault();
//		//ClassClassPath classPath = new ClassClassPath(this.getClass());
//		ClassClassPath classPath = new ClassClassPath(cls);
//		pool.insertClassPath(classPath);
//		
//		CtClass cc = pool.get(clazzName);
//		CtMethod cm = cc.getDeclaredMethod(methodName);
//		MethodInfo methodInfo = cm.getMethodInfo();
//		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
//		LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
//		if (attr == null) {
//			// exception
//		}
//		String[] paramNames = new String[cm.getParameterTypes().length];
//		int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
//		for (int i = 0; i < paramNames.length; i++){
//			paramNames[i] = attr.variableName(i + pos);	//paramNames即参数名
//		}
//		return paramNames;
//	}
//	/**
//	 * 得到参数的值
//	 * @param obj
//	 * @throws IllegalAccessException 
//	 * @throws IllegalArgumentException 
//	 */
//	public static String getFieldsValue(Object obj,String name)   {
//	 
//			
//			
//			Field[] fields = obj.getClass().getDeclaredFields();
//			String typeName = obj.getClass().getName();
//			for (String t : types) {
//				if(t.equals(typeName))
//					return "";
//			}
//			StringBuilder sb = new StringBuilder();
//			sb.append("【");
//			for (Field f : fields) {
//				f.setAccessible(true);
//				try {
//					if(name.equals(f.getName())){
//					for (String str : types) {
//						if (f.getType().getName().equals(str)){
//							 return String.valueOf(f.get(obj)); 
//						}
//					}
//					}
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				}
//			}
//			sb.append("】");
//			return sb.toString();
//			 
//			
//	 
//	}
//	
//	/**
//	 * 得到用户的登陆信息--这个还未实现，只是在网上抄的一段
//	 * @param joinPoint
//	 * @throws Exception
//	 */
//	public void adminOptionContent(JoinPoint joinPoint) throws Exception {
//		StringBuffer rs = new StringBuffer();
//		String className = null;
//		int index = 1;
//		Object[] args = joinPoint.getArgs();
//
//		for (Object info : args) {
//			// 获取对象类型
//			className = info.getClass().getName();
//			className = className.substring(className.lastIndexOf(".") + 1);
//			rs.append("[参数" + index + "，类型：" + className + "，值：");
//			// 获取对象的所有方法
//			Method[] methods = info.getClass().getDeclaredMethods();
//			// 遍历方法，判断get方法
//			for (Method method : methods) {
//				String methodName = method.getName();
//				System.out.println(methodName);
//				// 判断是不是get方法
//				if (methodName.indexOf("get") == -1) {// 不是get方法
//					continue;// 不处理
//				}
//				Object rsValue = null;
//				try {
//					// 调用get方法，获取返回值
//					rsValue = method.invoke(info);
//					if (rsValue == null) {// 没有返回值
//						continue;
//					}
//				} catch (Exception e) {
//					continue;
//				}
//				// 将值加入内容中
//				rs.append("(" + methodName + " : " + rsValue + ")");
//			}
//			rs.append("]");
//			index++;
//		}
//		System.out.println(rs.toString());
//	}
//
//	private void getParamterName(String clazzName, String methodName)
//			throws NotFoundException {
//		ClassPool pool = ClassPool.getDefault();
//		ClassClassPath classPath = new ClassClassPath(this.getClass());
//		pool.insertClassPath(classPath);
//
//		CtClass cc = pool.get(clazzName);
//		CtMethod cm = cc.getDeclaredMethod(methodName);
//		MethodInfo methodInfo = cm.getMethodInfo();
//		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
//		LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
//				.getAttribute(LocalVariableAttribute.tag);
//		if (attr == null) {
//			// exception
//		}
//		String[] paramNames = new String[cm.getParameterTypes().length];
//		int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
//		for (int i = 0; i < paramNames.length; i++)
//			paramNames[i] = attr.variableName(i + pos);
//		// paramNames即参数名
//		for (int i = 0; i < paramNames.length; i++) {
//			System.out.println(paramNames[i]);
//		}
//	}
//}