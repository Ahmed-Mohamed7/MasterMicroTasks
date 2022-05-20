package main.components;

import java.awt.*;
import java.util.ArrayList;

/**
 * This Class is responsible for the topology which is container of Component
 */
public class Topology {
    private String id;
    private ArrayList<Component> components=new ArrayList<Component>();

    /**
     *
     * @param id
     * @param components : list of the components for topology
     */
    public Topology(String id , ArrayList<Component>components){
        this.id = id;
        this.components = components;
    }

    /**
     * Set id of topology
     * @param id
     */
    public void setID(String id){
        this.id = id;
    }

    /**
     * get id of topology
     * @return
     */
    public String GetID(){
        return this.id;
    }

    /**
     * set the components list of topology
     * @param components
     */
    public void SetComponents(ArrayList<Component>components){
        this.components = components;
    }

    /**
     * get components list for topology
     * @return
     */
    public ArrayList<Component> GetComponents(){
        return this.components;
    }

    public void Display(){
        for( Component com : components ){
            System.out.println("\t\t\t" + com.GetId());
        }
    }



}
