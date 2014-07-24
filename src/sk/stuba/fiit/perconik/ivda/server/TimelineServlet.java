package sk.stuba.fiit.perconik.ivda.server;

import com.google.visualization.datasource.DataSourceServlet;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.query.Query;
import com.ibm.icu.util.GregorianCalendar;
import org.apache.log4j.Logger;
import sk.stuba.fiit.perconik.ivda.Client.EventsRequest;
import sk.stuba.fiit.perconik.ivda.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by Seky on 17. 7. 2014.
 */
public final class TimelineServlet extends DataSourceServlet {
    private static final Logger logger = Logger.getLogger(TimelineServlet.class.getName());

    @Override
    public DataTable generateDataTable(Query query, HttpServletRequest req) {
        // Pohyb okna nema vplyv na zmenu datumu, cize tensie okno zobrazuje to iste len ide o responzivny dizajn
        // Vplyv na rozsah ma jedine  zoom
        // Cize musime vypocitat sirku okna a poslat to sem
        GregorianCalendar start, end;
        Integer width;
        try {
            start = DateUtils.fromString(req.getParameter("start"));
            end = DateUtils.fromString(req.getParameter("end"));
            width = Integer.valueOf(req.getParameter("width"));
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
        }
        logger.info("Start: " + DateUtils.toString(start) +
                " end:" + DateUtils.toString(end) +
                " width:" + width);

        EventsRequest request = new EventsRequest().set(start, end).set("steltecia");
        ProcessEventsToDataTable process = new ProcessEventsToDataTable(request);
        return process.getDataTable();
    }

    @Override
    protected boolean isRestrictedAccessMode() {
        return false;
    }
}