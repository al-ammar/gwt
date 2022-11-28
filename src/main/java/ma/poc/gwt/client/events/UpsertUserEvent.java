package ma.poc.gwt.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.requestfactory.shared.RequestContext;

import ma.poc.gwt.shared.PocRequestFactory;
import ma.poc.gwt.shared.UserProxy;

public class UpsertUserEvent extends GwtEvent<UpsertUserEvent.Handler> {
	public static final Type<Handler> TYPE = new Type<Handler>();

	private final RequestContext request;
	private final UserProxy user;

	public UpsertUserEvent(UserProxy user) {
		this(null, user);
	}

	public UpsertUserEvent(RequestContext request, UserProxy user) {
		this.user = user;
		this.request = request;
	}

	public interface Handler extends EventHandler {
		void edit(RequestContext requestContext, UserProxy proxy);
	}

	@Override
	public Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.edit(request, user);
	}
}
