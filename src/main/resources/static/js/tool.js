/**
 * 增减input中的数字大小
 * @type {HTMLElement}
 */
var num_jia = document.getElementById("num-jia");
var num_jian = document.getElementById("num-jian");
var input_num = document.getElementById("docId");
num_jia.onclick = function () {
    if (input_num.value < 165)
        input_num.value = parseInt(input_num.value) + 1;
};
num_jian.onclick = function () {

    if (input_num.value <= 0) {
        input_num.value = 0;
    } else {
        input_num.value = parseInt(input_num.value) - 1;
    }
};