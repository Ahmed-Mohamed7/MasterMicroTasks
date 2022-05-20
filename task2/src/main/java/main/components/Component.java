package main.components;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * this class represent instance component
 */
public abstract class Component {

    private String id;
    private String type;
    private HashMap<String,String>netList;
    private HashMap<String, JSONObject>spec;

    /**
     *
     * @param id  id of component in Components list
     * @param type   type of component in Components list
     * @param netList  netlist of component in Components list
     * @param spec   specifications of component in Components list
     */
    public Component (final String id,final String type ,HashMap<String,String> netList,HashMap<String,JSONObject> spec){
        this.id = id;
        this.type = type;
        this.netList = netList;
        this.spec = spec;
    }

    // Setters and Getters

    /**
     * Set the id of component instance
     * @param id
     */
    public void SetId(String id){
        this.id = id;
    }

    /**
     * retrieve the id of component instance
     * @return String ID
     */
    public String GetId(){
        return id;
    }

    /**
     * Set the type of component instance
     * @param type
     * @return String TYPE
     */
    public void SetType(String type){
        this.type = type;
    }

    /**
     * retrieve the type of component instance
     * @return String
     */
    public String GetType(){
        return  type;
    }

    /**
     * get the netlist of component instance
     * @return HashMap<String,String> Netlist
     */
    public HashMap<String,String> GetNetList(){
        return netList;
    }


    /**
     * set the netlist of component instance
     * @param netList
     */
    public void SetNetList(HashMap<String,String>netList){
        this.netList = netList;
    }

    /**
     * Add element to netlist
     * @param Key
     * @param Val
     */
    public void AddToNetList(String Key, String Val){
        netList.put(Key, Val);
    }

    /**
     * retrieve the specification of component instance
     * @return HashMap<String,JSONObject>
     */
    public HashMap<String,JSONObject> GetProperties(){return this.spec;}

    /**
     * print the current Device
     */
    abstract void PrintDevice();
}
