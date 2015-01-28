function shipObjectClass(id,owner_id,lvl,pos_x,pos_y,busy) {

	this.id = id;
	this.owner_id = owner_id;
	this.lvl = lvl;
	this.pos_x = pos_x;
	this.pos_y = pos_y;
	this.busy = busy;
}


function loadXMLDoc(url)
{
	var xmlhttp;
	var shipNode,param;
	var txt,i;
	var shipObject =[];
	
	// var id,owner_id,lvl,pos_x,pos_y,busy;
	// var arrayOfShips=[];
	
	if (window.XMLHttpRequest)
		{// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp=new XMLHttpRequest();
		}
	else
		{// code for IE6, IE5
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
	xmlhttp.overrideMimeType('text/xml');
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
	
	xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
			{
				//txt="<table border='1'><tr><th>Title</th><th>Artist</th></tr>";
				
				shipNode=xmlhttp.responseXML.documentElement.getElementsByTagName("ship");
				
				// for every ship do:
				
				for (i=0;i<shipNode.length;i++)
				{
					shipObject[i] = new shipObjectClass(0,0,0,0,0,0);
					
					param=shipNode[i].getElementsByTagName("id");
					{
						try
						{
							shipObject[i].id = param[0].firstChild.nodeValue;
							// id = param[0].firstChild.nodeValue;
							//console.log("Assigned new value to ship no "+(i+1)+". It's id: "+shipObject[i].id);
						}
						catch (er)
						{
							console.log("Problem with XML management ( reading XML)");
							
						}
					}
					param=shipNode[i].getElementsByTagName("owner_id");
					{
						try
						{
							shipObject[i].owner_id = param[0].firstChild.nodeValue;
							// owner_id = param[0].firstChild.nodeValue;
						}
						catch (er)
						{
							console.log("Problem with XML management ( reading XML)");
							
						}
					}
					param=shipNode[i].getElementsByTagName("lvl");
					{
						try
						{
							shipObject[i].lvl = param[0].firstChild.nodeValue;
							// lvl = param[0].firstChild.nodeValue;
						}
						catch (er)
						{
							console.log("Problem with XML management ( reading XML)");
							
						}
					}
					param=shipNode[i].getElementsByTagName("current_x");
					{
						try
						{
							shipObject[i].pos_x = param[0].firstChild.nodeValue;
							// pos_x = param[0].firstChild.nodeValue;
						}
						catch (er)
						{
							console.log("Problem with XML management ( reading XML)");
							
						}
					}
					param=shipNode[i].getElementsByTagName("current_y");
					{
						try
						{
							shipObject[i].pos_y = param[0].firstChild.nodeValue;
							// pos_y = param[0].firstChild.nodeValue;
						}
						catch (er)
						{
							console.log("Problem with XML management ( reading XML)");
							
						}
					}
					param=shipNode[i].getElementsByTagName("busy");
					{
						try
						{
							shipObject[i].busy = param[0].firstChild.nodeValue;
							// busy = param[0].firstChild.nodeValue;
						}
						catch (er)
						{
							console.log("Problem with XML management ( reading XML)");
							
						}
					}
					
					
					// console.log("id= :"+id);
					// console.log("owner_id= :"+owner_id);
					// console.log("lvl= :"+lvl);
					// console.log("pos_x= :"+pos_x);
					// console.log("pos_y= :"+pos_y);
					// console.log("busy= :"+busy);
					// arrayOfShips.push(new shipObjectClass(id,owner_id,lvl,pos_x,pos_y,busy));
					
				}
				// console.log("xml_management -> shipObject[1].id= :"+arrayOfShips[1].id);
				// return arrayOfShips;
				//console.log("xml_management -> shipObject[1].id= :"+shipObject[1].id);
				drawOtherShips(shipObject);
				return shipObject;
			}
		}
		
		
}