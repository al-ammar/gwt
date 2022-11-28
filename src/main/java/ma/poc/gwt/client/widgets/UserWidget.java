package ma.poc.gwt.client.widgets;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.RequestContext;

import ma.poc.gwt.client.events.UpsertUserEvent;
import ma.poc.gwt.shared.PocRequestFactory;
import ma.poc.gwt.shared.UserProxy;
import ma.poc.gwt.shared.UserRequest;

public class UserWidget extends Composite {
	private static final Logger log = Logger.getLogger(UserWidget.class.getName());

	interface Binder extends UiBinder<Widget, UserWidget> {
	}

	private class FirstNameColumn extends Column<UserProxy, String> {
		public FirstNameColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(UserProxy object) {
			return object.getFirstName();
		}
	}

	private class LastNameColumn extends Column<UserProxy, String> {
		public LastNameColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(UserProxy object) {
			return object.getLastName();
		}
	}

	private class StatutMartialColumn extends Column<UserProxy, String> {
		public StatutMartialColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(UserProxy object) {
			return object.getStatutMartial();
		}
	}

	private class DateCreationColumn extends Column<UserProxy, Date> {
		public DateCreationColumn() {
			super(new DateCell());
		}

		@Override
		public Date getValue(UserProxy object) {
			return object.getDateCreation();
		}
	}

	@UiField
	DockLayoutPanel rootPanel;

	@UiField
	DockLayoutPanel headerPanel;

	@UiField(provided = true)
	SimplePager pagination = new SimplePager();

	@UiField(provided = true)
	DataGrid<UserProxy> table;

	@UiField
	Button createUser;

	@UiField
	Label label = new Label("Aucun résultat");

	Column<UserProxy, String> firstNameColumn = new FirstNameColumn();

	SingleSelectionModel<UserProxy> selectionModel = new SingleSelectionModel<UserProxy>();

	private final PocRequestFactory factory;
	private final RequestContext requestContext;
	private final EventBus eventBus;

	public UserWidget(PocRequestFactory factory, EventBus eventBus) {
		this.eventBus = eventBus;
		this.factory = factory;
		this.requestContext = factory.userRequest();
		table = new DataGrid<UserProxy>(10);
		initWidget(GWT.<Binder>create(Binder.class).createAndBindUi(this));
		CreationUser.init(eventBus, factory);
		table.addColumn(firstNameColumn, "Prénom");
		table.addColumn(new LastNameColumn(), "Nom");
		table.addColumn(new StatutMartialColumn(), "Situation Familiale");
		table.addColumn(new DateCreationColumn(), "Date de création");
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		table.setSelectionModel(selectionModel);
		fetch(0);
		selectionModel.addSelectionChangeHandler(new Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				UserProxy user = selectionModel.getSelectedObject();
				if (user == null) {
					return;
				}
				log.log(Level.INFO, "user selectione" + user.getId());
				selectionModel.setSelected(user, false);
				UserWidget.this.eventBus.fireEvent(new UpsertUserEvent(requestContext, user));
			}
		});
	}

	private void fetch(final int page) {
		((UserRequest) requestContext).getAllUsers(page, 15).fire(new Receiver<List<UserProxy>>() {
			@Override
			public void onSuccess(List<UserProxy> response) {
				pagination.setPageSize(10);
				table.setRowData(page, response);
				table.setPageStart(page);
				table.setRowCount(100, false);
			}
		});
	}

	@UiHandler("table")
	void OnRangeChange(RangeChangeEvent e) {
		log.log(Level.INFO, "DataGrid: Next Range " + e.getNewRange().getStart());
		log.log(Level.INFO, "DataGrid: Length " + e.getNewRange().getLength());
		Range range = e.getNewRange();
		int start = range.getStart();
		fetch(start);
	}

	@UiHandler(value = "createUser")
	void initCreate(ClickEvent event) {
		eventBus.fireEvent(new UpsertUserEvent(requestContext, null));
	}
}
