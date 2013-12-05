package edu.columbia.ase.teamproject.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.Gson;

import edu.columbia.ase.teamproject.persistence.dao.VoteDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteOptionDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.Vote;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.persistence.domain.VoteOption;
import edu.columbia.ase.teamproject.util.GsonProvider;
import edu.columbia.ase.teamproject.view.NavigationMenuEntry;
import edu.columbia.ase.teamproject.view.NavigationMenuSection;

// TODO: Auto-generated Javadoc
/**
 * The Class VoteService.
 */
public class VoteService {

	/** The vote option dao. */
	@Autowired
	VoteOptionDao voteOptionDao;

	/** The vote dao. */
	@Autowired
	VoteDao voteDao;

	/** The gson provider. */
	@Autowired
	GsonProvider gsonProvider;

	/**
	 * Verify votes.
	 *
	 * @param event
	 *            the event
	 * @param voteList
	 *            the vote list
	 * @throws Exception
	 *             the exception
	 */
	@VisibleForTesting
	@Transactional
	void verifyVotes(Event event, List<Long> voteList) throws Exception {
		if (event.getVoteCategories().size() != voteList.size()
				|| event.getEndTime().isBeforeNow())
			throw new Exception("Invalid voting!");
		// making sure user votes in each category
		boolean found = false;
		for (VoteCategory vc : event.getVoteCategories()) {
			found = false;
			for (VoteOption vo : vc.getVoteOptions()) {
				if (voteList.contains(vo.getId())) {
					found = true;
				}
			}
			if (!found) {
				throw new Exception("Must vote in all categories!");
			}
		}

	}

	/**
	 * Adds the votes.
	 *
	 * @param event
	 *            the event
	 * @param voteList
	 *            the vote list
	 * @param user
	 *            the user
	 * @throws Exception
	 *             the exception
	 */
	@Transactional
	public void addVotes(Event event, List<Long> voteList, UserAccount user)
			throws Exception {
		verifyVotes(event, voteList);

		for (int i = 0; i < voteList.size(); i++) {
			addVote(voteList.get(i), user);
		}
	}

	/**
	 * Adds the vote.
	 *
	 * @param id
	 *            the id
	 * @param user
	 *            the user
	 * @throws Exception
	 *             the exception
	 */
	@VisibleForTesting
	@Transactional
	void addVote(long id, UserAccount user) throws Exception {

		VoteOption voteOption = voteOptionDao.find(id);

		if (voteOption == null) {
			throw new Exception("Invalid vote option id (" + id + ")");
		}

		for (Vote v2 : user.getVotes()) {

			if (v2.getVoteOption().getVoteCategory().getId() == voteOption
					.getVoteCategory().getId()) {

				v2.getVoteOption().removeVote(v2);
				user.removeVote(v2);

				v2.setVoteOption(voteOption);

				voteDao.update(v2);

				v2.getVoteOption().addVote(v2);
				user.addVote(v2);

				return;
			}

		}

		Vote v = new Vote(voteOption, user);
		v = voteDao.add(v);
		voteOption.addVote(v);
		user.addVote(v);

	}

	/**
	 * Gets the results.
	 *
	 * @param event
	 *            the event
	 * @return the results
	 */
	public String getResults(Event event) {

		List<NavigationMenuSection> nms = new ArrayList<NavigationMenuSection>();
		for (VoteCategory v : event.getVoteCategories()) {

			NavigationMenuSection voteCatSection = new NavigationMenuSection(
					v.getDescription());
			for (VoteOption vo : v.getVoteOptions()) {
				voteCatSection.addEntry(new NavigationMenuEntry(v
						.getCategoryName(), Integer.toString(vo.getVotes()
						.size()), vo.getOptionName()));
			}
			nms.add(voteCatSection);
		}
		Gson gson = gsonProvider.provideGson();
		return (gson.toJson(nms));

	}

}
