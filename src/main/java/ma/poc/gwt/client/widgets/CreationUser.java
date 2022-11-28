package ma.poc.gwt.client.widgets;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import ma.poc.gwt.client.events.UpsertUserEvent;
import ma.poc.gwt.shared.PocRequestFactory;
import ma.poc.gwt.shared.UserProxy;

public class CreationUser {

	private static final Logger log = Logger.getLogger(CreationUser.class.getName());

	interface MyUiBinder extends UiBinder<Widget, CreationUser> {
	}

	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField
	DialogBox dialogBox;

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

	private final PocRequestFactory requestFactory;

	private final UserProxy userProxy;

	static void init(EventBus eventBus, PocRequestFactory requestFactory) {
		eventBus.addHandler(UpsertUserEvent.TYPE, new UpsertUserEvent.Handler() {
			@Override
			public void edit(PocRequestFactory requestFactory, UserProxy proxy) {
				log.log(Level.INFO, "!!!  Event consumed");
				new CreationUser(requestFactory, proxy).edit();
			}

		});
	}

	public CreationUser(final PocRequestFactory requestFactory, final UserProxy user) {
		this.requestFactory = requestFactory;
		this.userProxy = user;
//		initWidget(uiBinder.createAndBindUi(this));
		uiBinder.createAndBindUi(this);
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
		dialogBox.hide();
	}

	@UiHandler(value = "submitButton")
	void handleCreation(ClickEvent e) {
		UserProxy user = requestFactory.userRequest().create(UserProxy.class);
		user.setFirstName(firstName.getValue());
		user.setLastName(lastName.getValue());
		user.setGenre(listGenre.getValue(listGenre.getSelectedIndex()));
		user.setStatutMartial(listStatutMartial.getValue(listStatutMartial.getSelectedIndex()));
		requestFactory.userRequest().persist().using(user).fire();
	}

	void edit() {
		log.log(Level.INFO, "edit edit");
		dialogBox.center();
	}

}
