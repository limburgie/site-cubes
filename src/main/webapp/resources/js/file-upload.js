function styleUpload() {
    if (!$(".ui-fileupload-buttonbar").hasClass("btn-group")) {
        $(".ui-fileupload-buttonbar").addClass("btn-group");
        $(".ui-fileupload-choose").addClass("btn btn-primary");
        $(".ui-fileupload-choose .ui-icon").addClass("fa fa-folder-open-o");
        $(".ui-fileupload-upload").addClass("btn btn-default");
        $(".ui-fileupload-upload .ui-icon").addClass("fa fa-upload");
        $(".ui-fileupload-cancel").addClass("btn btn-default");
        $(".ui-fileupload-cancel .ui-icon").addClass("fa fa-ban");
    }
}