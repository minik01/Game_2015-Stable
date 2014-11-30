
var is_canvas_clicked_on = false;

$( document ).ready(function() {

// world map - default view

// redraw world map with selection

function redraw_world_map() {

	
	var c = document.getElementById("world_map_canvas");
	var ctx = c.getContext('2d');
	var canvas_size = c.width;
	
	ctx.fillStyle = "#FF0000";
	ctx.fill();

	ctx.lineWidth = 1;
	ctx.strokeStyle = "Green";
	ctx.beginPath();
	
	for (var i = 50; i < canvas_size; i += 50) {
		addLineSubPathX(ctx, i);
		addLineSubPathY(ctx, i);
	}
	ctx.stroke();

	// add horizontal stripes
	
	function addLineSubPathX(ctx, x) {
		ctx.moveTo(0, x);
		ctx.lineTo(canvas_size, x);
	}
	// add vertical stripes
	
	function addLineSubPathY(ctx, y) {
		ctx.moveTo(y, 0);
		ctx.lineTo(y, canvas_size);
	}

	}
	// redraw at first run
	
	redraw_world_map();
	
	// world map canvas listener - getter for sector selection
	
	document.getElementById('world_map_canvas').onclick = function(e) {
	
				var selection_size = 40;
				var sector_size = 50;
				
				var ctx = this.getContext('2d');
				ctx.clearRect(0,0,this.width,this.height);
				
				redraw_world_map();
				
				ctx.strokeStyle = "Red";
				
				var pos = getCursorPosition(this,e);
				var x = pos[0]/sector_size | 0;
				var y = pos[1]/sector_size | 0;
				x *=sector_size;
				y *=sector_size;
				console.log('x:' + x);
				console.log('y:' + y);
				if(x>450) x=450;
				if(y>450) y=450;
				ctx.fillRect(x+5,y+5,selection_size,selection_size);
				var sector = (y/50)*10+x/50 +1;
				ctx.font = 'normal 16pt fantasy';
			    ctx.fillStyle = 'blue';
				margin=8;
				if(sector<10) margin=20;
				else if(sector<100) margin=15;
			    ctx.fillText((sector), x+margin, y+32);
				$("#world_map_sector_p").html("Selected sector: "+sector);
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
			