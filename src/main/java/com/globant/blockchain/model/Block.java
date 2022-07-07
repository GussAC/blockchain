package com.globant.blockchain.model;

/**
 *
 * @author gustavo.angeles
 */
public class Block {
    private String prev_hash ;
    private String hash ;
    private String mensaje ;    
    private int once;

    public Block(String prev_hash, String mensaje, int once) {
        this.prev_hash = prev_hash;
        this.mensaje = mensaje;
        this.once = once;        
    }

    public String getPrev_hash() {
        return prev_hash;
    }

    public void setPrev_hash(String prev_hash) {
        this.prev_hash = prev_hash;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getOnce() {
        return once;
    }

    public void setOnce(int once) {
        this.once = once;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return prev_hash + "," + mensaje + "," + once;
    }

}
