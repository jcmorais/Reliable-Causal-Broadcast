import org.zeromq.ZMQ;

import java.util.HashMap;

/**
 * Created by carlosmorais on 23/05/16.
 */
public class Run {
    public static void  main(String[] args){
        Graph graph = new Graph();

        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket;

        HashMap<Integer, Node> socketNodes = new HashMap<Integer, Node>();
        HashMap<Integer, ZMQ.Socket> links = null;

        for(int nodeId = 0, line = 0; line < graph.LINES; line++ ){
            for(int column = 0; column < graph.COLUMNS; column++){
                if(line==0){
                    if(nodeId==0){
                        links = new HashMap<Integer, ZMQ.Socket>();
                        //right
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId+1));
                        links.put(nodeId+1, socket);
                        //down
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId+Graph.COLUMNS));
                        links.put(nodeId+Graph.COLUMNS, socket);
                    }
                    else if( nodeId == (graph.COLUMNS-1) ){
                        links = new HashMap<Integer, ZMQ.Socket>();
                        //left
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId-1));
                        links.put(nodeId-1, socket);
                        //down
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId+Graph.COLUMNS));
                        links.put(Graph.COLUMNS, socket);
                    }
                    else {
                        links = new HashMap<Integer, ZMQ.Socket>();
                        //right
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId+1));
                        links.put(nodeId+1, socket);
                        //left
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId-1));
                        links.put(nodeId-1, socket);
                        //down
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId+Graph.COLUMNS));
                        links.put(nodeId+Graph.COLUMNS, socket);
                    }
                    Node n = new Node(links, nodeId);
                    socketNodes.put(nodeId, n);
                }
                else if(line == (Graph.LINES-1)){
                    if((nodeId%Graph.COLUMNS)==0){
                        links = new HashMap<Integer, ZMQ.Socket>();
                        //right
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId+1));
                        links.put(nodeId+1, socket);
                        //up
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId-Graph.COLUMNS));
                        links.put(nodeId-Graph.COLUMNS, socket);
                    }
                    else if((nodeId%Graph.COLUMNS)==(graph.COLUMNS-1)){
                        links = new HashMap<Integer, ZMQ.Socket>();
                        //left
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId-1));
                        links.put(nodeId-1, socket);
                        //up
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId-Graph.COLUMNS));
                        links.put(nodeId-Graph.COLUMNS, socket);
                    }
                    else {
                        links = new HashMap<Integer, ZMQ.Socket>();
                        //right
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId+1));
                        links.put(nodeId+1, socket);
                        //left
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId-1));
                        links.put(nodeId-1, socket);
                        //up
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId-Graph.COLUMNS));
                        links.put(nodeId-Graph.COLUMNS, socket);
                    }
                    Node n = new Node(links, nodeId);
                    socketNodes.put(nodeId, n);
                }
                else{
                    if((nodeId%Graph.COLUMNS)==0){
                        links = new HashMap<Integer, ZMQ.Socket>();
                        //right
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId+1));
                        links.put(nodeId+1, socket);
                        //up
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId-Graph.COLUMNS));
                        links.put(nodeId-Graph.COLUMNS, socket);
                        //down
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId+Graph.COLUMNS));
                        links.put(nodeId+Graph.COLUMNS, socket);
                    }
                    else if((nodeId%Graph.COLUMNS)==(graph.COLUMNS-1)){
                        links = new HashMap<Integer, ZMQ.Socket>();
                        //left
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId-1));
                        links.put(nodeId-1, socket);
                        //up
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId-Graph.COLUMNS));
                        links.put(nodeId-Graph.COLUMNS, socket);
                        //down
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId+Graph.COLUMNS));
                        links.put(nodeId+Graph.COLUMNS, socket);
                    }
                    else {
                        links = new HashMap<Integer, ZMQ.Socket>();
                        //right
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId+1));
                        links.put(nodeId+1, socket);
                        //left
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId-1));
                        links.put(nodeId-1, socket);
                        //up
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId-Graph.COLUMNS));
                        links.put(nodeId-Graph.COLUMNS, socket);
                        //down
                        socket = context.socket(ZMQ.PUSH);
                        socket.connect("tcp://localhost:"+(Graph.PORT+nodeId+Graph.COLUMNS));
                        links.put(nodeId+Graph.COLUMNS, socket);
                    }
                    Node n = new Node(links, nodeId);
                    socketNodes.put(nodeId, n);
                }
                nodeId++;
            }
        }

        for(Node n : socketNodes.values()) {
            n.start();
            System.out.println(n.toString());
        }

        //socketNodes.get(5).incrementMyState();
        socketNodes.get(5).sendBroadcast();
        socketNodes.get(1).sendBroadcast();
        socketNodes.get(1).sendBroadcast();
        socketNodes.get(9).sendBroadcast();
        socketNodes.get(9).sendBroadcast();
        socketNodes.get(9).sendBroadcast();
        socketNodes.get(9).sendBroadcast();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Node n : socketNodes.values()) {
            System.out.println(n.toString());
        }
    }
}
