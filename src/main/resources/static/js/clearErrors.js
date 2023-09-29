$(document).ready(function() {
    $("#userName, #email, #password, #confirm, #title, #description, #date").on("input", function() {
        var errorsId = $(this).attr("id") + "Errors";
        $("#" + errorsId).html(""); // Clear the error messages
    });
});