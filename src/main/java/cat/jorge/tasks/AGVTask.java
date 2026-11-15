/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.jorge.tasks;

import cat.jorge.model.CintaTransportadora;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
/**
 *
 * @author Usuario
 */
public class AGVTask implements Runnable{
    private final CintaTransportadora cinta;
    private final int id;
    private static final AtomicInteger comptadorBat = new AtomicInteger(0);
    final Random rand = new Random();
    
    public AGVTask(int id, CintaTransportadora cinta){
        this.id = id;
        this.cinta = cinta;
    }
    
    @Override
    public void run(){
        try{
            while (!Thread.currentThread().isInterrupted()){
                cinta.afegirBateria(id,comptadorBat.incrementAndGet());
                Thread.sleep(rand.nextInt(401)+500);
            }
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
    
}
