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
	/*
	function processResponse()
	{
	  if (r.readyState == 4) {
	    if (r.status == 200) {

		var parser = new DOMParser();
		xmlDoc	=	parser.parseFromString(r.responseText				, "application/xml");
		xmlDoc2	=	parser.parseFromString(xmlDoc.getElementsByTagName('player')	, "application/xml");
	      alert(
		'XML z serwera: ' + 
		xmlDoc.firstChild.children[0].children//.text//.getElementsByTagName('tekst')[0].childNodes[0].nodeValue		
		//xmlDoc.getElementsByTagName('tekst')[0].childNodes[0].nodeValue
	      );
	    };
	  };
	}*/
	function ask_for_time() {
		
		xmlhttp=getXMLHttpRequest();
		xmlhttp.open("GET","ajax_time_request",false);
		xmlhttp.send();
		msecStartTimeFromServer=parseInt(xmlhttp.responseText);
		console.log("Received startTime from server: "+ msecStartTimeFromServer);
	}
	
	
	
	///////////////////////// timer
	
	
	var count
	var turn_time = 30;
	var msec_turn_time = 25000;
	var timer_started = false;
	
	function countdown() {
		// your code goes here
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
				//setTimeout(function() {  }, 4000)

			}
		}, 1000);
	}
	
	function UpdateTimer() {
		document.getElementById('game_clock').style.fontSize="large";
		document.getElementById('game_clock').innerHTML = "Turn ends in " + count + "s";
		
	}
	///////////////////////// timer for synchro
	
	function startTimerAfter(delayVal) {
		
		count = delayVal;
		console.log("Synchro waits for.." +count+" ms.");
		var del = setTimeout(function() { countdown(); }, count)
		
		
	}
	////////////////////////
	window.onload = function() {
		
		ask_for_time();
		var msecTimeHere;
		
		if(msecStartTimeFromServer!=0 && timer_started==false) {
				
				msecTimeHere = new Date().getTime();
				var msecDiff = msecTimeHere-msecStartTimeFromServer;
				console.log("Time here: "+msecTimeHere+" ms");
				console.log("Server Start Time: "+msecStartTimeFromServer+" ms");
				console.log("Server started "+msecDiff+" ms ago ( difference )");
				console.log("Diff in seconds "+(msecDiff/1000));
				//console.log("diff%30000ms "+(msecDiff%30000));
				//console.log("30000ms - diff%30000 "+(30000-(msecDiff%30000)));
				var tmp = msecDiff%30000;
				tmp+=1;
				console.log("tmp in seconds "+(tmp/1000));
				if(msecDiff%30000==0)
					countdown(turn_time);
				else
					
					startTimerAfter(30000 - tmp);
			
		}
		setInterval(function() { location.reload() }, 30000*6)
	}