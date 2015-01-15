var turnStartTimeFromServer=0;
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
		turnStartTimeFromServer=parseInt(xmlhttp.responseText);
		
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
		console.log("timeToStart: "+time2+"s");
		count2 = time2;
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
				countdown(turn_time);

			}
		}, 1000);
	}
	
	
	function countdown(new_time) {
		// Countdown while turn is active for player
		timer_started= true;
		count = new_time;
		
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
				timeToStart(10+1);

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
		
		if(turnStartTimeFromServer!=0) {
				// when server_start_time had been received and timer is off
				// msecTimeHere = new Date().getTime();
				// var msecDiff = msecTimeHere-turnStartTimeFromServer;
				//console.log("Time here: "+msecTimeHere+" ms");
				// console.log("Server Start Time: "+turnStartTimeFromServer+" ms");
				//console.log("Server started "+msecDiff+" ms ago ( difference )");
				console.log("Turn started "+turnStartTimeFromServer+" s ago");
				
				// var tmp = msecDiff%msec_turn_time;
				// tmp+=1;
				// console.log("tmp in seconds "+(tmp/1000));
				if(turnStartTimeFromServer==0) {
					// if its the time for new turn start it now
					drawShips();
					countdown(turn_time);
				}
					
				else {
					drawShips();
					// do countdown between turns
					if(turnStartTimeFromServer<30)
						//timeToStart(turnStartTimeFromServer);
						countdown(turnStartTimeFromServer);
					else
						countdown(turnStartTimeFromServer-30);
				}
		}
		setInterval(function() { location.reload() }, msec_turn_time*6)
	}