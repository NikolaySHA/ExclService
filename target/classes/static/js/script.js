$(document).ready(function () {
    var csrfToken = $('meta[name="csrf-token"]').attr('content');

    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': csrfToken
        }
    });

    $('#paintDetails').change(function () {
        var detailsCount = $(this).val();
        console.log("Selected details count: ", detailsCount); // Логване на избраната стойност

        if (detailsCount) {
            $(this).css('background-color', 'yellow');


        }
    });
});
