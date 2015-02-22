/**
 * PrimeFaces Dashboard Widget
 */
PrimeFaces.widget.Dashboard = PrimeFaces.widget.BaseWidget.extend({

    init: function(cfg) {
        this._super(cfg);

        this.cfg.connectWith = '.ui-dashboard-column';
        this.cfg.placeholder = 'portlet-placeholder';
        this.cfg.forcePlaceholderSize = true;
        this.cfg.revert=true;
        this.cfg.handle='.ui-panel-titlebar';

        var _self = this;

        if(this.cfg.behaviors) {
            var reorderBehavior = this.cfg.behaviors['reorder'];

            if(reorderBehavior) {
                this.cfg.update = function(e, ui) {

                    if(this === ui.item.parent()[0]) {
                        var panelEl = ui.item;
                        var fromColumnEl = ui.sender;
                        var toColumnEl = panelEl.closest(".ui-dashboard-column");
                        var toDashboardEl = toColumnEl.closest(".ui-dashboard");


                        var itemIndex = toColumnEl.find(".ui-panel").filter(':not(script):visible').index(panelEl),
                            receiverColumnIndex = toDashboardEl.find(".ui-dashboard-column").index(toColumnEl);

                        var ext = {
                            params: [
                                {name: _self.id + '_reordered', value: true},
                                {name: _self.id + '_widgetId', value: panelEl.attr('id')},
                                {name: _self.id + '_itemIndex', value: itemIndex},
                                {name: _self.id + '_receiverColumnIndex', value: receiverColumnIndex}
                            ]
                        }

                        if(fromColumnEl) {
                            var fromDashboardEl = fromColumnEl.closest(".ui-dashboard");
                            ext.params.push({name: _self.id + '_senderColumnIndex', value: fromDashboardEl.find(".ui-dashboard-column").index(fromColumnEl)});
                        }

                        reorderBehavior.call(_self, ext);
                    }

                };

                this.cfg.start = function(e, ui) {
                    var height = Math.floor(ui.item.height()) + 25;
                    $(".ui-dashboard-column").css("padding-bottom", height+"px !important");
                    $("body").addClass("dragging");
                }

                this.cfg.stop = function(e, ui) {
                    $(".ui-dashboard-column").css("padding-bottom", "5px !important");
                    $("body").removeClass("dragging");
                }
            }
        }

        $(this.jqId + ' .ui-dashboard-column').sortable(this.cfg);
    }

});