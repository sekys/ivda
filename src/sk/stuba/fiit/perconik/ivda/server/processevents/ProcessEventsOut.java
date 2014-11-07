package sk.stuba.fiit.perconik.ivda.server.processevents;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import sk.stuba.fiit.perconik.ivda.activity.dto.EventDto;
import sk.stuba.fiit.perconik.ivda.server.Developers;
import sk.stuba.fiit.perconik.ivda.server.EventsUtil;
import sk.stuba.fiit.perconik.ivda.server.servlets.IvdaEvent;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Seky on 22. 7. 2014.
 * <p/>
 * Metoda spracovania udalosti, ktora je rozsirena o moznost ukladat zaujimave udalosti.
 */
public abstract class ProcessEventsOut extends ProcessEvents {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private JsonGenerator generator;

    protected ProcessEventsOut(OutputStream out) {
        JsonFactory factory = new JsonFactory();
        try {
            generator = MAPPER.getFactory().createGenerator(out, JsonEncoding.UTF8);
        } catch (IOException e) {
            LOGGER.error("error, ", e);
        }
    }

    @Override
    protected void started() {
        if (generator == null) {
            return;
        }
        try {
            // Zapisame az ked musime
            generator.writeStartArray();
        } catch (IOException e) {
            LOGGER.error("error, ", e);
            stop();
        }
    }

    @Override
    protected void finished() {
        if (generator == null) {
            return;
        }
        try {
            generator.writeEndArray();
            generator.flush();
            generator.close();
        } catch (IOException e) {
            LOGGER.error("error, ", e);
        }
    }

    protected void add(IvdaEvent event) {
        // Black out developer name
        String group = Developers.getInstance().blackoutName(event.getGroup());
        event.setGroup(group);
        try {
            generator.writeObject(event);
        } catch (IOException e) {
            LOGGER.error("error, ", e);
            stop();
        }
    }

    public void add(EventDto e, @Nullable String content, @Nullable Integer value, @Nullable Object metadata) {
        String group = EventsUtil.event2name(e);
        IvdaEvent event = new IvdaEvent();
        event.setId(e.getEventId());
        event.setStart(e.getTimestamp());
        event.setContent(content != null ? content : group);
        event.setGroup(group);
        event.setY(value);
        event.setMetadata(metadata);
        add(event);
    }
}

