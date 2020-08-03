$(document).ready(function(){

conversionTableConfig = {
      "columns": [
                          { "data": "originalCurrency", "sClass": "user_id"  },
                          { "data": "targetCurrency" , "sClass": "user_login"  },
                         { "data": "originalAmount" , "sClass": "user_login"  },
                         { "data": "receivedAmount" , "sClass": "user_login"  },
                         { "data": "date" , "sClass": "user_login"  }
                      ],
                      "language": {
                                  "url": "//cdn.datatables.net/plug-ins/1.10.21/i18n/Russian.json"
                              },
                          "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "Все"]],
    };

  var conversion_history_table = $('#conversion_history').DataTable(conversionTableConfig);

 $('#date').datepicker({
                                   showOn: "both",
                                   buttonText: "<i class='fa fa-calendar'></i>",
                                   format: "yyyy-mm-dd",
                                   autoclose: true
                                  });

var request   = $.ajax({
        url:          '/api/currencies' ,
        cache:        false,
        contentType: "application/json",
        type:         'GET',
        success: function (currencies) {
                $.each(currencies, function(key, value) {
                    $('.currencies')
                        .append($("<option></option>")
                                   .attr("value",value.id)
                                   .text(value.id + ' ( ' + value.name + ' )'));
               });
                     },
        error: function (e) {
 }
      });


    function fetch_data(date='', from_currency, to_currency)  {
conversionTableConfig.ajax = {
"processing" : true,
      "serverSide" : true,
        "url" : "/api/operations/from/" + from_currency + "/to/" + to_currency + "/" + date ,
                    "type": "GET",
                    "dataSrc": "",
      };

     var dataTable = $('#conversion_history').DataTable(conversionTableConfig);
    }

    $('#search').click(function(){
     var date = $('#date').val();
     var from_currency = $('#from_currency_history').val();
     var to_currency = $('#to_currency_history').val();
     if(date != '')
     {
      $('#conversion_history').DataTable().destroy();
      fetch_data(date, from_currency, to_currency);
     }
     else
     {
      alert("Введите, пожалуйста, дату");
     }
    });

});

const to_ammount = document.getElementById('to_ammount');
const exchange = document.getElementById('exchange');

$(document).on('click', '#convert', function(e){
const from_currency = document.getElementById('from_currency').value;
const from_ammountEl = document.getElementById('from_ammount').value;
const to_currency = document.getElementById('to_currency').value;
 var request   = $.ajax({
        url:          '/api/from/' + from_currency + "/to/" + to_currency + "/" + from_ammountEl ,
        cache:        false,
        contentType: "application/json",
        type:         'GET',
        success: function (result) {
              $('#to_ammount').val(result);
                     },
        error: function (e) {

 }
      });
});
