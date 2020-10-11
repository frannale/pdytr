import java.io.Serializable;

public class BufferControlado implements Serializable{

    int cantidad;
    byte[] buffer;

    public BufferControlado(int cantidad, byte[] buffer){
        this.cantidad = cantidad;
        this.buffer = buffer;

    }

    public int getCantidad(){
        return this.cantidad;
    }

    public byte[] getBuffer(){
        return this.buffer;
    }
    
}