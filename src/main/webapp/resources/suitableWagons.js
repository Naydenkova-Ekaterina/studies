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

function getSuitableWagons() {
    orderId = $('#orderSelect').val();
    console.log(orderId);
    $.ajax({
        type: "GET",
        url: CONTEXT_PATH + "/wagon/getSuitable/" + orderId,
        success: function(data){
            console.log(data);
           // var wagons = $.parseJSON(data);
           // console.log(wagons);

        }
    });
    
}