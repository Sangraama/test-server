/**
 * author : Gihan Karunarathne
 * email : gckarunarathne@gmail.com
 * [client_console description]
 */
function client_console() {
  var TAG = 'Client Console: ';
  var ws;
  var hostAddress;
  var execute;
  var newWindow;
  var cmds = ['new_window', 'close_window', 'exit'];
  var textarea;

  this.init = function(url,textarea) {
    hostAddress = url;
    console.log('Client output init with hostAddress:' + hostAddress);
    textarea = textarea;

    /*var body = document.getElementsByTagName('body')[0];
    execute = document.createElement('div');
    execute.setAttribute('id', 'execute');
    body.appendChild(execute);*/
  }

  this.sendCommand = function(command) {

    if (ws.readyState == ws.OPEN) {
      console.log(command);
      ws.send(JSON.stringify(command));
    } else {
      console.log(" isn't ready or open to send data " + hostAddress);
    }

  }

  this.connect = function() {
    console.log('Connecting ... ' + hostAddress);
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
      console.log('Connection established between ' + hostAddress);
    }
    ws.onmessage = function(event) {
      var data = JSON.parse(event.data);
      $('#'+textarea).val($('#'+textarea).val() + '\n' + data.command);

      switch (data.command) {
        case cmds[0]:
          newWindow = window.open(data.url);
          break;

        case cmds[1]:
          newWindow.close();
          break;

        case cmds[2]:
          ws.close();
          window.close();
          break;

        default:
          console.log('ERROR : Not supported command ' + data.command);
          break;
      }

    }
    ws.onclose = function() {

    }
    ws.onerror = function() {

    }
  }

  this.createElement = function(tag, id) {
    var newElement = document.createElement(tag);
    newElement.setAttribute('id', id);
    return newElement;
  }

}