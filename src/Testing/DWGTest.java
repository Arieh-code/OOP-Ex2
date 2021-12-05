package Testing;

import Ex2.DWG;
import Ex2.Edge;
import Ex2.GNode;
import Ex2.Point3D;
import api.EdgeData;
import api.NodeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGTest {

    Point3D p1 = new Point3D(35.19589389346247,32.10152879327731,0.0);
    Point3D p2 = new Point3D(35.20319591121872,32.10318254621849,0.0);
    Point3D p3 = new Point3D(35.20752617756255,32.1025646605042,0.0);
    Point3D p4 = new Point3D(35.21007339305892,32.10107446554622,0.0);
    Point3D p5 = new Point3D(35.21310882485876,32.104636394957986,0.0);

    NodeData a = new GNode(0, p1);
    NodeData b = new GNode(1, p2);
    NodeData c = new GNode(2, p3);
    NodeData d = new GNode(3, p4);
    NodeData e = new GNode(4, p5);

    EdgeData e1 = new Edge(0,1.0, 3);
    EdgeData e2 = new Edge(0,2.0, 2);
    EdgeData e3 = new Edge(1,3.5, 2);
    EdgeData e4 = new Edge(4,5.3, 1);
    EdgeData e5 = new Edge(3,2.4, 2);

    DWG dwg = new DWG();

    @Test
    void getNode() {
        dwg.addNode(a);
        assertEquals(dwg.getNode(0), a);
    }

    @Test
    void getEdge() {
        dwg.addNode(a);
        dwg.addNode(c);
        dwg.connect(a.getId(), c.getId(), 1.5);
        EdgeData e1 = new Edge(0,1.5, 2);
        assertEquals(dwg.edgeMap.get(a.getId()).get(c.getId()).getSrc(), e1.getSrc());
        assertEquals(dwg.edgeMap.get(a.getId()).get(c.getId()).getDest(), e1.getDest());
        assertEquals(dwg.edgeMap.get(a.getId()).get(c.getId()).getW(), e1.getW());
    }

    @Test
    void addNode() {
        assertNull(dwg.getNode(0));
        dwg.addNode(a);
        assertEquals(dwg.getNode(0), a);
    }

    @Test
    void connect() {
        assertEquals(0, dwg.edgeSize());
        dwg.addNode(a);
        dwg.addNode(c);
        dwg.connect(a.getId(), c.getId(), 1.0);
        assertEquals(1, dwg.edgeSize());
    }

    @Test
    void nodeIter() {
    }

    @Test
    void edgeIter() {
    }

    @Test
    void testEdgeIter() {
    }

    @Test
    void removeNode() {
        assertEquals(0, dwg.nodeSize());
        dwg.addNode(a);
        assertEquals(dwg.getNode(a.getId()), a);
        dwg.removeNode(a.getId());
        assertEquals(0, dwg.nodeSize());
        assertNull(dwg.getNode(a.getId()));
    }

    @Test
    void removeEdge() {
        assertEquals(0, dwg.edgeSize());
        dwg.addNode(a);
        dwg.addNode(c);
        dwg.connect(a.getId(), c.getId(), 1.0);
        assertEquals(1, dwg.edgeSize());
        dwg.removeEdge(a.getId(), c.getId());
        assertEquals(0, dwg.edgeSize());

    }

    @Test
    void nodeSize() {
        assertEquals(dwg.nodeSize(), 0);
        dwg.addNode(a);
        dwg.addNode(c);
        assertEquals(dwg.nodeSize(), 2);
        dwg.removeNode(a.getId());
        assertEquals(dwg.nodeSize(), 1);
    }

    @Test
    void edgeSize() {
        assertEquals(0, dwg.edgeSize());
        dwg.addNode(a);
        dwg.addNode(c);
        dwg.connect(a.getId(), c.getId(), 1.278647826423);
        assertEquals(1, dwg.edgeSize());
    }

    @Test
    void getMC() {
        assertEquals(0, dwg.getMC());
        dwg.addNode(a);
        assertEquals(1, dwg.getMC());
        dwg.removeNode(a.getId());
        assertEquals(2, dwg.getMC());
        dwg.addNode(a);
        dwg.addNode(c);
        dwg.connect(a.getId(), c.getId(), 2.0);
        assertEquals(5, dwg.getMC());
        dwg.removeEdge(0, 2);
        assertEquals(6, dwg.getMC());
    }
}