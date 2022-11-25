package ma.poc.gwt.shared;

import com.google.web.bindery.requestfactory.shared.LoggingRequest;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface PocRequestFactory extends RequestFactory {

	UserRequest userRequest();

	LoggingRequest loggingRequest();
}
