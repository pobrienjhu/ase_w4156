package edu.columbia.ase.teamproject.view;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import edu.columbia.ase.teamproject.controllers.ControllerHelper;

/**
 * A convenience class that implements partial {@link Map} functionality.
 * Designed to be used by
 * {@link ControllerHelper#createBaseModel(javax.servlet.http.HttpSession)}
 * and other controllers to create lists of menu items that go into a
 * {@link NavigationMenuSection}.
 */
public class NavigationMenuEntry implements Map<String, String> {

	private final String uri;
	private final String id;
	private final String text;

	public static final Set<String> NAVIGATION_MENU_KEYS =
			ImmutableSet.<String>of("uri", "id", "text");

	/**
	 * Create an entry in the navigation menu list.  This should be added to
	 * an {@link NavigationMenuSection}.
	 *
	 * @param uri the URI to add to the 'href' attribute of the tag.
	 * @param id the value of the 'id' attribute 
	 * @param text the text to wrap in the anchor tag.
	 */
	public NavigationMenuEntry(@Nullable String uri, @Nullable String id,
			String text) {
		Preconditions.checkArgument(text.length() != 0);
		if (uri == null) {
			this.uri = "#";
		} else {
			this.uri = uri;
		}
		if (id == null) {
			this.id = "";
		} else {
			this.id = id;
		}
		this.text = text;
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(Object key) {
		return NAVIGATION_MENU_KEYS.contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Map.Entry<String, String>> entrySet() {
		return Sets.newHashSet(Maps.immutableEntry("uri", uri),
				Maps.immutableEntry("id", id),
				Maps.immutableEntry("text", text));
	}

	@Override
	public String get(Object keyObject) {
		if (!NAVIGATION_MENU_KEYS.contains(keyObject)) {
			return null;
		}

		String key = (String) keyObject;
		if (key.equals("uri")) {
			return uri;
		} else if (key.equals("id")) {
			return id;
		} else if (key.equals("text")) {
			return text;
		}
		throw new IllegalArgumentException();
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public Set<String> keySet() {
		return NAVIGATION_MENU_KEYS;
	}

	@Override
	public String put(String arg0, String arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return NAVIGATION_MENU_KEYS.size();
	}

	@Override
	public Collection<String> values() {
		return Sets.newHashSet(uri, id, text);
	}

}
