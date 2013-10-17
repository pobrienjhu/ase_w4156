package edu.columbia.ase.teamproject.persistence.domain.json;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.google.gson.Gson;

import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.EventType;
import edu.columbia.ase.teamproject.security.Permission;
import edu.columbia.ase.teamproject.util.GsonProvider;

/*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
*/
@RunWith(BlockJUnit4ClassRunner.class)
public class EventTypeAdapterTest {

	Gson gson;

	@Before
	public void setUp() {
		// TODO(pames): replace this w/ an @AutoWired gsonProvider once
		// the spring config works.
		gson = new GsonProvider().provideGson();
	}

	@Test
	public void testSerializeandDeserializeEvent() {
		UserAccount admin = new UserAccount(AccountType.OPENID,
				"http://localhost", "displayName", null,
				new ArrayList<Permission>());
		DateTime now = DateTime.now(DateTimeZone.UTC);
		Event event = new Event(admin, "eventName", "eventDescription",
				EventType.PRIVATE, now, now.plus(Duration.standardDays(1)));
		event.setId(123L);
		String json = gson.toJson(event);
		Event deserialized = gson.fromJson(json, Event.class);

		assertEquals(event.getId(), deserialized.getId());
		assertEquals(event.getName(), deserialized.getName());
		assertEquals(event.getDescription(),
				deserialized.getDescription());
		assertEquals(event.getEventType(), deserialized.getEventType());
		assertEquals(event.getEventType(), deserialized.getEventType());
		assertEquals(event.getStartTime(), deserialized.getStartTime());
		assertEquals(event.getEndTime(), deserialized.getEndTime());
		// TODO (pames): add checks for serialized member lists (user accounts
		// and categories).
	}
}
