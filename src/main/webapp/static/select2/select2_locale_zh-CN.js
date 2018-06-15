/**
 * Select2 Chinese translation
 */
(function ($) {
    "use strict";
    $.extend($.fn.select2.defaults, {
        formatNoMatches: function () { return accipiter.getLang_(jsLang,"formatNoMatches"); },
        formatInputTooShort: function (input, min) { var n = min - input.length;  return accipiter.getLang_(jsLang,"formatInputTooShort") + n +accipiter.getLang_(jsLang,"unitchar");},
        formatInputTooLong: function (input, max) { var n = input.length - max;  return accipiter.getLang_(jsLang,"formatInputTooLong") + n +accipiter.getLang_(jsLang,"unitchar");},
        formatSelectionTooBig: function (limit) { return accipiter.getLang_(jsLang,"formatSelectionTooBig")+ limit + accipiter.getLang_(jsLang,"term"); },
        formatLoadMore: function (pageNumber) { return accipiter.getLang_(jsLang,"formatLoadMore"); },
        formatSearching: function () { return accipiter.getLang_(jsLang,"formatSearching"); }
    });
})(jQuery);
