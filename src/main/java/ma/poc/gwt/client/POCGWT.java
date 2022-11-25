package ma.poc.gwt.client;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryLogHandler;
import com.google.web.bindery.requestfactory.shared.LoggingRequest;

import ma.poc.gwt.client.widgets.UserWidget;
import ma.poc.gwt.shared.PocRequestFactory;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class POCGWT implements EntryPoint {

	private static final Logger log = Logger.getLogger(POCGWT.class.getName());

	@UiField(provided = true)
	UserWidget rootWidget;

	EventBus bus = new SimpleEventBus();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Init Request Factory
		final PocRequestFactory factory = GWT.create(PocRequestFactory.class);
		factory.initialize(bus);
		// Init Logging
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable e) {
				log.log(Level.SEVERE, e.getMessage(), e);
			}
		});
		RequestFactoryLogHandler.LoggingRequestProvider provider = new RequestFactoryLogHandler.LoggingRequestProvider() {
			@Override
			public LoggingRequest getLoggingRequest() {
				return factory.loggingRequest();
			}
		};
		Logger.getLogger(POCGWT.class.getName())
				.addHandler(new RequestFactoryLogHandler(provider, Level.FINEST, new ArrayList<String>()));

		// Init Widget
		UserWidget userWidget = new UserWidget(bus, factory);
		RootLayoutPanel.get().add(userWidget);
	}
}
