/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.jorge.tasks;

import java.util.Random;

import cat.jorge.model.CentralSoldadura;
import cat.jorge.model.CintaTransportadora;
import cat.jorge.model.SupervisorCentral;

/**
 *
 * @author Usuario
 */
public class RobotTask implements Runnable {
    private final int id;
    private final CintaTransportadora cinta;
    private final SupervisorCentral supervisor;
    private final CentralSoldadura central;
    Random rand = new Random();
    

    public RobotTask(int id, CintaTransportadora cinta, SupervisorCentral supervisor, CentralSoldadura central) {
        this.id = id;
        this.cinta = cinta;
        this.supervisor = supervisor;
        this.central = central;
    }
    
    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                supervisor.verificaEstat(id);
                String bat = cinta.extreuBateria(id);
                int num = central.agafa(id);
                try{
                    Thread.sleep(rand.nextInt(401)+500);
                    central.registreCentral(id);
                }finally{
                    central.allibera(id,num);
                }
                
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
