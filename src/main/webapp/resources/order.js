

CONTEXT_PATH = $('#contextPathHolder').attr('data-contextPath');

function seeDetails(id) {
    $.ajax({
        type: "GET",
        url: CONTEXT_PATH + "/orders/getOrderRoute/" + id,
        success: function(data){
            console.log(data);
            $('#orderRoute').html(data);
            $('#orderRoute').css({'font-size':'18px'})
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
            $("#cargoes_table").html("");


            var trHTML = '';
            $.each(data, function (i, item) {
                trHTML += '<tr><td>' + '</td><td>' + item.id + '</td><td>' + item.name + '</td><td>'
                    + item.weight +'</td><td>' + item.status + '</td><td>' + item.src.city.name + '</td><td>' + item.dst.city.name + '</td></tr>';
            });
            $('#cargoes_table').append(trHTML);

        }
    });

    $.ajax({
        type: "GET",
        url: CONTEXT_PATH + "/orders/getOrderDrivers/" + id,
        success: function(data){
            console.log(data);
            $("#drivers_table").html("");


            var trHTML = '';
            $.each(data, function (i, item) {
                trHTML += '<tr><td>' + '</td><td>' + item.id + '</td><td>' + item.name + '</td><td>'
                    + item.surname +'</td><td>' + item.status + '</td><td>' + item.city.name + '</td>'
                    // item.wagon.id + '</td><td>' + item.order.id + '</td><td>' + item.user.id + '</td>'
                     +'</tr>';
            });
            $('#drivers_table').append(trHTML);

        }
    });
}

var orderToAddCargo;

function addCargo(id) {
    orderToAddCargo = id;
    $.ajax({
        type: "GET",
        url: CONTEXT_PATH + "/cargo/listFreeCargoes",
        success: function(data){
            console.log(data);
            //var opts = $.parseJSON(data);
            //console.log(opts);
            $.each(data, function(i, d) {
                $('#addCargoModal_selectAddCargoes').append('<option value="' + d.id + '">' + d.id + '</option>');
            });
        }
    });
}

function addCargoToExistingOrder() {

    id = $('#addCargoModal_selectAddCargoes').val();
    $.ajax({
        type: "POST",
        url: CONTEXT_PATH + "/order/addCargoToExistingOrder/" + orderToAddCargo + "/" + id,
        success: function(data){
            console.log(data);
            //var opts = $.parseJSON(data);
            //console.log(opts);

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

