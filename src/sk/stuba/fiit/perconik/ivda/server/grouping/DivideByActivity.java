package sk.stuba.fiit.perconik.ivda.server.grouping;

import sk.stuba.fiit.perconik.ivda.activity.dto.EventDto;
import sk.stuba.fiit.perconik.ivda.activity.dto.MonitoringStartedEventDto;
import sk.stuba.fiit.perconik.ivda.activity.dto.ProcessesChangedSinceCheckEventDto;
import sk.stuba.fiit.perconik.ivda.server.Catalog;
import sk.stuba.fiit.perconik.ivda.util.DateUtils;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.TimeUnit;

/**
 * Created by Seky on 21. 8. 2014.
 * <p/>
 * Skupiny rozdelujeme na zaklade casu alebo typu.
 */
@ThreadSafe
public class DivideByActivity implements IDividing {
    /**
     * Tzv. raz za minutu sa posle event, vtedy vieme urcite ze je aktivny
     */
    private static final long ACTIVITY_MIN_INTERVAL = TimeUnit.MINUTES.toMillis(1L);

    private final Catalog blacklist;

    public DivideByActivity() {
        blacklist = Catalog.Processes.BANNED.getList();
    }

    @Override
    public boolean canIgnore(EventDto event) {
        // Ignorujeme malo podstatne entity  ... startovanie monitorovania neznamena nic
        if (event instanceof MonitoringStartedEventDto) {
            return true;
        }

        // Dalsie entity znamenaju aktivitu, ProcessesChangedSinceCheckEventDto sa miesa spolu s ostatynmi aktivitamy preto ich ignorujeme
        if (event instanceof ProcessesChangedSinceCheckEventDto) {
            if (!blacklist.checkAtLeastOneDontExist(((ProcessesChangedSinceCheckEventDto) event).getStartedProcesses())) {
                // Nejde o zaujimavy proces, pravdepodobne nic nerobil
                return true;
            }
        }

        return false;
    }

    public long getActivityMinInterval() {
        return ACTIVITY_MIN_INTERVAL;
    }

    @Override
    public boolean canDivide(IGrouping group, EventDto event) {
        return divideByTime(group, event); // || divideByType(group, event);
    }

    /**
     * Ked interval medzi eventami je priliz velky, rozdel interval.
     *
     * @param actual
     * @return
     * @throws com.google.visualization.datasource.base.TypeMismatchException
     */
    protected boolean divideByTime(IGrouping group, EventDto actual) {
        return (DateUtils.diff(actual.getTimestamp(), group.getLastEvent().getTimestamp()) > ACTIVITY_MIN_INTERVAL);  // Je to velky casovy rozdiel
    }

    /**
     * Rozdel interval ked prvky su odlisneho typu
     *
     * @param actual
     * @return
     * @throws com.google.visualization.datasource.base.TypeMismatchException
     */
    /*protected boolean divideByType(IGrouping group, EventDto actual) {
        if (group.getLastEvent() instanceof WebEventDto && actual instanceof WebEventDto) {
            return false; // su rovnake
        } else if (group.getLastEvent() instanceof IdeEventDto && actual instanceof IdeEventDto) {
            return false; // su rovnake
        }
        // nie su rovnake alebo pojde o novy typ
        return true;
    } */
}
