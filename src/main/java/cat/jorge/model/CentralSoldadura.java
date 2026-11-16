/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.jorge.model;

import java.util.concurrent.Semaphore;
/**
 *
 * @author Usuario
 */
public class CentralSoldadura {
    private final int nombreEstacions;
    private final Semaphore sem;
    private final boolean[] estacions;
    private final Semaphore mutex;

    public CentralSoldadura(int nombreEstacions) {
        this.nombreEstacions = nombreEstacions;
        this.sem = new Semaphore(nombreEstacions);
        this.estacions = new boolean[nombreEstacions];
        this.mutex = new Semaphore(1);
    }
    

    public int agafa(int id) throws InterruptedException{
        sem.acquire();
        int num = -1;
        synchronized (this){
            for (int i = 0;i<estacions.length;i++){
                if (!estacions[i]){
                    estacions[i] = true;
                    num = i;
                    IO.println("El robot " + id +" ha ocupat l'estacio " + (i+1));
                    break;
                }
            }
        }
        return num;
    }
    
    public void allibera(int id, int num){
        synchronized (this){
            IO.println("El robot " + id +" ha alliberat l'estacio " + (num+1));
            estacions[num] = false;
            
        }
        sem.release();
    }
    
    public void registreCentral(int id) throws InterruptedException{
        mutex.acquire();
        try{
            IO.println("El robot " + id +" esta firmant");
            Thread.sleep(200);
        }finally{
            mutex.release();
        }
    }
    
    public int getEstacionsOcupades(){
        int num = 0;
        synchronized (this){
            for (int i = 0;i<estacions.length;i++){
                if(estacions[i]){
                    num++;
                }
            }
        }
        return num;
    }
    public int getTotalEstacions(){
        return nombreEstacions;
    }
}
