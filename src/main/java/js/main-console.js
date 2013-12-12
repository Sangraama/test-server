function main_console() {
  var TAG = 'Main Console: ';
  var ws;
  var hostAddress;
  var body;
  var cmds = ['newtab', 'close', 'exit'];

  this.init = function(url) {
    hostAddress = url;
    console.log('Admin Console init with hostAddress:' + hostAddress);
    body = document.getElementsByTagName('body')[0];
    var newDiv = document.createElement('div');
    newDiv.setAttribute('id', 'execute');
    body.appendChild(newDiv);
  }

  this.getCmds = function(){
    return cmds;
  }

  this.sendCommand = function(command) {
    var str = command.split(' ');
    var type = _.find(cmds, function(cmd) {
      return cmd == str[0];
    });
    console.log('Available command type : ' + type);
    if (type == undefined) {
      return;
    } else {
      if (ws.readyState == ws.OPEN) {
        console.log(this.commandFactory(type, command));
        ws.send(JSON.stringify(this.commandFactory(type, command)));
      } else {
        console.log(TAG + " isn't ready or open to send data " + hostAddress);
      }
    }
  }

  this.commandFactory = function(type, command) {
    switch (type) {
      case 'newtab':
        var str = command.split(' ');
        return {
          command: str[0],
          url: str[1]
        };
        break;
      case 'close':
        return {
          command: str[0]
        };
        break;
      default:

        break;
    }

  }

  this.connect = function() {
    console.log(TAG + 'Connecting ... ' + hostAddress);
    var hostProtocol = 'ws://';

    /*if (window.location.protocol == 'http:')
      hostProtocol = 'ws://';
    else
      hostProtocol = 'wss://';*/

    var hostURL = hostProtocol + hostAddress;
    /* Open a websocket which will depend on browser */
    if ('MozWebSocket' in window) {
      ws = new MozWebSocket(hostURL);
    } else if ('WebSocket' in window) {
      ws = new WebSocket(hostURL);
    } else {
      alert('Your browser does not support WebSockets');
    }

    ws.onopen = function() {
      console.log('Connection established between ' + hostURL);
    }
    ws.onmessage = function(event) {
      var data = JSON.parse(event.data);

      console.log('Received : ');
      console.log(data);
    }
    ws.onclose = function() {

    }
    ws.onerror = function() {

    }
  }
}