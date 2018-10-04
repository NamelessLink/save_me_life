'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});
function sendAjax() {
    var url = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : 'index';
    var options = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : '';

    var xmlhttp = void 0;
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var result = JSON.parse(xmlhttp.responseText);
            return result;
        }
    };
    alert(options);
    xmlhttp.open('GET', url + options);
    xmlhttp.send();
}
exports.sendAjax = sendAjax;