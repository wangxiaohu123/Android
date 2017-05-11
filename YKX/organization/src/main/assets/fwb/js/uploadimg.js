// 为jquery扩展了一个getUrlParam()方法
(function($) {
    $.getUrlParam = function(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
})(jQuery);

$(document).ready(function() {
    // 获取url参数
    // var xx = $.getUrlParam('word');
    // console.log(xx)

    // 七牛upload
    function qiniuUpload(rst, context) {
        $.get('https://api.develop.ykx100.com/v1/backend/uptoken/public')
            .done(function(response) {
                $.ajax({
                        url: 'http://upload-z2.qiniu.com/putb64/-1',
                        headers: {
                          "Content-Type" : "application/octet-stream",
                          "Authorization" : "UpToken "+response.data.token
                        },
                        type: 'POST',
                        data: rst.base64.replace('data:image/jpeg;base64,','')
                    })
                    .done(function(json) {
                        console.log('压缩：' + rst.fileLen / rst.origin.size * 100 + '%')
                        context.invoke('editor.insertImage', response.data.domain + '/' + json.key);
                        $('.ykx-cover').hide();
                    }) //成功
                    .fail(function(json) {
                        $('.ykx-cover').hide();
                        alert("上传图片失败");
                    }) //失败
            }) //延迟成功
            .fail(function() {
                $('.ykx-cover').hide();
                alert("获取token失效");
            }); //延迟失败
    }

    //  图片按钮
    var imgUploadButton = function(context) {
        var ui = $.summernote.ui;
        // create button
        var button = ui.button({
            contents: '<img src="./img/fillin-picture.svg" style="width:22.22px;height:18px;"/>',
            tooltip: 'ing-upload',
            click: function() {
                var $input = $('<input id="file" type="file" accept="image/*" style="display:none;">').appendTo('body');
                $input.trigger('click');
                $input.on('change', function() {
                    $('.ykx-cover').show();
                    lrz(this.files[0])
                    .then(function(rst) {
                        // 处理成功会执行
                        qiniuUpload(rst, context);
                    })
                    .catch(function(err) {
                        // 处理失败会执行
                        alert("请上传imag")
                    })
                    .always(function() {
                        // 不管是成功失败，都会执行
                    });
                });
            }
        });
        return button.render(); // return button as jquery object
    }

    //  拍照按钮
    var photographButton = function(context) {
        var ui = $.summernote.ui;
        // create button
        var button = ui.button({
            contents: '<img src="./img/fillin-camera.svg" style="width:22.22px;height:18px;"/>',
            tooltip: 'ing-upload',
            click: function() {
                var $input = $('<input id="file" type="file" capture="camera" accept="image/*" style="display:none;">').appendTo('body');
                $input.trigger('click');
                $input.on('change', function() {
                    $('.ykx-cover').show();
                    lrz(this.files[0]).then(function(rst) {
                            // 处理成功会执行
                            qiniuUpload(rst, context);
                        })
                        .catch(function(err) {
                            // 处理失败会执行
                            alert("请上传imag")
                        })
                        .always(function() {
                            // 不管是成功失败，都会执行
                        });
                });
            }
        });
        return button.render(); // return button as jquery object
    }

    $('#summernote').summernote({
        placeholder: '班课简介填写指导文字',
        shortcuts: false, //禁用快捷键
        focus: true, //获取焦点
        toolbar: [
            ['imgfile', ['imgfile']],
            ['photograph', ['photograph']]
        ],
        buttons: {
            imgfile: imgUploadButton,
            photograph: photographButton
        },
        callbacks: {
            onPaste: function(ne) {
                var bufferText = ((ne.originalEvent || ne).clipboardData || window.clipboardData).getData('Text/plain');
                //    ne.preventDefault();
                ne.preventDefault ? ne.preventDefault() : (ne.returnValue = false);
                // Firefox fix
                setTimeout(function() {
                    document.execCommand("insertText", false, bufferText);
                }, 10);
                /*  */
            }
        }
    });
});
