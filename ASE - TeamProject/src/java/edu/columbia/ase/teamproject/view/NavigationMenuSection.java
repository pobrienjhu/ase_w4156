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

// TODO: Auto-generated Javadoc
/**
 * Represents a section of the right hand side navigation bar.
 */
public class NavigationMenuSection implements Map<String, Object> {

	/** The section name. */
	private final String sectionName;

	/** The entries. */
	private List<Object> entries;

	/** The Constant NAVIGATION_SECTION_KEYS. */
	public static final Set<String> NAVIGATION_SECTION_KEYS = ImmutableSet
			.<String> of("sectionName", "entries");

	/**
	 * Instantiates a new navigation menu section.
	 *
	 * @param sectionName
	 *            the name of the section
	 */
	public NavigationMenuSection(String sectionName) {
		Preconditions.checkArgument(!sectionName.isEmpty());
		this.sectionName = sectionName;
		this.entries = Lists.newArrayList();
	}

	/**
	 * Gets the section name.
	 *
	 * @return the section name
	 */
	public String getSectionName() {
		return sectionName;
	}

	/**
	 * Adds the entry.
	 *
	 * @param entry
	 *            the entry to add to this section
	 */
	public void addEntry(NavigationMenuEntry entry) {
		Preconditions.checkNotNull(entry);
		this.entries.add(entry);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object put(String key, Object value) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<Object> values() {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public Map<String, String> remove(Object key) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#get(java.lang.Object)
	 */
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<String> keySet() {
		return NAVIGATION_SECTION_KEYS;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		return NAVIGATION_SECTION_KEYS.size();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#entrySet()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		Map.Entry<String, Object> sectionEntry = Maps.immutableEntry(
				"sectionName", (Object) sectionName);
		Map.Entry<String, Object> linkListEntry = Maps.immutableEntry(
				"entries", (Object) entries);
		return Sets.newHashSet(sectionEntry, linkListEntry);
	}
}
