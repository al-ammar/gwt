package ma.poc.gwt.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import ma.poc.gwt.shared.PocRequestFactory;
import ma.poc.gwt.shared.UserProxy;
import ma.poc.gwt.shared.UserRequest;

public class CreationUser extends Composite {

	interface MyUiBinder extends UiBinder<Widget, CreationUser> {
	}

	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField
	ListBox listStatutMartial;

	@UiField
	ListBox listGenre;

	@UiField
	Button resetButton;

	@UiField
	Button submitButton;

	@UiField
	TextBox lastName;

	@UiField
	TextBox firstName;

	@UiField
	TextBox profession;

	public CreationUser() {
		initWidget(uiBinder.createAndBindUi(this));
		listStatutMartial.addItem("---", "");
		listStatutMartial.addItem("Celibataire", "C");
		listStatutMartial.addItem("Marie", "M");
		listGenre.addItem("---", "");
		listGenre.addItem("Female", "F");
		listGenre.addItem("Male", "M");

	}

	@UiHandler(value = "resetButton")
	void handleClick(ClickEvent e) {
		lastName.setText(null);
		firstName.setText(null);
		profession.setText(null);
		listGenre.setSelectedIndex(0);
		listStatutMartial.setSelectedIndex(0);

	}

	@UiHandler(value = "submitButton")
	void handleCreation(ClickEvent e) {
		final EventBus eventBus = new SimpleEventBus();
		PocRequestFactory requestFactory = GWT.create(PocRequestFactory.class);
		requestFactory.initialize(eventBus);
		firstName.setText("ZZZZZZZZZzz");

		UserRequest request = requestFactory.userRequest();
		UserProxy user = request.create(UserProxy.class);
		user.setFirstName("XXx");
		request.persist().using(user).fire();
//		request.getUserById("").fire();
	}
}
