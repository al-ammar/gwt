package ma.poc.gwt.client.widgets;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.server.testing.Gender;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import ma.poc.gwt.client.events.UpsertUserEvent;
import ma.poc.gwt.shared.PocRequestFactory;
import ma.poc.gwt.shared.UserProxy;
import ma.poc.gwt.shared.UserRequest;

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

	private final UserRequest context;

	private UserProxy userProxy;

	static void init(EventBus eventBus, PocRequestFactory requestFactory) {
		eventBus.addHandler(UpsertUserEvent.TYPE, new UpsertUserEvent.Handler() {
			@Override
			public void edit(RequestContext requestContext, UserProxy proxy) {
				log.log(Level.INFO, "!!!  Event consumed");
				new CreationUser((PocRequestFactory) requestContext.getRequestFactory(), proxy).edit();
			}

		});
	}

	public CreationUser(final PocRequestFactory requestFactory, final UserProxy user) {
		this.requestFactory = requestFactory;
		this.context = requestFactory.userRequest();
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
		if (this.userProxy == null) {
			this.userProxy = context.create(UserProxy.class);
		} else {
			this.userProxy = context.edit(this.userProxy);
		}
		this.userProxy.setFirstName(firstName.getValue());
		this.userProxy.setLastName(lastName.getValue());
		this.userProxy.setGenre(listGenre.getValue(listGenre.getSelectedIndex()));
		this.userProxy.setStatutMartial(listStatutMartial.getValue(listStatutMartial.getSelectedIndex()));
		log.log(Level.INFO, "Appel Persist" + this.userProxy.getFirstName());
		context.upsert(this.userProxy).fire(new Receiver<String>() {
			@Override
			public void onSuccess(String response) {
				log.log(Level.INFO, "Persisted" + response);
				dialogBox.hide();
			}

			@Override
			public void onFailure(ServerFailure error) {
				log.log(Level.SEVERE, error.getStackTraceString());
				log.log(Level.INFO, error.getMessage());
				// TODO launch event to display error
			}
		});
	}

	void edit() {
		log.log(Level.INFO, "edit edit");
		if (this.userProxy != null) {
			firstName.setValue(this.userProxy.getFirstName());
			lastName.setValue(this.userProxy.getLastName());
			profession.setValue(this.userProxy.getProfession());
			// TODO matching index with value from DTO
			listGenre.setSelectedIndex(1);
			listStatutMartial.setSelectedIndex(1);
		}
		dialogBox.center();
	}
}
