package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.jblog.vo.UserVo;


public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1. Handler 종류 확인
		if(handler instanceof HandlerMethod == false) {
			//DefaultservletHandler가 처리하는 정적 자원
			return true;
		}
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		// 3. Handler Method의 @Auth 받아보기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 4. Handler Method에 @Auth가 없으면 Type에 붙어 있는지 확인 작업이 필요
		if(auth == null) {
			//과제
			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
		}
		
		// 5. Handler Method에 @Auth가 안붙어 있는 경우
		if(auth == null) {
			return true;
		}
		
		// 6. Handler Method에 @Auth가 부터 있기 떄문에 인증(Authentication)여부 확인
		HttpSession session = request.getSession();
		if(session == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		// 7.@Auth가 적용되어 있지만 인증이 되어 있지 않음
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
//		// 8. 권한 체크를 위해서 @Auth의 role값을 가져와야한다.
//		String role = auth.role();
//		String authUserRole = authUser.getRole();
//		
//		if(role.equals("ADMIN") && authUserRole.equals("USER")) {
//			response.sendRedirect(request.getContextPath());
//			return false;
//		}
		
		// 9. @Auth가 적용되어 있고 인증도 되어있음
		return true;
	}

}
