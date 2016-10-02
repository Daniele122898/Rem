/**
 * Created by Daniele on 01.10.2016.
 */
public class Gold extends Item{
    public Gold(Enemy e , String name, int amount){
        super(e, name, amount);
    }

    public Gold(Player p , String name, int amount){
        super(p, name, amount);
    }

}
