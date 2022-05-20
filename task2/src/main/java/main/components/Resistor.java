package main.components;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * this class represent resistor components which is inherted from component class
 */
public class Resistor extends Component{

    /**
     *
     * @param id
     * @param type
     * @param netList
     * @param spec
     */
    public Resistor(String id, String type, HashMap<String, String> netList, HashMap<String, JSONObject> spec) {
        super(id, type, netList, spec);
    }
    @Override
    void PrintDevice(){
        System.out.println("\t\t\t*************   Resistor    *************");
        System.out.println("\t\t\t ID: " + this.GetId());
        System.out.println("\t\t\t TYPE :" + this.GetType());
        System.out.println("\t\t\t NETlist: " + this.GetNetList());
        System.out.println("\t\t\t Properties: " + this.GetProperties());
        System.out.println("\t\t\t" + "*************   Resistor    *************");
    }
}
