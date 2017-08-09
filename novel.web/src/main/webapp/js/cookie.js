function saveCookie(key, value, expires) {
    var date = new Date();
    if (expires != undefined) {
        date.setTime(date.getTime() + expires);
    } else {
        date.setTime(date.getTime() + 365 * 24 * 60 * 60 * 1000);
    }	//设置一年或指定的有效期，如果值位-1，则删除这个cookie
    document.cookie = key + "=" + value + "; expires=" + date.toGMTString();
}

function getCookie(key) {
    var cookie = document.cookie;
    var cookies = cookie.split("; ");
    for (var i = 0; i < cookies.length; i++) {
        var keyValue = cookies[i].split("=");
        var currKey = keyValue[0];
        var currValue = keyValue[1];
        if (key == currKey) {
            return currValue;
        }
    }
    return "";
}

function removeCookie(key) {
    saveCookie(key, "", -1);
}