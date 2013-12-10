function main_console() {
  var TAG = 'Main Console: ';
  var ws;
  var hostURL = '';
  var body;

  this.init = function(url) {
    hostURL = url;
    body = document.getElementsByTagName('body')[0];
    var newDiv = document.createElement('div');
    newDiv.setAttribute('id','execute');
    body.appendChild(newDiv);
  }

  this.sendCommand = function(command){
    if (ws.readyState == ws.OPEN) {
      ws.send(command);
    } else {
      console.log(TAG + " isn't ready or open to send data " + hostURL);
    }
  }

  this.connect = function() {
    console.log(TAG + 'Connecting ... ' + hostURL);
    var hostProtocol = 'ws://';

    /*if (window.location.protocol == 'http:')
      hostProtocol = 'ws://';
    else
      hostProtocol = 'wss://';*/

    var hostURL = hostProtocol + hostURL;
    /* Open a websocket which will depend on browser */
    if ('MozWebSocket' in window) {
      ws = new MozWebSocket(hostURL);
    } else if ('WebSocket' in window) {
      ws = new WebSocket(hostURL);
    } else {
      alert('Your browser does not support WebSockets');
    }

    ws.onopen = function() {

    }
    ws.onmessage = function() {

    }
    ws.onclose = function() {

    }
    ws.onerror = function() {

    }
  }
}