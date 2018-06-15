package com.gospell.aas.common.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.gospell.aas.common.errorcode.AppUtils;
import com.gospell.aas.common.errorcode.ErrorCode4App;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.entity.sys.User;

/**
 * 登录过滤
 * 
 * @author van
 * @date 2015年04月07日 15:12:17
 */
public class AppLoginFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		User user = UserUtils.getUser();
		String requestURI = request.getRequestURI();
		if (org.apache.commons.lang3.StringUtils.isNoneBlank(requestURI)
				&& requestURI.startsWith("/park/rs")
				&& !requestURI.equals("/park/rs/app/login")) {

			if (user == null || org.apache.commons.lang3.StringUtils.isBlank(user.getId())
					|| user.getId() == "null") {

				Map<String, Object> map = AppUtils.GI.errorCodeReturnMap(
						ErrorCode4App.ERROR_NO_401,
						ErrorCode4App.ERROR_NO_401_VALUE);
				
				response.getWriter().write(JsonMapper.getInstance().toJson(map));

			} else {
				filterChain.doFilter(request, response);
			}
		} else {
			filterChain.doFilter(request, response);
		}

	}

}