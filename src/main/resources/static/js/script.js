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

            $.ajax({
                url: 'http://localhost:8080//next-available-date',
                type: 'GET',
                data: {
                    detailsCount: detailsCount
                },
                success: function (response) {
                    console.log("Response received: ", response); // Логване на получените данни
                    $('#date').val(response); // Поставяне на датата в полето
                },
                error: function (xhr, status, error) {
                    console.error('Error fetching next available date:', error);
                }
            });
        }
    });
});
