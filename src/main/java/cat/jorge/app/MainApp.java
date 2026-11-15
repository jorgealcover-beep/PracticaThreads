/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cat.jorge.app;

import cat.jorge.model.*;
import cat.jorge.tasks.*;
import cat.jorge.view.*;
/**
 *
 * @author Usuario
 */
public class MainApp {

    public static void main(String[] args) throws InterruptedException {
        SupervisorCentral supervisor = new SupervisorCentral();
        CentralSoldadura central = new CentralSoldadura(4);
        CintaTransportadora cinta = new CintaTransportadora(5);
        
        int numRobots = 6;
        Thread[] robots = new Thread[numRobots];
        for (int i = 0; i<numRobots;i++){
            robots[i] = new Thread(new RobotTask(i+1,cinta,supervisor,central));
        }
        int numAGV = 6;
        Thread[] AGV = new Thread[numAGV];
        for (int i = 0; i<numAGV;i++){
            AGV[i] = new Thread(new AGVTask(i+1, cinta));
        }
        
        Thread filDashboard = new Thread(new DashboardTask(central, cinta, supervisor, robots));
        filDashboard.setDaemon(true);
        
        
        filDashboard.start();
        
        for (int i = 0; i<numRobots;i++){
            robots[i].start();
        }
        
        for (int i = 0; i<numAGV;i++){
            AGV[i].start();
        }
        
        Thread.sleep(4000);
        
        supervisor.activaAlerta();
        
        Thread.sleep(2500);
        
        supervisor.desactivaAlerta();
        
        Thread.sleep(2500);
        
        for (int i = 0; i<numRobots;i++){
            robots[i].interrupt();
        }
        
        for (int i = 0; i<numAGV;i++){
            AGV[i].interrupt();
        }
        for (int i = 0; i<numRobots;i++){
            robots[i].join();
        }
        
        for (int i = 0; i<numAGV;i++){
            AGV[i].join();
        }
        
        IO.println("Resum:");
        IO.println("Estacions ocupades: " + central.getEstacionsOcupades() + " de " + central.getTotalEstacions());
        IO.println("Bateries en la cinta: " + cinta.getBateriesActuals() + " de " + cinta.getCapacitat());
    }
    
}
