import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import main.components.Component;
import main.components.Topology;
import main.components.TopologyAPI;
import org.json.JSONException;
import org.junit.Test;
public class TopologyApiTest {
    @Test
    public  void TestReadJson() throws IOException {
        TopologyAPI.DeleteTopology("top1");
        assertEquals("cannot Read topology with unique id",true,TopologyAPI.ReadJson("topology.json"));
        assertEquals("Read topology with duplicate id",false,TopologyAPI.ReadJson("topology.json"));
    }

    @Test
    public void TestWriteJson() throws JSONException {
       TopologyAPI.WriteJson("top1");
        Path path = Paths.get("E:\\mastermicro\\Master Micro\\outdata\\res.json");
        assertEquals("file not found",true,Files.exists(path));
    }

    @Test
    public void TestQueryTopology() throws IOException {
        TopologyAPI.ReadJson("topology.json");
        assertEquals(1,TopologyAPI.QueryTopology().size());
    }

    @Test
    public void TestDeleteToplogy() throws IOException {
        TopologyAPI.ReadJson("topology.json");
        TopologyAPI.DeleteTopology("top1");
        assertEquals(0,TopologyAPI.QueryTopology().size());
    }

    @Test
    public void TestQueryDevice() throws IOException {
        TopologyAPI.ReadJson("topology.json");
        assertEquals(2,TopologyAPI.QueryDevices("top1").size());
    }

    @Test
    public void TestQueryDevicesWithnetList() throws IOException {
        TopologyAPI.ReadJson("topology.json");
        ArrayList<Component> components = TopologyAPI.QueryDevicesWithNetListNode("top1","t1");
        assertEquals("resistor", components.get(0).GetType());

    }


}
