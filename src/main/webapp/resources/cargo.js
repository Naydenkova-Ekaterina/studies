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

    $.get(CONTEXT_PATH+ "/cargo/edit/" + id, function(data, status){
        console.log(data);
        console.log(data.src.city.id);
        console.log(data.dst.city.id);

        $('#UpdReg').val(data.id);
        $('#UpdShift').val(data.name);
        $('#UpdCap').val(data.weight);
        $('#UpdStat').val(data.status);
        $('#UpdSrc').val(data.src.id);
        $('#UpdDst').val(data.dst.id);
     //   $('#UpdOrder').val(data.order.id);


    });

}