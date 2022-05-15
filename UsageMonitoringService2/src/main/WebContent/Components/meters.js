$(document).ready(function() {
    if ($('#alertSuccess').text().trim() == "") {
        $('#alertSuccess').hide();
    }

    $('#alertError').hide();
})

// SAVE
$(document).on("click","#btnSave", function(event) {
    // Clear alerts
    $("#alertSuccess").text(""); 
    $("#alertSuccess").hide(); 
    $("#alertError").text(""); 
    $("#alertError").hide();

    // Form validation
    var status = validateMeterForm(); 
    if (status != true) 
    { 
        $("#alertError").text(status); 
        $("#alertError").show(); 
        return; 
    } 

    // if hidMeterIDSave value is null set as POST else set as PUT
    var type = ($("#hidMeterIDSave").val() == "") ? "POST" : "PUT";

    // ajax communication
    $.ajax({
        url: "MetersAPI",
        type: type,
        data: $("#formMeter").serialize(),
        dataType: "text",
        complete: function(response, status) {
            onMeterSaveComplete(response.responseText, status);
        }
    });
});

// after completing save request
function onMeterSaveComplete(response, status) {

    if (status == "success") { //if the response status is success
        var resultSet = JSON.parse(response);

        if (resultSet.status.trim() === "success") { //if the json status is success
            //display success alert
            $("#alertSuccess").text("Successfully saved");
            $("#alertSuccess").show();
    
            //load data in json to html
            $("#divMetersGrid").html(resultSet.data);

        } else if (resultSet.status.trim() === "error") { //if the json status is error
            //display error alert
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") { 
        //if the response status is error
        $("#alertError").text("Error while saving");
        $("#alertError").show();
    } else { 
        //if an unknown error occurred
        $("#alertError").text("Unknown error occurred while saving");
        $("#alertError").show();
    } 

    //resetting the form
    $("#hidMeterIDSave").val("");
    $("#formMeter")[0].reset();
}

// UPDATE
//to identify the update button we didn't use an id we used a class
$(document).on("click", ".btnUpdate", function(event) 
{ 
    //get meter id from the data-meterid attribute in update button
    $("#hidMeterIDSave").val($(this).data('meterid')); 
    //get data from <td> element
    $("#meterCode").val($(this).closest("tr").find('td:eq(0)').text()); 
    $("#houseownerName").val($(this).closest("tr").find('td:eq(1)').text()); 
    $("#singleUnitPrice").val($(this).closest("tr").find('td:eq(2)').text()); 
    $("#unitsAmount").val($(this).closest("tr").find('td:eq(3)').text());
    $("#totalPrice").val($(this).closest("tr").find('td:eq(4)').text());
}); 

// DELETE
$(document).on("click",".btnRemove", function(event) {
    // ajax communication
    $.ajax({
        url: "MetersAPI",
        type: "DELETE",
        data: "meterID=" + $(this).data("meterid"),
        dataType: "text",
        complete: function(response, status) {
            onMeterDeleteComplete(response.responseText, status);
        }
    });
});

// after completing delete request
function onMeterDeleteComplete(response, status) {

    if (status == "success") { //if the response status is success
        var resultSet = JSON.parse(response);

        if (resultSet.status.trim() === "success") { //if the json status is success
            //display success alert
            $("#alertSuccess").text("Successfully deleted");
            $("#alertSuccess").show();
    
            //load data in json to html
            $("#divMetersGrid").html(resultSet.data);

        } else if (resultSet.status.trim() === "error") { //if the json status is error
            //display error alert
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") { 
        //if the response status is error
        $("#alertError").text("Error while deleting");
        $("#alertError").show();
    } else { 
        //if an unknown error occurred
        $("#alertError").text("Unknown error occurred while deleting");
        $("#alertError").show();
    } 
}

// VALIDATION
function validateMeterForm() { 
    // CODE 
    if ($("#meterCode").val().trim() == "") 
    { 
        return "Insert Meter Code."; 
    } 
    

    // HOUSE OWNER'S NAME 
    if ($("#houseownerName").val().trim() == "") 
    { 
        return "Insert House Owner Name."; 
    } 
    

    // SINGLE UNIT PRICE
    if ($("#singleUnitPrice").val().trim() == "") 
    { 
        return "Insert Single Unit Price."; 
    } 
    
    // is numerical value 
    var tmpUnitPrice = $("#singleUnitPrice").val().trim(); 
    if (!$.isNumeric(tmpUnitPrice)) 
    { 
        return "Insert a numerical value for Single Unit Price."; 
    } 
    
    // convert to decimal price 
    $("#singleUnitPrice").val(parseFloat(tmpUnitPrice).toFixed(2));


    // UNITS AMOUNT
    if ($("#unitsAmount").val().trim() == "") 
    { 
        return "Insert Units Amount."; 
    } 
    
    // is numerical value 
    var tmpAmount = $("#unitsAmount").val().trim(); 
    if (!$.isNumeric(tmpAmount)) 
    { 
        return "Insert a numerical value for Units Amount."; 
    } 
    
    
    // TOTAL PRICE
    /*if ($("totalPrice").val().trim() == "") 
    { 
        return "Insert Total Price."; 
    } 

    // is numerical value 
    var tmpTotalPrice = $("#totalPrice").val().trim(); 
    if (!$.isNumeric(tmpTotalPrice)) 
    { 
        return "Insert a numerical value for Total Price."; 
    }*/ 
    
    
    return true; 
} 