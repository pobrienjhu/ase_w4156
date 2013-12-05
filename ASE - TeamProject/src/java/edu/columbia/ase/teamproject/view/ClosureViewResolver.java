package edu.columbia.ase.teamproject.view;

import java.util.Locale;

import org.springframework.web.servlet.View;

import com.tomakehurst.springclosuretemplates.web.mvc.ClosureTemplateViewResolver;

// TODO: Auto-generated Javadoc
/**
 * The Class ClosureViewResolver.
 */
public class ClosureViewResolver extends ClosureTemplateViewResolver {

	/** The Constant VIEW_PREFIX. */
	public static final String VIEW_PREFIX = "soy:";

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.web.servlet.view.UrlBasedViewResolver#loadView(java
	 * .lang.String, java.util.Locale)
	 */
	@Override
	protected View loadView(String viewName, Locale locale) throws Exception {
		if (!viewName.startsWith(VIEW_PREFIX)) {
			return null;
		}
		return buildView(viewName.substring(VIEW_PREFIX.length()));
	}

}
