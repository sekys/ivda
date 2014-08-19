
function getDatatableURL(start, end, width) {
    var restURL = "datatable?";
    var parameters = $.param({
        start: gDateFormater.format(start),
        end: gDateFormater.format(end),
        width: width
    });
    return restURL + parameters;
}

function getEventEntityURL(eventID) {
    var restURL = "rest/timeline/event?";
    var parameters = $.param({
        id: eventID
    });
    return restURL + parameters;
}

function getDiffURL() {
    var restURL = "rest/timeline/filediff?";
    var parameters = $.param({
        version: 31517,
        old: 33408,
        path: "sk.stuba.fiit.perconik.eclipse/src/sk/stuba/fiit/perconik/eclipse/jdt/core/JavaElementEventType.java"
    });
    return restURL + parameters;
}

function handleServiceResponse(response) {
    if (response.isError()) {
        alert('Error in query: ' + response.getMessage() + ' ' + response.getDetailedMessage());
        return;
    }
    gData = response.getDataTable();
    drawChart(gData);
    gTimeline.draw(gData);
    gDataTable.draw(gData, {
        allowHtml: true,
        showRowNumber: true,
        page: "enable",
        pageSize: 20
    });
}


function loadRange(start, end, width) {
    var query = new google.visualization.Query( getDatatableURL(start, end, width) );
    query.send(handleServiceResponse);
}