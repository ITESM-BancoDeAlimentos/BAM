var addAttrToClass = function(elem, clss, attr, val) {
    return function() {
        $(elem + clss).attr(attr, val);
        $(clss).find(elem).attr(attr, val);
    };
};
var addPlaceholders = addAttrToClass('input', '.ui-placeholder-todos', 'placeholder', 'Todos');
