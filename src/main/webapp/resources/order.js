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

CONTEXT_PATH = $('#contextPathHolder').attr('data-contextPath');

function seeDetails(id) {
    $.ajax({
        type: "GET",
        url: CONTEXT_PATH + "/orders/getOrderRoute/" + id,
        success: function(data){
            console.log(data);
           // var opts = $.parseJSON(data);
            // $.each(opts, function(i, d) {
            //     $('#orderSelect').append('<option value="' + d.id + '">' + d.id + '</option>');
            // });
        }
    });

    $.ajax({
        type: "GET",
        url: CONTEXT_PATH + "/orders/getOrderCargoes/" + id,
        success: function(data){
            console.log(data);
            // var opts = $.parseJSON(data);
            // $.each(opts, function(i, d) {
            //     $('#orderSelect').append('<option value="' + d.id + '">' + d.id + '</option>');
            // });
        }
    });

    $.ajax({
        type: "GET",
        url: CONTEXT_PATH + "/orders/getOrderDrivers/" + id,
        success: function(data){
            console.log(data);
           // var opts = $.parseJSON(data);
            // $.each(opts, function(i, d) {
            //     $('#orderSelect').append('<option value="' + d.id + '">' + d.id + '</option>');
            // });
        }
    });
}

function addOrder() {
   // var CONTEXT_PATH = $('#contextPathHolder').attr('data-contextPath');

    $.ajax({
        type: "GET",
        url: CONTEXT_PATH + "/cargo/listFreeCargoes",
        success: function(data){
            console.log(data);
            //var opts = $.parseJSON(data);
            //console.log(opts);
             $.each(data, function(i, d) {
                 $('#selectAddCargoes').append('<option value="' + d.id + '">' + d.id + '</option>');
             });
        }
    });
}

function setIdForRemove(id) {
    document.getElementById('formRemove').action += id;
}

function setIdForUpdate(id) {



    $.get(CONTEXT_PATH+ "/driver/edit/" + id, function(data, status){
        $('#UpdReg').val(data.id);
        $('#UpdShift').val(data.name);
        $('#UpdCap').val(data.surname);
        $('#UpdStat').val(data.status);
        $('#UpdCity').val(data.city.id);
        $('#UpdWagon').val(data.wagon.id);
        $('#UpdOrder').val(data.order.id);
        $('#UpdUser').val(data.user.id);


    });

}