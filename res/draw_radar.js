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
	function init() {
		
		redraw_background();
		
	}  


	// when document is ready do :
	
	init();
	
});
   