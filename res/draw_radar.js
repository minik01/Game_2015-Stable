$( document ).ready(function() {
	
	
	function redraw_background() {

		var canvas = document.getElementById('radar_canvas');
        var context = canvas.getContext('2d');
		
		var centerX = canvas.width / 2;
		var centerY = canvas.height / 2;
		var radius = canvas.width*0.4;

		context.beginPath();
		context.arc(centerX, centerY, radius, 0, 2 * Math.PI, false);
		context.fillStyle = 'green';
		context.fill();
		context.lineWidth = 5;
		context.strokeStyle = '#003300';
		context.stroke();
		
		
		// draw player
		
		context.beginPath();
		context.arc(centerX, centerY, radius*0.03, 0, 2 * Math.PI, false);
		context.fillStyle = 'white';
		context.fill();
		
		

	}
	
	
	// Ajax part
	
	
	function assign_position()
	{
	var a;
	 
		if (window.XMLHttpRequest)
		{// If the browser if IE7+[or]Firefox[or]Chrome[or]Opera[or]Safari
			a=new XMLHttpRequest();
		}
		else
		{//If browser is IE6, IE5
			a=new ActiveXObject("Microsoft.XMLHTTP");
		}
	 
		a.onreadystatechange=function()
		{
		  if (a.readyState==4 && a.status==200)
			{
				document.getElementById("myDiv").innerHTML=a.responseText;
			}
		}
		 
		a.open("POST","java4s.txt",true);
		a.send();
	} // fun1() close
	
	function init() {
		
		redraw_background();
		
	}  


	// when document is ready do :
	
	init();
	
});
   