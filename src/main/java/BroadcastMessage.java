import java.io.Serializable;

/**
 * Created by carlosmorais on 23/05/16.
 */
public class BroadcastMessage implements Serializable {
    private int id;
    private String msg; //to test
    private VectorClock vc;
    int nodeOrigin;
    int nodeSender;

    public BroadcastMessage(int id, int node, VectorClock vc, String msg) {
        this.vc = vc;
        this.msg = msg;
        this.id = id;
        this.nodeOrigin = node;
        this.nodeSender = node;
    }

    public String getMsg() {
        return msg;
    }

    public int getId() {
        return id;
    }

    public int getNodeOrigin() {
        return nodeOrigin;
    }

    public void setNodeSender(int node) {
        this.nodeSender = node;
    }

    public VectorClock getVc() {
        return vc;
    }
}
