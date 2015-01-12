function getRadarCanvas() {
	var canvas = document.getElementById('radar_canvas');
	return canvas;
}
function getRadarContext() {
	var canvas = getRadarCanvas();
	var context = canvas.getContext('2d');
	return context;
}
function redraw_background() {
	
	var canvas = getRadarCanvas();
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
	
	
	// draw main player at the center
	
	context.beginPath();
	context.arc(centerX, centerY, radius*0.03, 0, 2 * Math.PI, false);
	context.fillStyle = 'white';
	context.fill();
	
	

}
function findMyX(ships) {
	for (i=0;i<ships.length;i++) {
		if(ships[i].owner_id=="69") return ships[i].pos_x;
	}
}
function findMyY(ships) {
	for (i=0;i<ships.length;i++) {
		if(ships[i].owner_id=="69") return ships[i].pos_y;
	}
}
function drawOtherShips(new_ships) {
	var ships =[];
	ships = new_ships;
	console.log("drawOtherShips");
	console.log("draw_radar -> ships= :"+ships);
	console.log("draw_radar -> shipObject[1].id= :"+ships[1].id);
	
	var shipOthers = ships;
	var contex = getRadarContext();
	
	var myX = findMyX(shipOthers); // get main players position x ( use it as reference point )
	var myY = findMyY(shipOthers); // get main players position y ( use it as reference point )
	
	if(shipOthers!=0)
	console.log("ships OK, length: "+shipOthers.length);
	else
	console.log("ships are not ok, length: "+shipOthers.length);
	for (i=0;i<shipOthers.length;i++) {
		console.log("Ship["+i+"]");
		// draw each player
		var x = shipOthers[i].pos_x;
		var y = shipOthers[i].pos_y; // world coords
		console.log("x_world "+x);
		console.log("y_world "+y);
		var drawCoords = parseWorldX(x,y);
		console.log("x_canv "+drawCoords.x);
		console.log("y_canv "+drawCoords.y);
		context.beginPath();
		context.arc(drawCoords.x, drawCoords.y, radius*0.03, 0, 2 * Math.PI, false);
		context.fillStyle = 'red';
		context.fill();
	
	}

}
function init() {
	
	redraw_background();
}  

function draw_radar() {
	
	
	// when document is ready do :
	
	init();
	
}


$( document ).ready( function() {

	draw_radar();


});
   