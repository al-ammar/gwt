package ma.poc.gwt.client.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.web.bindery.requestfactory.shared.Receiver;

import ma.poc.gwt.shared.PocRequestFactory;
import ma.poc.gwt.shared.UserProxy;

public class UserWidget extends Composite {
	private static final Logger log = Logger.getLogger(UserWidget.class.getName());

	private int indexPage;

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

	private class StatutMartielColumn extends Column<UserProxy, String> {
		public StatutMartielColumn() {
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

	private final PocRequestFactory factory;
	private final EventBus eventBus;

	private final SingleSelectionModel<UserProxy> selectionModel = new SingleSelectionModel<UserProxy>();
	Column<UserProxy, String> firstNameColumn = new FirstNameColumn();

	public UserWidget(EventBus bus, PocRequestFactory factory) {
		this.eventBus = bus;
		this.factory = factory;
		table = new DataGrid<UserProxy>(5);
		initWidget(GWT.<Binder>create(Binder.class).createAndBindUi(this));
		firstNameColumn.setSortable(true);
		table.addColumn(firstNameColumn, "Prénom");
		table.addColumn(new LastNameColumn(), "Nom");
		table.addColumn(new StatutMartielColumn(), "Situation Familiale");
		table.addColumn(new DateCreationColumn(), "Date de création");
		table.setEmptyTableWidget(label.asWidget());
//		table.setRowCount(5);
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		table.setSelectionModel(selectionModel);
		fetch(0);
	}

	private void fetch(final int page) {
		factory.userRequest().getAllUsers(page, 10).fire(new Receiver<List<UserProxy>>() {
			@Override
			public void onSuccess(List<UserProxy> response) {
				table.setRowData(page, response);
				table.setPageStart(page);
				table.setRowCount(10 + response.size(), false);
				ListDataProvider<UserProxy> dataProvider = new ListDataProvider<UserProxy>();
				dataProvider.addDataDisplay(table);
				List<UserProxy> list = dataProvider.getList();
				for (UserProxy t : response) {
					list.add(t);
				}
				ListHandler<UserProxy> handerTri = new ListHandler<UserProxy>(list);
				handerTri.setComparator(firstNameColumn, new Comparator<UserProxy>() {
					@Override
					public int compare(UserProxy o1, UserProxy o2) {
						if (o1 == o2) {
							return 0;
						}
						if (o1 != null) {
							return (o2 != null) ? o1.getFirstName().compareTo(o2.getFirstName()) : 1;
						}
						return -1;
					}
				});
				table.addColumnSortHandler(handerTri);
				table.getColumnSortList().push(firstNameColumn);
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

}
