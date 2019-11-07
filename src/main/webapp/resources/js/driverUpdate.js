CONTEXT_PATH = $('#contextPathHolder').attr('data-contextPath');


// $.ajax({
//     type: "GET",
//     url: CONTEXT_PATH + "/cities/listCitiesDTO",
//     success: function(data){
//         console.log(data);
//         $.each(data, function(i, d) {
//             $('#UpdCity').append('<option value="' + d.id + '">' + d.name + '</option>');
//         });
//     }
// });

$.ajax({
    type: "GET",
    url: CONTEXT_PATH + "/driver/getDriverCargoes",
    success: function(data){
        console.log(data);
        $("#cargoes_table").html("");


        var trHTML = '';
        var index = 0;
        $.each(data, function (i, item) {
            trHTML = '<tr><td>' + '</td><td>' + item.id + '</td><td>' + item.name + '</td><td>'
                + item.weight +'</td><td>' + item.src.city.name  + '</td><td>' + item.dst.city.name +
                '</td><td><select class="cargoStatus"><option value="prepared">Prepared</option><option value="shipped">Shipped</option><option value="delivered">Delivered</option>' +
                '</select><td><button value="' + item.id + '" onclick="changeCargoStatus(' + item.id + ',' +  index + ')">Change cargo status</button>' + '</td></tr>';
            //$("#cargoes_table select:last-child").val(item.status);
            $('#cargoes_table').append(trHTML);
            $('.cargoStatus').eq(index).val(item.status);
            index++;
            //document.getElementsByClassName("cargoStatus").item(i).value = item.status;


        });
      //  $('#cargoes_table').append(trHTML);
        //document.getElementsByClassName("cargoStatus").item(i).

    }
});

function setIdForUpdate(id) {

    var CONTEXT_PATH = $('#contextPathHolder').attr('data-contextPath');

    $.get(CONTEXT_PATH+ "/driver/edit/" + id, function(data, status){
        $('#UpdReg').val(data.id);
        $('#UpdShift').val(data.name);
        $('#UpdCap').val(data.surname);
        $('#UpdStat').val(data.status);


    });

}

function changeDriverStatus() {
    console.log("ssfafasf")
    var status =  $('#UpdStat').val();
    $.ajax({
        type: "POST",
        url: CONTEXT_PATH + "/driver/changeDriverStatus/" + status,
        success: function(data){
            console.log(data);

        }
    });
}

function changeCargoStatus(cargoId, i) {
    status = $('.cargoStatus').eq(i).val();
    $.ajax({
        type: "POST",
        url: CONTEXT_PATH + "/cargo/changeCargoStatus/" + status + '/' + cargoId,
        success: function(data){
            console.log(data);

        }
    });
    
}

function startShift() {
    $.ajax({
        type: "GET",
        url: CONTEXT_PATH + "/driver/startShift" ,
        success: function(data){
            console.log(data);

        }
    });
}

function endShift() {
    $.ajax({
        type: "GET",
        url: CONTEXT_PATH + "/driver/endShift" ,
        success: function(data){
            console.log(data);

        }
    });
}