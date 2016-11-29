
$(function () {
    $('.delete-button').on('click', function (event) {
        var buttonClassNames = event.target.className;
        var objName = buttonClassNames.includes('product') ? 'Product' : 'Category';

        if (!confirm("Delete " + objName + "\n\n  Are you sure?")) {
            return false;
        }
    });
});
