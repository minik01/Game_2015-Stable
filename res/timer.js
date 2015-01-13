var msecStartTimeFromServer=0;
	var r;
	var xmlDoc;	
	function getXMLHttpRequest()
	{
	  var request = false;
	    
	  try {
	    request = new XMLHttpRequest();
	  } catch(err1) {
	    try {
	      request = new ActiveXObject('Msxml2.XMLHTTP');
	    } catch(err2) {
	      try {
		request = new ActiveXObject('Microsoft.XMLHTTP');                
	      } catch(err3) {
		request = false;
	      }
	    }
	  }
	  return request;
	}
	
	function ask_for_time() {
		
		xmlhttp=getXMLHttpRequest();
		xmlhttp.open("GET","ajax_time_request",false);
		xmlhttp.send();
		msecStartTimeFromServer=parseInt(xmlhttp.responseText);
		console.log("Received startTime from server: "+ msecStartTimeFromServer);
	}
	
	
	/////////////////////////XML
	var shipObject = [];
	
	function drawShips() {
		console.log("timer.js -> Load XML");
		shipObject = loadXMLDoc("ships_data.xml"); // get and read xml with ships
		//console.log("shipObject.id = "+shipObject[1].id);
		//drawOtherShips(shipObject); // mark ships on the radar
	}
	///////////////////////// timer
	
	
	var count,count2
	var turn_time = 30;
	var server_turn_time = 40;
	var msec_turn_time = turn_time *1000;
	var timer_started = false;
	
	
	function timeToStart(time2) {
		// Timer between Turns
		console.log("timeToStart: "+time2);
		if(time2 % 1 != 0) count2=Math.round( time2 );
		else count2 = time2;
		ToStartUpdate(count2);
		console.log("count2.0: "+count2);
		var timerId2 = setInterval(function() {
			count2--;
			console.log(count2);
			ToStartUpdate(count2);
			if(count2<0) {
				location.reload();
			}
			if(count2 == 5) {
				// 5 sec to new turn
				drawShips();
			}
			if(count2 == 0) {
				// real end of turn
				clearInterval(timerId2);
				countdown();

			}
		}, 1000);
	}
	
	
	function countdown() {
		// Countdown while turn is active for player
		timer_started= true;
		count = turn_time;
		UpdateTimer();
		var timerId = setInterval(function() {
			count--;
			console.log(count);
			UpdateTimer();
			if(count == 0) {
				// your code goes here
				count = turn_time+1;
				count = turn_time;
				clearInterval(timerId);
				timeToStart(10);

			}
		}, 1000);
	}
	
	function UpdateTimer() {
		// Update text for countdown while turn is active
		document.getElementById('game_clock').style.fontSize="large";
		document.getElementById('game_clock').innerHTML = "Turn ends in " + count + "s";
		
	}
	function ToStartUpdate(a) {
		// Update text how much time left to new turn
		document.getElementById('game_clock').style.fontSize="large";
		document.getElementById('game_clock').innerHTML = "Turn starts in " + a + "s";
		
	}
	
	////////////////////////
	window.onload = function() {
		// when web-page loads 
		try {
			//get time from server
			ask_for_time();
		} catch(err){
			//reload for new try
			location.reload();
		}
		var msecTimeHere;
		
		if(msecStartTimeFromServer!=0 && timer_started==false) {
				// when server_start_time had been received and timer is off
				msecTimeHere = new Date().getTime();
				var msecDiff = msecTimeHere-msecStartTimeFromServer;
				//console.log("Time here: "+msecTimeHere+" ms");
				console.log("Server Start Time: "+msecStartTimeFromServer+" ms");
				//console.log("Server started "+msecDiff+" ms ago ( difference )");
				console.log("Server started "+(msecDiff/1000)+" s ago");
				
				var tmp = msecDiff%msec_turn_time;
				tmp+=1;
				console.log("tmp in seconds "+(tmp/1000));
				if(msecDiff%msec_turn_time==0) {
					// if its the time for new turn start it now
					drawShips();
					countdown();
				}
					
				else {
					drawShips();
					// do countdown between turns
					if((server_turn_time - tmp/1000)<30)
						timeToStart(server_turn_time - tmp/1000);
					else
						timeToStart((server_turn_time - tmp/1000)-30);
				}
		}
		setInterval(function() { location.reload() }, msec_turn_time*6)
	}