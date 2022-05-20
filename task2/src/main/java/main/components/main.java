package main.components;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class main {
    public static void main(String[] args){

        try{
            TopologyAPI topoApi = new TopologyAPI();
            System.out.println("### Read JSon #####");
            if(topoApi.ReadJson("topology.json"))
                System.out.println("Read Json FIle Successfully");
            else  System.out.println("Falied to Read Json FIle");

            System.out.println("### QueryTopology #####");
            Collection topos =  topoApi.QueryTopology();
            System.out.println(topos.size());

            System.out.println("### Write Json #####");
            if(topoApi.WriteJson("top1"))
                System.out.println("Write Json FIle Successfully");
            else  System.out.println("Falied to Write Json FIle");

            System.out.println("### QueryDevices #####");
            ArrayList<Component>Devices =  topoApi.QueryDevices("top1");
            for (Component com:Devices
                 ) {
                System.out.println(com.GetId());
            }
            System.out.println("### QueryDevicesWithNetListNode #####");
            for (Component com:topoApi.QueryDevicesWithNetListNode("top1","t1")) {
                System.out.println(com.GetId());
            };

            System.out.println("### Delete Topology #####");
            topoApi.DeleteTopology("top1");
            System.out.println(topoApi.QueryTopology().size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
