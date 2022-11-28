package ma.poc.gwt.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

import ma.poc.gwt.server.MyServiceLocator;
import ma.poc.gwt.server.UserService;

@Service(value = UserService.class, locator = MyServiceLocator.class)
public interface UserRequest extends RequestContext {

	Request<Long> countUsers();

	Request<UserProxy> getUserById(String id);

	Request<String> upsert(UserProxy userProxy);

	Request<List<UserProxy>> getAllUsers(int page, int max);
}
