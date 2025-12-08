package Theresa.helper;

import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;

import java.util.ArrayList;

public class DungeonHelper {
    public static void connectNode(MapRoomNode src, MapRoomNode dst) {
        src.addEdge(new MapEdge(src.x, src.y, src.offsetX, src.offsetY, dst.x, dst.y, dst.offsetX, dst.offsetY, false));
    }

    public static ArrayList<MapRoomNode> createRow(int row){
        return createRow(row, new ArrayList<>());
    }

    public static ArrayList<MapRoomNode> createRow(int row, MapRoomNode node){
        ArrayList<MapRoomNode> tmp = new ArrayList<>();
        tmp.add(node);
        return createRow(row,tmp);
    }

    public static ArrayList<MapRoomNode> createRow(int row,MapRoomNode node1,MapRoomNode node2){
        ArrayList<MapRoomNode> tmp = new ArrayList<>();
        tmp.add(node1);
        tmp.add(node2);
        return createRow(row,tmp);
    }

    public static ArrayList<MapRoomNode> createRow(int row,MapRoomNode node1,MapRoomNode node2,MapRoomNode node3){
        ArrayList<MapRoomNode> tmp = new ArrayList<>();
        tmp.add(node1);
        tmp.add(node2);
        tmp.add(node3);
        return createRow(row,tmp);
    }

    public static ArrayList<MapRoomNode> createRow(int row,ArrayList<MapRoomNode> nodes){
        ArrayList<MapRoomNode> tmp = new ArrayList<>();
        for(int i =0;i<7;i++) {
            tmp.add(new MapRoomNode(i,row));
        }
        for(int i =0;i<nodes.size();i++){
            tmp.set(nodes.get(i).x,nodes.get(i));
        }
        return tmp;
    }
}
