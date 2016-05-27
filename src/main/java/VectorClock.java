import java.io.Serializable;

/**
 * Created by carlosmorais on 24/05/16.
 */
public class VectorClock  implements Serializable{
    private int[] vc;
    private int selfId;

    public VectorClock(int size, int id){
        this.selfId = id;
        this.vc = new int[size];
        for (int i=0; i<this.vc.length; i++ ) {
            this.vc[i] = 0;
        }
    }

    public VectorClock(int[] vc, int id){
        this.vc = vc;
        this.selfId = id;
    }

    public int getId() {
        return selfId;
    }

    public VectorClock getVC(){
        this.incrementValue(this.selfId);
        int[] vcAux = new int[this.vc.length];
        for (int i=0; i<this.vc.length; i++ ) {
            vcAux[i] = this.vc[i];
        }
        return new VectorClock(vcAux, selfId);
    }

    public synchronized void incrementValue(int id){
        this.vc[id]++;
    }


    public boolean caused(VectorClock vcR){
        boolean caused = true;
        int id = vcR.getId();

        if(vc[id] == (vcR.vc[id]-1)){
            for(int i=0; i<vc.length; i++){
                if((i!=id) && (vc[i] < vcR.vc[i])){
                    caused = false;
                }
            }
        }
        else
            caused = false;
        return caused;
    }


    public String toString(){
        StringBuilder s = new StringBuilder();
        boolean flag=false;
        s.append("VC [");
        for (int i=0; i<vc.length; i++){
            if (flag)
                s.append(",");
            else
                flag = true;
            s.append(vc[i]);
        }
        s.append("]");
        return s.toString();
    }
}
