/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.jorge.model;


/**
 *
 * @author Usuario
 */
public class SupervisorCentral {
    private boolean alerta;
    
    public synchronized void activaAlerta(){
        alerta = true;
    }
    
    public synchronized void desactivaAlerta(){
        alerta = false;
        notifyAll();
    }
    
    public synchronized void verificaEstat(int id) throws InterruptedException{
        while (alerta){
            wait();
        }
    }
    
    public synchronized boolean isAlerta(){
        return alerta;
    }
}
