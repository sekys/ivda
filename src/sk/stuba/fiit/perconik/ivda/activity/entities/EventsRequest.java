package sk.stuba.fiit.perconik.ivda.activity.entities;

import com.ibm.icu.util.GregorianCalendar;
import sk.stuba.fiit.perconik.ivda.util.Configuration;
import sk.stuba.fiit.perconik.ivda.util.DateUtils;
import sk.stuba.fiit.perconik.ivda.util.UriUtils;
import sk.stuba.fiit.perconik.uaca.dto.EventDto;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by Seky on 22. 7. 2014.
 * Trieda ktora namapuje EventsURI vlastnosti a ulozi ich do URI.
 */
public final class EventsRequest extends EventsRequestParameters {
    private static final long serialVersionUID = 1794091480392084768L;

    public EventsRequest() {
    }

    @SuppressWarnings("OverlyBroadThrowsClause")
    public URI getURI() throws Exception {
        UriBuilder builder = UriBuilder.fromUri(Configuration.getInstance().getUacaLink());
        return UriUtils.addBeanProperties(builder, EventsRequestParameters.class, this).build();
    }


    public void setTime(GregorianCalendar from, GregorianCalendar to) {
        timeFrom = DateUtils.toString(from);
        timeTo = DateUtils.toString(to);
    }

    public void setType(URI type) {
        eventTypeUri = type.toString();
    }

    public void setType(EventDto event) {
        setType(event.getEventTypeUri());
    }

    public void setType(EventDto event, String subtype) {
        setType(UriBuilder.fromUri(event.getEventTypeUri()).path(subtype).build());
    }
}