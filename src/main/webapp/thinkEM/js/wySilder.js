;(function ($){
	function WySilder(eles,options){
		this._init(eles,options);
	}
	WySilder.prototype._init = function (eles,options){
        //IE8不将伪数组转成真数组直接这样push会出错，即不写Array.prototype.slice.apply(eles)会出错
		([]).push.apply(this, Array.prototype.slice.apply(eles));
		this._options = {
			/*轮播图的数据，使用该轮播图时可以不用写dom结构，直接传递数据即可自动生成、
			如果原先就有dom结构又传递了数据那么会把原来的替换掉*/
			/*data: [
				{
					img: "images/1-1.jpg",
					link: "http: //www.baidu.com",
					alt: "这是一张图片"
				},
				{
					img: "images/2-1.jpg",
					alt: "这还是刚才那张图片"
				},
				{
					img: "images/3-1.png",
					link: "http: //www.baidu.com",
					alt: ""
				}
			],*/
			auto: true,//自动播放
            speed: 20,//轮播图运动速度
			sideCtrl: true,//是否需要侧边控制按钮
			"defaultView": 0,//默认显示的所引，IE8不能使用default，否则会报错
            interval: 3000,//自动轮播的时间，以毫秒为单位，默认3000毫秒
            activeClass: "active",//小的控制按钮激活的样式，不包括作用两边，默认active
            bottomCtrl: true,//是否显示下边的控制按钮
		};
		var that = this;
		$.extend(true, this._options, options);
		$(this).each(function(index, ele) {
			var $this = $(this),
				silderCtrl = $this.find(".js-silder-ctrl"),
				silderMain = $this.find(".silder-main");
			$this.addClass('js-silder');
			//判断是否有轮播图主结构，如果没有则生成
			if(silderMain.length == 0){
				var silderScroll = $('<div class="silder-scroll"><div class="silder-main"></div></div>');
				silderMain = silderScroll.children('.silder-main');
				$this.append(silderMain);

                ele.silderMain = silderMain;
			}else{
                ele.silderMain = silderMain;
				/*计算.silder-main的高度*/
				if($(that).length > 1){
					/*如果有多个轮播图，每个轮播图初始化都需要一定的时间，并且轮播图高度是由第一张图片的高度所定，如果不延时则不能计算出图片的高度
					，则整个轮播就看不到效果。经测试延时550毫秒后即使页面中有多达10多个轮播也不会出问题*/
					;(function (that,$this){
						setTimeout(function (){
							that._calcSilderMainWH($this);
						},550);
					})(that,$this);
				}else{
					that._calcSilderMainWH($this);
				}
			}
			//判断是否有控制按钮，如果没有则生成一个包裹控制按钮的div
			if(silderCtrl.length == 0){
				silderCtrl = $('<div class="js-silder-ctrl"></div>');
				$this.append(silderCtrl);
			}
			ele.silderCtrl = silderCtrl;
			ele.index = that._options.defaultView || 0;

			//如果轮播图的数据是动态的，则在此创建轮播图图片
			if(that._options.data && $.isArray(that._options.data)){
                ($this).addClass('js-silder');
				that._createSilderImg();
			}
			ele.imgsLen = ele.silderMain.children(".silder-main-img").length;

			//将除默认显示之外的全部图片都移到右边
			var silderMainImgs = $this.find(".silder-main-img");
			silderMainImgs.each(function(index2, ele2) {
				if(index2 != that._options.defaultView){
					$(ele2).css("left", silderMainImgs.eq(0).width());
				}
			});
            
		});
		//生成小的控制按钮及左右控制按钮
		this._createControllBtns();
		if(this._options.auto){
            this._autoPlay();
        }
        //给默认显示的轮播图对应的控制按钮添加激活样式
        $(this).each(function(index, ele) {
            that._setControllBtnClass(ele.silderCtrl.find('.silder-ctrl-con').eq(that._options.defaultView));
        });
        
        //给window绑定resize事件，实现自适应
        $(window).on("resize", function (){
            that._calcSilderMainWH();
        });
	}
	/*计算.silder-main的高度，默认高度以第一张图片的显示高度为标准*/
	WySilder.prototype._calcSilderMainWH = function ($ele,width,height){
		if($ele && $ele.length && $ele.length > 0){
			var ele = $ele[0],
				silderMain = ele.silderMain,
				//_width = width || silderMain.children('.silder-main-img').eq(0).find('img').width();
				_height = height || silderMain.children('.silder-main-img').eq(0).find('img').height();
			//consoel.log(_width),
			console.log(silderMain.children('.silder-main-img').eq(0).find('img').height()),
			//silderMain.width(_width);
			silderMain.height(_height);
		}else{
			$(this).each(function(index, ele) {
				var silderMain = ele.silderMain,
					//_width = width || silderMain.children('.silder-main-img').eq(0).find('img').width();
					_height = height || silderMain.children('.silder-main-img').eq(0).find('img').height();
				//silderMain.width(_width);
				silderMain.height(_height);
			});
		}
		
	}
	/*生成轮播图图片*/
	WySilder.prototype._createSilderImg = function (){
		var that = this,	
			data = this._options.data;
		$(that).each(function(index, el) {
			var self = $(this),
				silderMain = this.silderMain,
				silderImgStr = "";
			$.each(data,function(index2, dataitem) {
				var alt = dataitem.alt ? dataitem.alt : "";
				silderMainImg = $('<div class="silder-main-img"></div>'),
					eleA = null,
					img = $('<img src="' + dataitem.img + '" alt="' + alt + '">');
				//如果是第一张图片，则设置包裹所有图片的父元素的宽高为第一张图片的宽高
				if(index2 == 0){
					img.on('load',  function(event) {
						that._calcSilderMainWH($(this).width(),$(this).height());
					});
				}
				//判断是否有链接
				if(dataitem.link){
					eleA = $('<a href="' + dataitem.link + '"></a>');
					silderMainImg.append(eleA.append(img));
				}else{
					silderMainImg.append(img);
				}
				silderMain.append(silderMainImg);
			});
		});
	}
	/*生成小的控制按钮*/
	WySilder.prototype._createControllBtns = function (){
		var that = this;
		$(that).each(function(index, el) {
			var self = $(this),
				silderMain = this.silderMain,
				silderCtrl = this.silderCtrl,
				imgLength = silderMain.children('.silder-main-img').length,
				i = 0,
				ctrlBtnStr = "";
			if(that._options.sideCtrl){
				ctrlBtnStr += '<span class="silder-ctrl-prev"><span>&lt;</span></span>';
			}
			if(that._options.bottomCtrl){
				for(; i < imgLength; i ++){
					var temp = '<span class="silder-ctrl-con"><span>' + (i + 1) + '</span></span>';
					ctrlBtnStr += temp;
				}
			}
			if(that._options.sideCtrl){
				ctrlBtnStr += '<span class="silder-ctrl-next"><span>&gt;</span></span>';
			}
			silderCtrl.append($(ctrlBtnStr));
		});
		this._ctrlBtnBindEvents();
	}
	/*小的控制按钮事件绑定*/
	WySilder.prototype._ctrlBtnBindEvents = function (){
		var that = this;
		$(that).each(function(index, ele) {
			this.silderCtrl.children().on("click", function (){
                var boxWidth = $(ele).width(),
                    silderImgs = ele.silderMain.children('.silder-main-img');
				if($(this).hasClass('silder-ctrl-next')){
                    //下一张
                    that._play($(ele));
                }else if($(this).hasClass('silder-ctrl-prev')){
                     //当前这张图片往右边边出去
                   WySilder.animate(silderImgs[ele.index], {"left": boxWidth}, null, that._options.speed);

                   ele.index -= 1;
                   if(ele.index < 0){
                      ele.index = ele.imgsLen - 1;
                   }
                   /*上一张图片进来之前先将它移到最左边，这样就能保证无限循环，如果不这样设置，那么第一次到头后就不会有从左边出来的效果了。
                   */
                   silderImgs[ele.index].style.left = -boxWidth + "px";
                   //上一张图片进来
                   WySilder.animate(silderImgs[ele.index], {"left": 0}, null, that._options.speed);
                   //给小的控制按钮添加激活样式
                   that._setControllBtnClass($(this).parent().find('.silder-ctrl-con').eq(ele.index));
                }else{
                    var thatNum = $(this).children('span').html() * 1 - 1;
                    /*如果点击的这个数字按钮的数值大于当前图片的索引，那么图片应该从右边出来*/
                   if(thatNum > ele.index){
                       WySilder.animate(silderImgs[ele.index], {"left": -boxWidth}, null, that._options.speed);
                       silderImgs[thatNum].style.left = boxWidth + "px";
                   }else if(thatNum < ele.index){
                       WySilder.animate(silderImgs[ele.index], {"left": boxWidth}, null, that._options.speed);
                       silderImgs[thatNum].style.left = -boxWidth + "px";
                   }
                   WySilder.animate(silderImgs[thatNum], {"left": 0});
                   //给小的控制按钮添加激活样式
                    that._setControllBtnClass($(this));
                   ele.index = thatNum;
                }
			});
		});
	}
	/*轮播运动*/
	WySilder.prototype._play = function ($playEle){
		var that = this,
			playEle = $playEle[0],
			imgsLen = playEle.imgsLen,
            //这个boxWidth应该是图片的宽度，考虑到要支持响应式所以这里的宽度就设为轮播图最外面的盒子的宽度
            boxWidth = $playEle.width(),
            silderImgs = playEle.silderMain.children(".silder-main-img");
        //当前这张图片往左边出去
        WySilder.animate(silderImgs.eq(playEle.index)[0], {"left": -boxWidth}, null, this._options.speed);
		playEle.index += 1;
        if(playEle.index > playEle.imgsLen - 1){
            playEle.index = 0;
        }
         /*下一张图片进来之前先将它移到最右边，这样就能保证无限循环，如果不这样设置，那么第一次到尾后就不会有从右边出来的效果了。
       */
       silderImgs.eq(playEle.index)[0].style.left = boxWidth + "px";
       //下一张图片进来
       WySilder.animate(silderImgs.eq(playEle.index)[0], {"left": 0}, null, this._options.speed);
       //给小的控制按钮添加激活样式
       this._setControllBtnClass(playEle.silderCtrl.find('.silder-ctrl-con').eq(playEle.index));
	}
    /*自动播放*/
    WySilder.prototype._autoPlay = function (){
        var that = this,
            $that = $(that),
            //轮播时间
            interval = isNaN(that.interval * 1) ? 3000 : that.interval;
        if(that._options.auto){
            $that.each(function(index, el) {
                clearInterval(this.timer);
                var self = $(this);
                this.timer = setInterval(function (){
                    that._play(self);
                }, interval);

                //给轮播盒子绑定mouseenter和mouseleave事件
                self.hover(function() {
                    clearInterval(this.timer);
                }, function() {
                    clearInterval(this.timer);
                    this.timer = setInterval(function (){
                        that._play(self);
                    }, interval);
                });
            });
        }
    }
    /*给小的控制按钮设置样式*/
    WySilder.prototype._setControllBtnClass = function (currentBtn){
        var classname = typeof this._options.activeClass != "string" ? "active" : this._options.activeClass;
        currentBtn.addClass(classname).siblings(".silder-ctrl-con").removeClass(classname);
    }
    /*缓动动画，不想有太多的依赖，所以自己写了个简单的缓动动画*/
    WySilder.animate = function (ele,attrsJson,fn,speed){
        var that = this;
       clearInterval(ele.timer);
       ele.timer = setInterval(function (){
           var flag = true;
           for(var attr in attrsJson){
               var curStyle = parseInt( $(ele).css(attr) ),
                   step = (parseInt(attrsJson[attr]) - curStyle) / 10;

               step = step > 0 ? Math.ceil(step) : Math.floor(step);

               if(curStyle != attrsJson[attr]){
                   flag = false;
               }

               if(attr == "zIndex"){
                   ele.style.zIndex = attrsJson[attr];
               }else{
                   ele.style[attr] = curStyle + step + "px";
               }
           }
           if(flag){
               clearInterval(ele.timer);
               if(fn){
                   fn.call(window);
               }
           }
       }, speed || 20);
   }
   $.silder = function (eles,options){
        eles = $(eles);
        new WySilder(eles, options);
        return eles;
   }
	$.fn.silder = function (options){
		console.log(new WySilder(this, options));
		return this;
	}
})(jQuery);