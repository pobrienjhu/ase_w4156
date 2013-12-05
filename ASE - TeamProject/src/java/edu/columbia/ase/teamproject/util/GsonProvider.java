package edu.columbia.ase.teamproject.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.json.EventTypeAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class GsonProvider.
 */
public class GsonProvider {

	/** The Constant gson. */
	private static final Gson gson = new GsonBuilder()
		.registerTypeAdapter(Event.class, new EventTypeAdapter())
		.create();

	/**
	 * Provide gson.
	 *
	 * @return the gson
	 */
	public Gson provideGson() {
		return gson;
	}

}
