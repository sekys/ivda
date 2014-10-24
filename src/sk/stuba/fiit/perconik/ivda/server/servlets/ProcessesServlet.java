package sk.stuba.fiit.perconik.ivda.server.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Logger;
import sk.stuba.fiit.perconik.ivda.server.processes.PerUserProcesses;
import sk.stuba.fiit.perconik.ivda.server.processes.Process;
import sk.stuba.fiit.perconik.ivda.util.Configuration;
import sk.stuba.fiit.perconik.ivda.util.GZIP;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seky on 22. 10. 2014.
 * <p/>
 * Servlet pre vypisanie procesov pre daneho pouzivatel v danom case.
 */
public class ProcessesServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ProcessesServlet.class.getName());
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ImmutableMap<String, PerUserProcesses> processes;

    static {
        Configuration.getInstance();
        File processesFile = new File(Configuration.CONFIG_DIR, "processes.gzip");
        try {
            processes = (ImmutableMap<String, PerUserProcesses>) GZIP.deserialize(processesFile);
        } catch (Exception e) {
            throw new RuntimeException(processesFile + " file is missing.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            ProcessesRequest request = new ProcessesRequest(req);
            LOGGER.info("Request: " + request);
            ImmutableList<Process> list = findProcesses(request);
            resp.setContentType(MediaType.APPLICATION_JSON);
            ServletOutputStream stream = resp.getOutputStream();
            MAPPER.writeValue(stream, list);
        } catch (Exception e) {
            throw new WebApplicationException(e);
        }
    }

    private static ImmutableList<Process> findProcesses(ProcessesRequest req) {
        List<Process> saved = new ArrayList<>();
        for (String developer : req.getDevelopers()) {
            PerUserProcesses info = processes.get(developer);
            if (info == null) {
                continue;
            }

            for (Process p : info.getFinished()) {
                if (p.isOverlaping(req.getStart(), req.getEnd())) {
                    saved.add(p);
                }
            }
        }
        fixOverLapingProccesses(saved);
        return ImmutableList.copyOf(saved);
    }

    private static void fixOverLapingProccesses(List<Process> list) {
        for (Process a : list) {
            Integer number = 1;
            for (Process b : list) {
                if (a != b && a.getName().equals(b.getName()) && a.isOverlaping(b)) {
                    b.setName(b.getName() + number);
                    number++;
                }
            }
        }
    }
}
