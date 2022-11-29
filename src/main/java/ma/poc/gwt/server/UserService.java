package ma.poc.gwt.server;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.web.bindery.requestfactory.shared.Locator;

import ma.poc.gwt.domain.User;

public class UserService extends Locator<User, String> {

	private static final Logger logger = Logger.getLogger(UserService.class.getName());

	private static final Map<String, List<User>> input = new HashMap<String, List<User>>();

	@Override
	public User create(Class<? extends User> clazz) {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		return user;
	}

	@Override
	public User find(Class<? extends User> clazz, String id) {
		return null;
	}

	@Override
	public Class<User> getDomainType() {
		return User.class;
	}

	@Override
	public String getId(User domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(User domainObject) {
		return domainObject.getVersion();
	}

	public User getUserById(String id) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "http://localhost:8086/rest/users/");
		builder.setCallback(new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				System.out.println(response.getText());
				System.out.println(response.getHeadersAsString());
			}

			@Override
			public void onError(Request request, Throwable exception) {
				System.out.println(exception);
			}
		});
		try {
			Request out = builder.send();
		} catch (RequestException e) {
			GWT.log("Erreur lors persist", e);
		}
		return new User();
	}

	public String upsert(final User user) {
		requireNonNull(user);
		boolean exist = false;
		int lastIndex = 0;
		final Iterator<List<User>> iterator = input.values().iterator();
		while (iterator.hasNext()) {
			final List<User> users = iterator.next();
			Iterator<User> it = users.iterator();
			while (it.hasNext()) {
				User toUpdate = it.next();
				if (StringUtils.equalsIgnoreCase(toUpdate.getId(), user.getId())) {
					exist = true;
					toUpdate.setFirstName(user.getFirstName());
					toUpdate.setLastName(user.getLastName());
					toUpdate.setGenre(user.getGenre());
					toUpdate.setProfession(user.getProfession());
					toUpdate.setStatutMartial(user.getStatutMartial());
				}
			}
			lastIndex++;
		}
		if (!exist) {
			input.put(String.valueOf(lastIndex), Arrays.asList(user));
		}
		return user.getId();
	}

	public Long countUsers() {
		return null;
	}

	public List<User> fromMap(final int page, final int max) {
		requireNonNull(page, "page ne doit pas être null");
		requireNonNull(max, "max ne doit pas être null");
		logger.log(Level.INFO, "Map de donnée" + UserService.input);
		final String key = String.valueOf(page);
		if (input.containsKey(key)) {
			return input.get(key);
		}
		final List<User> users = new ArrayList<User>();
		for (int i = 0; i < max; i++) {
			User user = new User();
			user.setId(UUID.randomUUID().toString());
			user.setDateCreation(new Date());
			user.setFirstName(page + i + UUID.randomUUID().toString());
			user.setLastName(page + i + UUID.randomUUID().toString());
			user.setStatutMartial("C");
			users.add(user);
		}
		input.put(key, users);
		return users;
	}

	public List<User> getAllUsers(int page, int max) {
		return fromMap(page, max);
	}
}
