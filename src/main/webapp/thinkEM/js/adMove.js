window.onload = function(){
	
	
	var lis = $('#ad_w li');
	$('#ad_w').css('width',lis.length*280);
	var lc1 = 0;
	var rc1 = lis.length-4;
	timer = setInterval(myMove,5000);
	
	function myMove(){
		if(rc1>=1){
			lc1++;
			rc1--;
			$('#ad_w').animate({left:'-=270px'}, 1000);
		}
		else {
			rc1 = lis.length-4;
			$('#ad_w').animate({left:0},1000);
			
		}
	}
	
	
	$('#interest #ad_right img').on('click',function(){
		if (rc1 < 1){
			alert("已经是最后一张广告");
			return;
		}
		lc1++;
		rc1--;
		$('#ad_w').animate({left:'-=270px'}, 1000);

		
	})
	
	
	$('#interest #ad_left img').on('click',function(){
		if (lc1 < 1) {
			alert("已经是第一张图片广告");
			return;
		}
		lc1--;
		rc1++;
		$('#ad_w').animate({left:'+=270px'}, 1000);

	})
	
	if(window.screen.width<500){
		var lis = $('#ad_w li');
		$('#ad_w').css('width',lis.length*280);
		var lc1 = 0;
		var rc1 = lis.length-1;
		timer = setInterval(myMove,5000);
		
		function myMove(){
			if(rc1>=1){
				lc1++;
				rc1--;
				$('#ad_w').animate({left:'-=270px'}, 1000);
			}
			else {
				rc1 = lis.length-1;
				$('#ad_w').animate({left:0},1000);
				
			}
		}
		
		
		$('#interest #ad_right img').on('click',function(){
			if (rc1 < 1){
				alert("已经是最后一张广告");
				return;
			}
			lc1++;
			rc1--;
			$('#ad_w').animate({left:'-=270px'}, 1000);

			
		})
		
		
		$('#interest #ad_left img').on('click',function(){
			if (lc1 < 1) {
				alert("已经是第一张图片广告");
				return;
			}
			lc1--;
			rc1++;
			$('#ad_w').animate({left:'+=270px'}, 1000);

		})
	}
}
	
