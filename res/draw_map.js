


$( document ).ready(function() {

// world map - default view

function redraw() {

	var size = 500;
	var c = document.getElementById("world_map_canvas");
	var ctx = c.getContext('2d');
	ctx.strokeStyle = "#FF0000";
	ctx.fillRect(0,0,size,size);

	ctx.lineWidth = 1;
	ctx.strokeStyle = 'green';
	ctx.beginPath();
	
	for (var i = 50; i < size; i += 50) {
		addLineSubPathX(ctx, i);
		addLineSubPathY(ctx, i);
	}
	ctx.stroke();

	
	function addLineSubPathX(ctx, y) {
		ctx.moveTo(0, y);
		ctx.lineTo(size, y);
	}
	function addLineSubPathY(ctx, y) {
		ctx.moveTo(y, 0);
		ctx.lineTo(y, size);
	}

	}
	redraw();
	
	document.getElementById('world_map_canvas').onclick = function(e) {
				redraw();
				var c = document.getElementById("world_map_canvas");
				var ctx = c.getContext('2d');
				ctx.strokeStyle = 'red';
				var pos = getCursorPosition(c,e);
				var x = pos[0]/50 | 0;
				var y = pos[1]/50 | 0;
				x *=50;
				y *=50;
				console.log('x:' + x);
				console.log('y:' + y);
				ctx.fillRect(x+5,y+5,45,45);
				$("#world_map_sector_p").html("Selected sector:"+x/50+","+y/50);
			}
	function getCursorPosition(canvas, event) {
		var x, y;

		canoffset = $(canvas).offset();
		x = event.clientX + document.body.scrollLeft + document.documentElement.scrollLeft - Math.floor(canoffset.left);
		y = event.clientY + document.body.scrollTop + document.documentElement.scrollTop - Math.floor(canoffset.top) + 1;

		return [x,y];
	}
});   
			