function client_console() {
  var ws;
  var hostURL = '';
  var execute;
  var newWindow;

  this.init = function(url) {
    hostURL = url;
    var body = document.getElementsByTagName('body')[0];
    execute = document.createElement('div');
    execute.setAttribute('id', 'execute');
    body.appendChild(execute);
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
      console.log('Connection established between ' + hostURL);
    }
    ws.onmessage = function(event) {
      var data = JSON.parse(event.data);

      switch(data.command){
        case 'open_window':
          openNewWindow(data.url);
        break;

        case 'close_window':
          closeWindow();
        break;

        case 'exit':
          exit();
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

  /**
   * http://www.w3schools.com/jsref/met_win_open.asp
   * @param  {[type]} url [description]
   * @return {[type]}     [description]
   */
  this.openNewWindow = function(url) {
    newWindow = window.open(url);
  }

  this.closeWindow = function() {
    newWindow.close();
  }

  this.exit = function() {
    ws.close();
    window.close();
  }
}