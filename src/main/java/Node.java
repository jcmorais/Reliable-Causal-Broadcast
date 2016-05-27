import Utils.ObjectSerializable;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by carlosmorais on 23/05/16.
 */
public class Node extends Thread{
    private HashMap<Integer, ZMQ.Socket> links; //<#Node, socket>
    private ZMQ.Context context;
    private ZMQ.Socket socket;
    private List<BroadcastMessage> queu;
    private VectorClock vc;
    private int node; //my ID

    private int count;
    private int[] nodesMsgCount;

    public Node(HashMap<Integer, ZMQ.Socket> connectTo, int node){
        this.context = ZMQ.context(1);
        this.socket = context.socket(ZMQ.PULL);
        int aux = (Graph.PORT+node);
        this.socket.bind("tcp://*:" + aux);
        this.links = connectTo;
        this.vc = new VectorClock(Graph.N_NODES, node);
        this.node = node;
        this.queu = new ArrayList<BroadcastMessage>();
        this.count = 0;
        this.nodesMsgCount = new int[Graph.N_NODES];
        for(int i=0; i<nodesMsgCount.length; i++)
            nodesMsgCount[i]=0;
    }

    public void sendBroadcast(){
        BroadcastMessage msg = new BroadcastMessage(count, node, vc.getVC(), "");
        count++;
        for (ZMQ.Socket socket : this.links.values()) {
            socket.send(ObjectSerializable.ObjectToBytes(msg));
        }
    }

    public void forward(BroadcastMessage msg){
        int nodeOrigin = msg.getNodeOrigin();
        msg.setNodeSender(this.node);
        for (Integer nodeId : this.links.keySet()) {
            if(nodeId != nodeOrigin) {
                this.links.get(nodeId).send(ObjectSerializable.ObjectToBytes(msg));
            }
        }
    }


    // TODO: 24/05/16 será que percebi que pq temos uma queu??? 
    // TODO: 24/05/16 FIFO? jGroups? ou que raio???
    public void deliver(BroadcastMessage msg){
        this.queu.add(msg);
        Iterator<BroadcastMessage> it = this.queu.iterator();
        boolean flag = true;

        while (flag) {
            flag = false;
            while (it.hasNext()) {
                BroadcastMessage m = it.next();
                if (vc.caused(m.getVc())) {
                    flag = true;
                    log("new caused msg!");
                    // TODO: 24/05/16 duvida: só atualizo o valor do sender da mensagem??? assim?
                    vc.incrementValue(m.getVc().getId()); // TODO: 24/05/16  ou adiciono o valor que esta no vetor caused??
                    it.remove(); //remove BroadcastMessage m
                    log("new vc : " + vc.toString());
                }
                // TODO: 24/05/16 tenho que verificar isto periodicamente? thread? ou enquando percorrer e encontrar um caused, volto a percorrer a queu???
            }
        }
    }

    public int getNode() {
        return node;
    }

    //onReceive
    @Override
    public void run() {
        while(true){
            byte[] b = socket.recv();
            BroadcastMessage msg = (BroadcastMessage) ObjectSerializable.BytesToObject(b);

            if(this.nodesMsgCount[msg.nodeOrigin] == msg.getId()){
                this.nodesMsgCount[msg.nodeOrigin]++; //increment number of receive msg
                this.forward(msg);
                this.deliver(msg);
                log("deliver origin-"+msg.nodeOrigin+" id-"+msg.getId());
            }
            else
                log("discard a repeted msg");
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Node"+node+"  ");
        s.append(vc.toString() + "  link to : ");
        for (Integer integer : this.links.keySet()) {
            s.append(integer+" ");
        }
        return s.toString();
    }

    public void log(String s){
        System.out.println("Node"+node+": "+s);
    }
}
