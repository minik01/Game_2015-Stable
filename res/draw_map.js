var world_size = 10000;
var sector_size = 1000;
var ratio;
var canvas;
var ctx;
var canvas_size;
var timer;
var player_pos_x;
var player_pos_y;
var scaled_pos_x;
var scaled_pos_y;
var sector_selected;
var sector_active;
var selection_size = 40;
var sector_canvas_size = 50;
function calculate_sector(x,y) {
	// get sector with x,y coordinates
	return (y/50)*10+x/50 +1;
}

function get_scaled_pos(in_x, in_y) {
	// get player world coordinates and scale to canvas coordinates
	scaled_pos_x = in_x/ratio;
	scaled_pos_y = in_y/ratio;
	// initialize player's sector
	
	console.log("scaled_pos_x: "+scaled_pos_x);
	console.log("scaled_pos_y: "+scaled_pos_y);
}
function getRealPosition(in_x,in_y) {
	this.world_x = in_x*ratio;
	this.world_y = in_y*ratio;
}
function mark_active_sector() {
	
	ctx.fillStyle = "white";

	var x = scaled_pos_x/sector_canvas_size | 0;
	var y = scaled_pos_y/sector_canvas_size | 0;
	x *=sector_canvas_size;
	y *=sector_canvas_size;
	//console.log('x:' + x);
	//console.log('y:' + y);
	if(x>450) x=450;
	if(y>450) y=450;
	ctx.fillRect(x+5,y+5,selection_size,selection_size);
	sector_active = calculate_sector(x,y); 
	
	console.log("sector_active: "+sector_active);
	
	ctx.font = 'normal 16pt fantasy';
	ctx.fillStyle = 'blue';
	margin=8;
	if(sector_active<10) margin=20;
	else if(sector_active<100) margin=15;
	ctx.fillText((sector_active), x+margin, y+32);
	console.log("scaled_pos_x_canvas: "+x);
	console.log("scaled_pos_y_canvas: "+y);
	//$("#world_map_sector_p").html("Selected sector: "+sector_active);
	
}
$( document ).ready(function() {
	// when document is ready do :
function init() {
	// initialize canvas
	canvas = document.getElementById("world_map_canvas");
	ctx = canvas.getContext('2d');
	canvas_size = canvas.width;
	
	ratio = world_size/canvas_size;
	
	// get new player world coordinates
	player_pos_x = 1000;
	player_pos_y = 2000;
	
	// scale coordinates from world to canvas and get sector
	get_scaled_pos(player_pos_x,player_pos_y);
	
	
	//timer=setInterval(draw, 10);
	//return timer;
}
function draw() {
	/*ctx.clearRect(0, 0, canvas.width, canvas.height);
	ctx.fillStyle = "#FAF7F8";
	ctx.fillRect(0,0,canvas.width,canvas.height);
	ctx.fillStyle = "#003300";
	drawBall(circle);
	if (circle.x +dx > canvas.width || circle.x +dx < 0)
	dx=-dx;
	if(circle.y+dy>bar.y && circle.x>bar.x && circle.x<bar.x+barImg.width)
	dy=-dy;
	if (circle.y +dy > canvas.height || circle.y +dy < 0)
	dy=-dy;
	circle.x += dx;
	circle.y += dy;
	ctx.drawImage(barImg,bar.x,bar.y);
	if(circle.y>bar.y){
	clearTimeout(timer);
	ctx.clearRect(0, 0, canvas.width, canvas.height);
	alert("Game Over");*/
}
  
// world map - default view

// redraw world map with selection

function redraw_world_map() {

	ctx.fillStyle = "#FF0000";
	ctx.fill();

	ctx.lineWidth = 1;
	ctx.strokeStyle = "Green";
	ctx.beginPath();
	
	for (var i = 50; i < canvas_size; i += 50) {
		// add horizontal stripes
		addLineSubPathX(ctx, i);
		// add vertical stripes
		addLineSubPathY(ctx, i);
	}
	ctx.stroke();
	
	mark_active_sector()
	
	function addLineSubPathX(ctx, x) {
		ctx.moveTo(0, x);
		ctx.lineTo(canvas_size, x);
	}
	
	
	function addLineSubPathY(ctx, y) {
		ctx.moveTo(y, 0);
		ctx.lineTo(y, canvas_size);
	}

	}
	
	// set up initial values
	init();
	
	// redraw at first run
	
	redraw_world_map();
	
	
	
	// world map canvas listener - getter for sector selection
	
	document.getElementById('world_map_canvas').onclick = function(e) {
	
				
				
				
		ctx.clearRect(0,0,this.width,this.height);
		
		redraw_world_map();
		
		ctx.fillStyle = "Red";
		
		var pos = getCursorPosition(this,e);
		var x = pos[0]/sector_canvas_size | 0;
		var y = pos[1]/sector_canvas_size | 0;
		x *=sector_canvas_size;
		y *=sector_canvas_size;
		//console.log('x:' + x);
		//console.log('y:' + y);
		if(x>450) x=450;
		if(y>450) y=450;
		ctx.fillRect(x+5,y+5,selection_size,selection_size);
		sector_selected = calculate_sector(x,y); 
		ctx.font = 'normal 16pt fantasy';
		ctx.fillStyle = 'blue';
		margin=8;
		if(sector_selected<10) margin=20;
		else if(sector_selected<100) margin=15;
		ctx.fillText((sector_selected), x+margin, y+32);
		$("#world_map_sector_p").html("Selected sector: "+sector_selected);
	}
	//	get click coordinates for canvas
	
	function getCursorPosition(canvas, event) {
		var x, y;

		canoffset = $(canvas).offset();
		x = event.clientX + document.body.scrollLeft + document.documentElement.scrollLeft - Math.floor(canoffset.left);
		y = event.clientY + document.body.scrollTop + document.documentElement.scrollTop - Math.floor(canoffset.top) + 1;

		return [x,y];
	}
});   
			