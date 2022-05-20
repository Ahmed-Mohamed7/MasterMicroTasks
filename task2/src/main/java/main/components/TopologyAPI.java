package main.components;

import java.io.*;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * this class handle all feature and functions of api
 */
public class TopologyAPI {

    private static HashMap<String, Topology> topologyArray = new HashMap<>();

    /**
     * this function responsible for read json file and get data from it
     * @param FileName String
     * @return  Boolean if Json read successfully or not
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Boolean ReadJson(String FileName) throws FileNotFoundException , IOException{
        try {
            String StrData = GetContent("indata\\"+FileName);
            JSONObject jsonData = new JSONObject(StrData);
            String topologyId = jsonData.getString("id");
            if(topologyArray.containsKey(topologyId)) return false;
            ArrayList<Component> coms = new ArrayList<Component>();
            HashMap<String,Object> properties = new HashMap<String,Object>();
            JSONArray array = jsonData.getJSONArray("components");
            for(int i=0; i<array.length(); i++){
                JSONObject component = array.getJSONObject(i);
                String type = component.getString("type");
                String  id = component.getString("id");
                HashMap<String,JSONObject> property =  GetAllProperties(component);
               HashMap<String,String> netlist = SetNetList(component.getJSONObject("netlist"));
                // create new Component
                if(type == "resistor") {
                    coms.add(new Resistor(id, type, netlist, property));
                }else{
                    coms.add(new Transistor(id, type, netlist, property));
                }
            }
            topologyArray.put(topologyId,new Topology(topologyId,coms));
            return true;
        }
        catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException(fileNotFoundException.getMessage());
        } catch (IOException | JSONException iOException) {
            throw new IOException(iOException.getMessage());
        }
    }


    /**
     * this function responsible for write topolgy with given id into file(json)
     * @param id String
     * @return Boolean if the json written successfully or not
     * @throws JSONException
     */
    public static boolean WriteJson(String id) throws JSONException {
        Topology topo =  getTopology(id);
        if(topo == null)
            return false;
        // create new json object that will be return
        JSONObject topology = new JSONObject();
        // add id
        topology.put("id",topo.GetID());
        ArrayList<Component>componentsArray =  topo.GetComponents();
        ArrayList<JSONObject>componentsArrayJson = new ArrayList<JSONObject>();
        // iterate over the components
        for (Component component:componentsArray) {
            // set new json for each component
            JSONObject jsonComponent = new JSONObject();
            // set the values for the json
            jsonComponent.put("type",component.GetType());
            jsonComponent.put("id",component.GetId());
            jsonComponent.put("netList", new JSONObject(component.GetNetList()));
            for (Map.Entry<String, JSONObject> set :
                    component.GetProperties().entrySet()) {
                jsonComponent.put(set.getKey(), set.getValue());
            }
            componentsArrayJson.add(jsonComponent);
        }
        topology.put("component",new JSONArray(componentsArrayJson));

        String jsonString = topology.toString();
        try {
            FileWriter myWriter = new FileWriter("outdata\\res.json");
            myWriter.write(jsonString);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return true;
    }

    /**
     * this function responsible for set the right format for netlist (HashMap)
     * @param  net JSONObject
     * @return HashMap<String, String>
     * @throws JSONException
     */
    public static HashMap<String, String> SetNetList(JSONObject net) throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        Iterator<String> keysItr = net.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            String value = net.get(key).toString();
            map.put(key, value);
        }
        return map;
    }

    /**
     * this function responsible for read file and String contains its content
     * @param filePath String
     * @return String
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String GetContent(String filePath) throws FileNotFoundException , IOException {
        // reference :https://stackoverflow.com/questions/33638765/how-to-read-json-data-from-txt-file-in-java
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String strData = "";
        try {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = reader.readLine();
            }
            strData = sb.toString();
        }
        finally {
            reader.close();
        }
        return strData;
    }

    /**
     * this function responsible for Deleting a Topology given id
     * @param TopologyID
     * @return Boolean
     */
    public static boolean DeleteTopology(String TopologyID){
        if(topologyArray.containsKey(TopologyID)){
            topologyArray.remove(TopologyID);
            return true;
        }
        else return false;
    }

    /**
     * this function responsible for get all components(Device) in given topology
     * @param TopologyID
     * @return ArrayList<Component>
     */
    public static ArrayList<Component> QueryDevices(String TopologyID){

        Topology topology = getTopology(TopologyID);
        if(topology != null){
            return topology.GetComponents();
        }else{
            return new ArrayList<Component>();
        }
    }

    /**
     * this function responsible for Get all properties expect id,type,netlist for a component
     * @param data
     * @return HashMap<String,JSONObject>
     * @throws JSONException
     */
    private static HashMap<String,JSONObject> GetAllProperties(JSONObject data) throws JSONException {
        Iterator < ? > keys = data.keys();
        HashMap<String,JSONObject> allProperties = new HashMap<String,JSONObject> ();
        while (keys.hasNext()) {
            String key = (String) keys.next();

            if (data.get(key) instanceof JSONObject && key!="type" && key!="id" && !key.equals("netlist")) {
                allProperties.put(key,data.getJSONObject(key));
            }
        }
        return allProperties;
    }

    /**
     * this function responsible for get the devices(components) that matches TopologyID and netListID
     * @param TopologyID
     * @param netListID
     * @return ArrayList<Component>
     */
    public static ArrayList<Component> QueryDevicesWithNetListNode(String TopologyID, String netListID){
        Topology topology = getTopology(TopologyID);
        if(topology == null)
            return new ArrayList<Component>();
        return QueryDevicesWithNetListNode2(topology,netListID);

    }


    public static ArrayList<Component> QueryDevicesWithNetListNode2(Topology Topology, String netListID){
        ArrayList<Component>allComponents = Topology.GetComponents();
        ArrayList<Component>matchedComponent = new ArrayList<>();
        for (Component com: allComponents) {
            if(com.GetNetList().containsKey(netListID))
                matchedComponent.add(com);
        }
        return matchedComponent;

    }


    /**
     * this function responsible for get Topology by ID
     * @param TopologyID
     * @return Topology
     */
    private static Topology getTopology(String TopologyID){
        if (topologyArray.containsKey(TopologyID)){
            return topologyArray.get(TopologyID);
        }
        return null;
    }

    /**
     * this function responsible for get all topologies
     * @return Collection<Topology>
     */
    public static Collection<Topology> QueryTopology(){
        return topologyArray.values();
    }

    private static Boolean CheckID(String id){
        if(topologyArray.containsKey(id))
            return false;
        return true;
    }
}
