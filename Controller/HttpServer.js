
var http = require('http');
var url = require('url') ;
var net=require('net');
var geo = require('geo');

var qgroundcontrolHost='127.0.0.1';
var qgroundcontrolPort=1234;

var httpserverHost='127.0.0.1';
var httpserverPort=9876;

var currentResponse;

// TCP CLIENT - Connects to QGroundControl

var client=net.connect(qgroundcontrolPort,qgroundcontrolHost,function(){
	console.log('TCP client connected: '+qgroundcontrolHost+'  Port: '+qgroundcontrolPort);
});

client.on('data',function(data){
	console.log('Data received from QGroundControl: '+data);
  	currentResponse.writeHead(200, {'Content-Type': 'text/plain','Access-Control-Allow-Origin': 'http://127.0.0.1:8080' });
  	currentResponse.end(data);

});

client.on('close',function(){
	client.destroy();
	console.log('TCP Client Connection Closed');
});


//HTTP SERVER - For access from GUI

http.createServer(function (req, res) {

	currentResponse = res;

	var queryObject = url.parse(req.url,true).query;
	var command = queryObject['command'];
	

	if(command)
	{
		if(command == 'set-location-uas')
	  	{
	  		var address = queryObject['address'];
	  		if(address == 'TownHall-Garden')
	  		{
	  			console.log('Sending command:' + command+':'+'37.2953825'+':'+'-121.9297045'+':'+3+'\n');
				client.write(command+':'+'37.2953825'+':'+'-121.9297045'+':'+3+'\n');
	  		}
	  		else
	  		{
		  		geo.geocoder(geo.google, address, false, function(formattedAddress, latitude, longitude) {
		  			console.log('Sending command:' + command+':'+latitude+':'+longitude+':'+2+'\n')
			        client.write(command+':'+latitude+':'+longitude+':'+1+'\n');
			    });
	  		}
	  	}
	  	else
	  	{
	  		console.log('Sending command:'+ command+'\n');
	  		client.write(command+'\n');
	  	}
	}

}).listen(httpserverPort, httpserverHost);

console.log('HTTP Server running. Host:'+httpserverHost+'  Port: '+httpserverPort);


