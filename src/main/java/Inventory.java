import java.util.ArrayList;
import java.util.List;

public class Inventory {

    //private Item[] items;
    private List<Item> items;

    public Inventory(){
        items = new ArrayList<Item>();
    }

    public void add(Item item){
        /*for(int i = 0; i < items.length; i++){
            if(items[i] == null){
                items[i] = item;
                break;
            }
        }*/
        items.add(item);
    }

    public Item get(int index){
        return items.get(index);
        //return items[index];
    }
    public Item get(String name1){
        /*for(int i= 0; i < items.length; i++){
            if(items[i].name.equals(name1)){
                return items[i];
            }
        }
        return null;*/
        for (Item t: items) {
            if(t.name.equals(name1))
                return t;
        }

        return null;
    }

   /* public boolean exists(String name2){
        for(int i = 0; i < items.length; i++){
            if(items[i].name.equalsIgnoreCase(name2))
                return true;
        }
        return false;
    }*/

    public int getSize(){
        return items.size();
    }


}
