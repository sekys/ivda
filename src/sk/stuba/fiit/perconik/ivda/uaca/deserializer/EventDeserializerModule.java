package sk.stuba.fiit.perconik.ivda.uaca.deserializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import sk.stuba.fiit.perconik.ivda.uaca.dto.EventDto;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Created by Seky on 20. 7. 2014.
 * <p/>
 * Pomocou nasho modulu povieme Jacksonu aby pouzil nas vlastny deserializer.
 */
public final class EventDeserializerModule extends SimpleModule {
    private static final String ENTITIES_PACKAGE = "sk.stuba.fiit.perconik.ivda.uaca.dto";
    private static final String GROUP_ID = "sk.stuba.fiit.perconik.ivda"; // alias adresa projektu
    private final PolymorphicDeserializer<EventDto> deserializer;

    public EventDeserializerModule() {
        super("PolymorphicAnimalDeserializerModule", new Version(1, 0, 0, "1.0-SNAPSHOT", GROUP_ID, GROUP_ID));

        deserializer = new PolymorphicDeserializer<>(EventDto.class, "eventTypeUri");
        deserializer.pushSubTypesOf(ENTITIES_PACKAGE);
        addDeserializer(EventDto.class, deserializer);
        setMixInAnnotation(XMLGregorianCalendar.class, XMLGregorianCalendarMixIn.class);
    }

    /**
     * Trieda XMLGregorianCalendar obsahuje jeden setter navyse, pre rok.
     * Tento setter potrebujeme odignorovat. Da sa to pomocou MixIn anotacie.
     */
    abstract class XMLGregorianCalendarMixIn {
        @JsonIgnore
        public abstract void setYear(int year);
    }
}
