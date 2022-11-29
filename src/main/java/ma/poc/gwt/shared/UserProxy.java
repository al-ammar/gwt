package ma.poc.gwt.shared;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

import ma.poc.gwt.domain.User;
import ma.poc.gwt.server.UserService;

@ProxyFor(value = User.class, locator = UserService.class)
public interface UserProxy extends ValueProxy {

	String getLastName();

	String getFirstName();

	void setLastName(String s);

	void setFirstName(String s);

	void setGenre(String gendre);

	void setStatutMartial(String statutMartial);

	String getGenre();

	String getStatutMartial();

	void setDateCreation(Date date);

	Date getDateCreation();

	Integer getVersion();

	void setVersion(Integer version);

	void setId(String id);

	String getId();

	String getProfession();

	void setProfession(String profession);
}
