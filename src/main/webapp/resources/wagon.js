$(document).ready(function() {
    $('[data-toggle="tooltip"]').tooltip();

    var checkbox = $('table tbody input[type="checkbox"]');
    $("#selectAll").click(function () {
        if (this.checked) {
            checkbox.each(function () {
                this.checked = true;
            });
        } else {
            checkbox.each(function () {
                this.checked = false;
            });
        }
    });
    checkbox.click(function () {
        if (!this.checked) {
            $("#selectAll").prop("checked", false);
        }
    });

});

function setIdForRemove(id) {
    document.getElementById('formRemove').action += id;
}

function setIdForUpdate(id) {

    var CONTEXT_PATH = $('#contextPathHolder').attr('data-contextPath');

    $.get(CONTEXT_PATH+ "/wagon/edit/" + id, function(data, status){

        $('#UpdReg').val(data.id);
        $('#UpdShift').val(data.shiftSize);
        $('#UpdCap').val(data.capacity);
        $('#UpdStat').val(data.status);
        $('#UpdCity').val(data.city.id);
    });

}