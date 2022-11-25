package ma.poc.gwt.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.web.bindery.requestfactory.shared.Locator;

import ma.poc.gwt.domain.User;

public class UserService extends Locator<User, String> {

	private static final Logger log = Logger.getLogger(UserService.class.getName());

	Map<String, List<User>> input = new HashMap<String, List<User>>();

	@Override
	public User create(Class<? extends User> clazz) {
		return new User();
	}

	@Override
	public User find(Class<? extends User> clazz, String id) {
		return null;
	}

	@Override
	public Class<User> getDomainType() {
		return null;
	}

	@Override
	public String getId(User domainObject) {
		return null;
	}

	@Override
	public Class<String> getIdType() {
		return null;
	}

	@Override
	public Object getVersion(User domainObject) {
		return null;
	}

	public void persist() {
//		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "http://localhost:8086/rest/users");
//		builder.setHeader("Content-type", "application/x-www-form-urlencoded");
//		System.out.println(this);
//		try {
//			Request request = builder.sendRequest(this.toString(), new RequestCallback() {
//				public void onError(Request request, Throwable exception) {
//					GWT.log("Erreur lors du WS", exception);
//				}
//
//				public void onResponseReceived(Request request, Response response) {
//					if (200 == response.getStatusCode()) {
////		            updateTable(JsonUtils.safeEval(response.getText()));
//					} else {
//					}
//				}
//			});
//
//		} catch (RequestException e) {
//			GWT.log("Erreur lors du WS", e);
//		}
//		return null
		System.out.println("INIT PERSIST");
		GWT.log("INIT");
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
				"http://localhost:8086/rest/users/9c7e4957-51e0-4fa9-aae3-c1c9f412ad33");
		try {
			builder.send();
		} catch (RequestException e) {
			GWT.log("Erreur lors persist", e);
		}
		GWT.log("DONE");
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

//	public void persist(User user) {
//		System.out.println("INIT persist");
//		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "http://localhost:8086/rest/users/");
//		builder.setHeader("Accept", "application/json");
//		builder.setHeader("Content-type", "application/json");
//		builder.setCallback(new RequestCallback() {
//			@Override
//			public void onResponseReceived(Request request, Response response) {
//				System.out.println(response.getText());
//				System.out.println(response.getHeadersAsString());
//			}
//
//			@Override
//			public void onError(Request request, Throwable exception) {
//				System.out.println(exception);
//			}
//		});
//		try {
//			Request out = builder.send();
//			System.out.println("En attente " + out.isPending());
//		} catch (RequestException e) {
//			log.log(Level.INFO, "Erreur lors persist");
//		}
//		System.out.println("DONE persist");
//	}

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
