package edu.columbia.ase.teamproject.view;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Represents a section of the right hand side navigation bar. 
 */
public class NavigationMenuSection implements Map<String, Object> {
	private final String sectionName;
	private List<Object> entries;

	public static final Set<String> NAVIGATION_SECTION_KEYS =
			ImmutableSet.<String>of("sectionName", "entries");

	/**
	 * @param sectionName the name of the section
	 */
	public NavigationMenuSection(String sectionName) {
		Preconditions.checkArgument(!sectionName.isEmpty());
		this.sectionName = sectionName;
		this.entries = Lists.newArrayList();
	}

	public String getSectionName() {
		return sectionName;
	}

	/**
	 * @param entry the entry to add to this section
	 */
	public void addEntry(NavigationMenuEntry entry) {
		Preconditions.checkNotNull(entry);
		this.entries.add(entry);
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object put(String key, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Object> values() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String> remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(Object keyObject) {
		if (!NAVIGATION_SECTION_KEYS.contains(keyObject)) {
			return null;
		}

		String key = (String) keyObject;
		if (key.equals("sectionName")) {
			return sectionName;
		} else if (key.equals("entries")) {
			return entries;
		}

		throw new IllegalArgumentException();
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public Set<String> keySet() {
		return NAVIGATION_SECTION_KEYS;
	}

	@Override
	public int size() {
		return NAVIGATION_SECTION_KEYS.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		Map.Entry<String, Object> sectionEntry =
				Maps.immutableEntry("sectionName", (Object) sectionName); 
		Map.Entry<String, Object> linkListEntry =
				Maps.immutableEntry("entries", (Object) entries);
		return Sets.newHashSet(sectionEntry, linkListEntry);
	}
}
