package edu.columbia.ase.teamproject.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.json.EventTypeAdapter;

public class GsonProvider {

	private static final Gson gson = new GsonBuilder()
		.registerTypeAdapter(Event.class, new EventTypeAdapter())
		.create();

	public Gson provideGson() {
		return gson;
	}

}
