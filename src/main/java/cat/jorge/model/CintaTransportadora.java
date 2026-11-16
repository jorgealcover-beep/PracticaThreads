/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.jorge.model;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
/**
 *
 * @author Usuario
 */
public class CintaTransportadora {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition cintaNoPlena = lock.newCondition();
    private final Condition cintaNoBuida = lock.newCondition();
    public ArrayList<String> bateries;
    public int capacitat;

    public CintaTransportadora(int capacitat) {
        this.capacitat = capacitat;
        this.bateries = new ArrayList<>();
    }
    
    
    
    public void afegirBateria(int id, int comptadorBat) throws InterruptedException{
        lock.lock();
        try{
            while (bateries.size()>=capacitat){
                cintaNoPlena.await();
            }
            bateries.add("Bat-" +comptadorBat);
            IO.println("AGV " + id + " ha afegit " + bateries.getLast());
            cintaNoBuida.signal();
        }finally{
            lock.unlock();
        }
        
    }
    
    public String extreuBateria(int id) throws InterruptedException{
        lock.lock();
        try{
            while(bateries.isEmpty()){
                cintaNoBuida.await();
            }
            IO.println("El robot " + id + " ha extret " + bateries.getFirst());
            String bat = bateries.remove(0);
            cintaNoPlena.signal();
            return bat;
        }finally{
            lock.unlock();
        }
    }
    
    public int getBateriesActuals(){
        lock.lock();
        try{
            return bateries.size();
        }finally{
            lock.unlock();
        }
    }
    
    public int getCapacitat(){
        return capacitat;
    }
}
