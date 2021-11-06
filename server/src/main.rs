use message_io::node::{self};
use message_io::network::{NetEvent, Transport};

fn main() {
    // Create a node, the main message-io entity. It is divided in 2 parts:
    // The 'handler', used to make actions (connect, send messages, signals, stop the node...)
    // The 'lobbyListener', used to read events from the network or signals.
    let (lobbyHandler, lobbyListener) = node::split::<()>();

    // Listen for TCP, UDP and WebSocket messages at the same time.
    lobbyHandler.network().listen(Transport::Tcp, "0.0.0.0:21450").unwrap();
    //lobbyHandler.network().listen(Transport::Udp, "0.0.0.0:21507").unwrap();
    //lobbyHandler.network().listen(Transport::Ws, "0.0.0.0:3044").unwrap();

    // Read incoming network events.
    lobbyListener.for_each(move |event| match event.network() {
        NetEvent::Connected(_, _) => {
            unreachable!() // Used for explicit connections.
        },
        NetEvent::Accepted(_endpoint, _lobbyListener) => println!("Client connected"), // Tcp or Ws
        NetEvent::Message(endpoint, data) => {
            // wauit
            println!("Received: {}", String::from_utf8_lossy(data));
            handler.network().send(endpoint, data);
        },
        NetEvent::Disconnected(_endpoint) => println!("Client disconnected"), //Tcp or Ws
    });
}
