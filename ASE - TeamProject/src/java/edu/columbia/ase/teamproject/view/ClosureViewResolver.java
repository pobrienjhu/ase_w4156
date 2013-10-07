package edu.columbia.ase.teamproject.view;

import java.util.Locale;

import org.springframework.web.servlet.View;

import com.tomakehurst.springclosuretemplates.web.mvc.ClosureTemplateViewResolver;

public class ClosureViewResolver extends ClosureTemplateViewResolver {

	public static final String VIEW_PREFIX = "soy:";

	@Override
	protected View loadView(String viewName, Locale locale) throws Exception {
		if (!viewName.startsWith(VIEW_PREFIX)) {
			return null;
		}
		return buildView(viewName.substring(VIEW_PREFIX.length()));
	}

}
