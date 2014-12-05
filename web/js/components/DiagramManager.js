/**
 * Created by Seky on 3. 12. 2014.
 */

DiagramManager = function () {
    this.lastId = 1;
    this.dom = $("#graphs");
    this.components = {};
    this.visibleComponents = {};
    this.registerComponents();
    this.lockedMove = true;

    // Sprav zoznam presuvatelny
    this.dom.sortable({
        //containment: "parent",
        handle: "h1",
        start: function (start, ui) {
            gGlobals.toolbar.showTrash();
        },
        stop: function (event, ui) {
            gGlobals.toolbar.hideTrash();
        }
    });
};

DiagramManager.prototype.redraw = function () {
    var com;
    var instance = this;
    Object.keys(this.visibleComponents).forEach(function (key) {
        com = instance.visibleComponents[key];
        com.redraw();
    });
};

DiagramManager.prototype.getGraphs = function () {
    var com;
    var graphs = [];
    var instance = this;
    Object.keys(this.components).forEach(function (key) {
        com = instance.components[key];
        graphs.push({id: key, name: com.getTitle()});
    });
    return graphs;
};

DiagramManager.prototype.find = function (id) {
    if (!this.components.hasOwnProperty(id)) {
        throw new Error("component nenajdeny");
    }
    return this.components[id];
};


DiagramManager.prototype.create = function (id, attributes) {
    // Pridaj do zoznamu viditelnych
    var com = jQuery.extend(true, {}, this.find(id));
    com.id = this.lastId;
    this.visibleComponents[com.id] = com;
    this.lastId++;

    // Zobraz to na webe
    com.init(attributes, this);
    this.dom.append(com.dom);
};

DiagramManager.prototype.destroy = function (id) {
    if (!this.visibleComponents.hasOwnProperty(id)) {
        throw new Error("component nenajdeny");
    }
    var com = this.visibleComponents[id];
    com.destroy();
    delete this.visibleComponents[id];
};

DiagramManager.prototype.register = function (component) {
    this.components[component.getName()] = component;
};

DiagramManager.prototype.registerComponents = function () {
    this.register(new CountEventsCom());
    this.register(new CountEventsDividedCom());
    this.register(new CodeChangesCom());
    this.register(new ActivityDynamicCom());
    this.register(new ActivityDetailCom());
    this.register(new activityTimeGroupedCom());
    this.register(new FilesModificationCom());
    this.register(new DomainVisitCom());
    this.register(new WebDurationComp());
    this.register(new BrowserVsRewrittenCodeCom());
    this.register(new DetailComponent());
};

DiagramManager.prototype.onMove = function (item, range) {
    if (!this.lockedMove) return;

    var com;
    var instance = this;
    Object.keys(this.visibleComponents).forEach(function (key) {
        com = instance.visibleComponents[key];
        if (com.id != item.id) {
            com.setRange(range);
        }
    });
};
