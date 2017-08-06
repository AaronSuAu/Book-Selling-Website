(function ($) {
    var methods = {

        method : function () {
            var bookids=[];
            var $boxes = $("input[name=books]:checked");
            if($boxes.length == 0){
                return;
            }
            $boxes.each(function(){
                var bookid = $(this).parent().next().find("p:contains('bookid')").html();
                bookid = bookid.substring(7,bookid.length);
                console.log("test"+bookid);
                bookids.push(bookid);
            });
            return bookids; //return element for chaining
        },
         method2 : function () {
            alert('test2 succeeded!');
             return this;
        }
    };

    $.fn.test = function (method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist');
        }
    }

})(jQuery);