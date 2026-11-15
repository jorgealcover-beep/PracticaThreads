/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.jorge.view;

import cat.jorge.model.*;

/**
 *
 * @author Usuario
 */
public class DashboardTask implements Runnable {
    private final CentralSoldadura central;
    private final CintaTransportadora cinta;
    private final SupervisorCentral supervisor;
    private final Thread[] robots;
    boolean alerta;

    public DashboardTask(CentralSoldadura central, CintaTransportadora cinta,SupervisorCentral supervisor, Thread[] robots) {
        this.central = central;
        this.cinta = cinta;
        this.supervisor = supervisor;
        this.robots = robots;
    }
    
    @Override
    public void run(){
        while (!Thread.currentThread().isInterrupted()){
            IO.println("----Estat----");
            alerta = supervisor.isAlerta();
            if(alerta){
                IO.println("ALERTA ACTIVA");
            }else{
                IO.println("No alerta"); 
            }
            IO.println("Estacions ocupades: " + central.getEstacionsOcupades() + " de " + central.getTotalEstacions());
            IO.println("Bateries en la cinta: " + cinta.getBateriesActuals() + " de " + cinta.getCapacitat());
            for (int i = 0; i < robots.length; i++){
                IO.println("Robot " + (i+1) +": " + robots[i].getState());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
}
