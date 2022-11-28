package ma.poc.gwt.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.web.bindery.requestfactory.shared.Locator;

import ma.poc.gwt.domain.User;

public class UserService extends Locator<User, String> {

	Map<String, List<User>> input = new HashMap<String, List<User>>();

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
		System.out.println("INIT getUserById");
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
			System.out.println("En attente " + out.isPending());
		} catch (RequestException e) {
			GWT.log("Erreur lors persist", e);
		}
		System.out.println("END getUserById");
		GWT.log("DONE");
		return new User();
	}

	public String upsert(User user) {
		System.out.println("INIT persist");
		System.out.println("DONE persist");
		return user.getId();
	}

	public Long countUsers() {
		return null;
	}

	public List<User> fromMap(int page, int max) {
		final String key = String.valueOf(page);
		if (input.containsKey(key)) {
			return input.get(key);
		}
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < max; i++) {
			User user = new User();
			user.setDateCreation(new Date());
			user.setFirstName(page + i + UUID.randomUUID().toString());
			user.setLastName(page + i + UUID.randomUUID().toString());
			user.setStatutMartial("Marié");
			users.add(user);
		}
		input.put(key, users);
		return users;
	}

	public List<User> getAllUsers(int page, int max) {
		return fromMap(page, max);
	}
}
