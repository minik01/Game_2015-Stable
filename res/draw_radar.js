function getRadarCanvas() {
	var canvas = document.getElementById('radar_canvas');
	return canvas;
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
	//console.log("draw_radar -> ships= :"+ships);
	//console.log("draw_radar -> shipObject[1].id= :"+ships[1].id);
	
	var shipOthers = ships;
	var canvas = getRadarCanvas();
	var context = canvas.getContext('2d');
	var centerOfCanvasX = canvas.width / 2;
	var centerOfCanvasY = canvas.height / 2;
	var radius = canvas.width*0.4;
	
	var myX = findMyX(shipOthers); // get main players position x ( use it as reference point )
	var myY = findMyY(shipOthers); // get main players position y ( use it as reference point )
	
	// if(shipOthers!=0)
	// console.log("ships OK, length: "+shipOthers.length);
	// else
	// console.log("ships are not ok, length: "+shipOthers.length);
	
	
	// temporary my id
	
	var my_id = 69;
	for (i=0;i<shipOthers.length;i++) {
		if(shipOthers[i].owner_id != my_id) {
			
			// draw each player
			var x = shipOthers[i].pos_x;
			var y = shipOthers[i].pos_y; // world coords
			//console.log("Not my ship");
			//console.log("x"+i+"_world "+x);
			//console.log("y"+i+"_world "+y);
			shipX = x*centerOfCanvasX/myX;
			shipY = y*centerOfCanvasY/myY;
			//console.log("x"+i+"_canv "+shipX);
			//console.log("y"+i+"_canv "+shipY);
			context.beginPath();
			context.arc(shipX, shipY, radius*0.03, 0, 2 * Math.PI, false);
			context.fillStyle = 'red';
			context.fill();
			//console.log("Ship["+i+"] shown on radar");
		}
	}

}
function init() {
	
	redraw_background();
}  

function draw_radar() {
	
	
	// when document is ready do :
	
	init();
	
}
function change_ship_pos(my_id,pos_x,pos_y){
	
	var xmlhttp;
	
	if (window.XMLHttpRequest)
		{// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp=new XMLHttpRequest();
		}
	else
		{// code for IE6, IE5
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
	xmlhttp.open("GET","ajax_new_coords&ship_id="+my_id+"&pos_x="+pos_x+"&pos_y="+pos_y,true);
	xmlhttp.send();
	
	xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
			{
				
				console.log("new_target_accepted")
			}
		}
}

$( document ).ready( function() {

	draw_radar();
	
	
	// make click-able
	var canvas = getRadarCanvas();
	var context = canvas.getContext('2d');
	var centerOfCanvasX = canvas.width / 2;
	var centerOfCanvasY = canvas.height / 2;
	var radius = canvas.width*0.4;
	
	var new_x,new_y;
	
	var my_id = 69;
	
	function relMouseCoords(event){
		var totalOffsetX = 0;
		var totalOffsetY = 0;
		var canvasX = 0;
		var canvasY = 0;
		var currentElement = this;

		do{
			totalOffsetX += currentElement.offsetLeft - currentElement.scrollLeft;
			totalOffsetY += currentElement.offsetTop - currentElement.scrollTop;
		}
		while(currentElement = currentElement.offsetParent)

		canvasX = event.pageX - totalOffsetX;
		canvasY = event.pageY - totalOffsetY;

		return {x:canvasX, y:canvasY}
	}
	HTMLCanvasElement.prototype.relMouseCoords = relMouseCoords;

	var change_pos = false;
	$('#map_radar_selected_position_confirm').click(function (e) {
	
		if(change_pos) {
			
			
			change_ship_pos(my_id,new_pos.world_x,new_pos.world_y);
			change_pos = false;
		
		}
	
	});
	$('#radar_canvas').click(function (e) {
		console.log("canvas clicked");
		coords = canvas.relMouseCoords(event);
		canvasX = coords.x;
		canvasY = coords.y;
		// console.log("coorsX"+canvasX);
		// console.log("coorsY"+canvasY);
		// var clickedX = e.pageX - this.offsetLeft;
		// var clickedY = e.pageY - this.offsetTop;
		
		var clickedX = canvasX;
		var clickedY = canvasY;
		
		var radarX0 = this.offsetLeft;
		var radarY0 = this.offsetTop;
		// console.log("radar x0 = "+radarX0);
		// console.log("radar y0 = "+radarY0);
		// console.log("clicked x = "+clickedX);
		// console.log("clicked y = "+clickedY);
		// console.log("radar centerX = "+centerOfCanvasX);
		// console.log("radar centerY = "+centerOfCanvasY);
		// console.log("radar radius = "+radius);
		// console.log("min x = "+parseInt(centerOfCanvasX-radius));
		// console.log("min y = "+parseInt(centerOfCanvasY-radius));
		var distanceFromCenterOfCanvas = Math.sqrt((Math.pow(parseInt(clickedX-centerOfCanvasX),2))+(Math.pow(parseInt(clickedY-centerOfCanvasY),2)));
		//console.log("distance from the middle = "+distanceFromCenterOfCanvas);
		//check if visible area is clicked
		if(distanceFromCenterOfCanvas < radius) {
			console.log("fulfilled condition");
			new_pos = new getRealPosition(clickedX,clickedY);
			change_pos= true;
			$("#map_radar_selected_position_p").html("Selected position: x="+new_pos.world_x+" y="+new_pos.world_y);
			
		}
	});

	
});
  