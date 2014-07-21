package sk.stuba.fiit.perconik.ivda.deserializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gratex.perconik.useractivity.app.dto.EventDto;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Created by Seky on 20. 7. 2014.
 */
public final class EventDeserializerModule extends SimpleModule {
    private PolymorhicDeserializer<EventDto> deserializer;

    abstract class XMLGregorianCalendarMixIn {
        @JsonIgnore
        public abstract void setYear(int year);
    }

    public EventDeserializerModule() {
        super("PolymorphicAnimalDeserializerModule", new Version(1, 0, 0, "1.0-SNAPSHOT", "sk.stuba.fiit.perconik.ivda", "sk.stuba.fiit.perconik.ivda"));

        deserializer = new PolymorhicDeserializer<>(EventDto.class, "EventTypeUri");
        deserializer.pushSubTypesOf("com.gratex.perconik.useractivity.app.dto");
        addDeserializer(EventDto.class, deserializer);
        setMixInAnnotation(XMLGregorianCalendar.class, XMLGregorianCalendarMixIn.class);
    }
}