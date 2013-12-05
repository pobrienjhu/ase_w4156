package edu.columbia.ase.teamproject.persistence.domain.json;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.persistence.domain.VoteOption;
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
    public void testSerializeAndDeserializeEvent() {
        UserAccount admin = new UserAccount(AccountType.OPENID, "http://localhost", "displayName",
                null, "user@example.com", new ArrayList<Permission>());
        DateTime now = DateTime.now();
        Event event = new Event(null, admin, "eventName", "eventDescription", EventType.PRIVATE,
                now, now.plus(Duration.standardDays(1)));
        event.setId(123L);

        String json = gson.toJson(event)
                .replace("[],", "[],\"userEmails\":[\"user@example.com\"],");
        System.out.println(json);
        Event deserialized = gson.fromJson(json, Event.class);
        System.out.println(deserialized);

        assertEquals(event.getId(), deserialized.getId());
        assertEquals(event.getName(), deserialized.getName());
        assertEquals(event.getDescription(), deserialized.getDescription());
        assertEquals(event.getEventType(), deserialized.getEventType());
        assertEquals(event.getEventType(), deserialized.getEventType());
        assertEquals(event.getStartTime(), deserialized.getStartTime());
        assertEquals(event.getEndTime(), deserialized.getEndTime());
        // TODO (pames): add checks for serialized member lists (user accounts
        // and categories).
    }

    @Test
    public void testSerializeAndDeserializeVoteCategories() {
        UserAccount admin = new UserAccount(AccountType.OPENID, "http://localhost", "displayName",
                null, "user@example.com", new ArrayList<Permission>());
        DateTime now = DateTime.now();
        Event event = new Event(null, admin, "eventName", "eventDescription", EventType.PRIVATE,
                now, now.plus(Duration.standardDays(1)));
        event.setId(123L);

        VoteCategory firstCategory = new VoteCategory(event, "First Category",
                "First Category Description");
        firstCategory.setId(456L);
        VoteOption firstCategoryFirstOption = new VoteOption(firstCategory, "Option 1-1");
        firstCategoryFirstOption.setId(4561L);
        VoteOption firstCategorySecondOption = new VoteOption(firstCategory, "Option 1-2");
        firstCategorySecondOption.setId(4562L);
        VoteOption firstCategoryThirdOption = new VoteOption(firstCategory, "Option 1-3");
        firstCategoryThirdOption.setId(4563L);
        firstCategory.addVotingOption(firstCategoryFirstOption);
        firstCategory.addVotingOption(firstCategorySecondOption);
        firstCategory.addVotingOption(firstCategoryThirdOption);

        VoteCategory secondCategory = new VoteCategory(event, "Second Category",
                "First Category Description");
        secondCategory.setId(789L);
        VoteOption secondCategoryFirstOption = new VoteOption(firstCategory, "Option 2-1");
        secondCategoryFirstOption.setId(7891L);
        VoteOption secondCategorySecondOption = new VoteOption(firstCategory, "Option 2-2");
        secondCategorySecondOption.setId(7892L);
        secondCategory.addVotingOption(secondCategoryFirstOption);
        secondCategory.addVotingOption(secondCategorySecondOption);

        event.setVoteCategories(ImmutableList.<VoteCategory> of(firstCategory, secondCategory));

        String json = gson.toJson(event, Event.class);
        Event deserialized = gson.fromJson(json, Event.class);
        assertEquals(2, deserialized.getVoteCategories().size());

        assertEquals(Long.valueOf(456L), deserialized.getVoteCategories().get(0).getId());
        assertEquals(3, deserialized.getVoteCategories().get(0).getVoteOptions().size());
        assertEquals(Long.valueOf(4561L), deserialized.getVoteCategories().get(0).getVoteOptions()
                .get(0).getId());
        assertEquals(Long.valueOf(4562L), deserialized.getVoteCategories().get(0).getVoteOptions()
                .get(1).getId());
        assertEquals(Long.valueOf(4563L), deserialized.getVoteCategories().get(0).getVoteOptions()
                .get(2).getId());

        assertEquals(Long.valueOf(789L), deserialized.getVoteCategories().get(1).getId());
        assertEquals(2, deserialized.getVoteCategories().get(1).getVoteOptions().size());
        assertEquals(Long.valueOf(7891L), deserialized.getVoteCategories().get(1).getVoteOptions()
                .get(0).getId());
        assertEquals(Long.valueOf(7892L), deserialized.getVoteCategories().get(1).getVoteOptions()
                .get(1).getId());

    }
}
