package tr.org.linux.kamp.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import tr.org.linux.kamp.thymeleaf.annotation.DefaultLayout;
import tr.org.linux.kamp.thymeleaf.annotation.Layout;
import tr.org.linux.kamp.thymeleaf.annotation.NoLayout;

public class ThymeleafLayoutInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView == null || !modelAndView.hasView() || modelAndView.getView() != null) {
			return;
		}

		String originalViewName = modelAndView.getViewName();
		if (originalViewName == null || isRedirectOrForward(originalViewName)) {
			return;
		}
		
		String layoutName = getLayoutName(handler);
		if(layoutName.equals(NoLayout.NAME)) {
			return;
		}
		
		modelAndView.setViewName(layoutName);
		modelAndView.addObject("layout_view", originalViewName);
	}

	private String getLayoutName(Object handler) {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Layout layout = getMethodOrTypeAnnotation(handlerMethod);
		if(layout == null) {
			return DefaultLayout.NAME;
		} else {
			return layout.value();
		}
	}

	private Layout getMethodOrTypeAnnotation(HandlerMethod handlerMethod) {
		Layout layout = handlerMethod.getMethodAnnotation(Layout.class);
		if(layout == null) {
			layout = handlerMethod.getBeanType().getAnnotation(Layout.class);
		}
		return layout;
	}

	private boolean isRedirectOrForward(String viewName) {
		return viewName.startsWith("redirect:") || viewName.startsWith("forward:");
	}

}
