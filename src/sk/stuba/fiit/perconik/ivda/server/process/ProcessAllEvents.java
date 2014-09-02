package sk.stuba.fiit.perconik.ivda.server.process;

import sk.stuba.fiit.perconik.ivda.server.MyDataTable;
import sk.stuba.fiit.perconik.uaca.dto.EventDto;

import javax.annotation.concurrent.NotThreadSafe;


/**
 * Created by Seky on 7. 8. 2014.
 * <p>
 * Vypis vsetky eventy tak ako pridu.
 */
@NotThreadSafe
public final class ProcessAllEvents extends ProcessEventsToDataTable {
    @Override
    protected void proccessItem(EventDto event) {
        String action = event.getActionName();
        String description = "<span class=\"more\"><pre>"
                + event + "<br/>"
                + "</pre></span>";
        dataTable.add(event.getEventId(), event.getUser(), event.getTimestamp(), null, MyDataTable.ClassName.AVAILABLE, action, description);
    }
}
