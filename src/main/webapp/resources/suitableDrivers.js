var CONTEXT_PATH = $('#contextPathHolder').attr('data-contextPath');

$.ajax({
    type: "GET",
    url: CONTEXT_PATH + "/orders/listOrdersDTO",
    success: function(data){
        //var opts = $.parseJSON(data);
        $.each(data, function(i, d) {
            $('#orderSelect').append('<option value="' + d.id + '">' + d.id + '</option>');
        });
    }
});

function getSuitableDrivers() {
    orderId = $('#orderSelect').val();
    console.log(orderId);
    $.ajax({
        type: "GET",
        url: CONTEXT_PATH + "/driver/getSuitable/" + orderId,
        success: function(data){
            console.log(data);
            // var wagons = $.parseJSON(data);
            // console.log(wagons);


            var trHTML = '';
            $.each(data, function (i, item) {
                trHTML += '<tr><td>' + '</td><td>' + item.id + '</td><td>' + item.name + '</td><td>'
                    + item.surname +'</td><td>' + item.status + '</td><td>' + item.city.name
                    + '</td>'
                    // item.wagon.id + '</td><td>' + item.order.id + '</td><td>' + item.user.id + '</td>'
                    + ' <td><button value="' + item.id + '" onclick="setDriverForOrder(this)">Choose driver</button>' +'</td></tr>';
            });
            $('#records_table').append(trHTML);

        }
    });

}

function setDriverForOrder(obj) {
    orderId = $('#orderSelect').val();
    console.log(orderId);
    console.log(obj.value);
    $.ajax({
        type: "POST",
        url: CONTEXT_PATH + "/driver/setOrder/" + obj.value + "/" + orderId,
        success: function(data){
            console.log(data);
            // var wagons = $.parseJSON(data);
            // console.log(wagons);


        }
    });
}