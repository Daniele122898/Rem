/**
 * Created by Daniele on 01.10.2016.
 */
public class player {

    private static String ID;
    private static int level;
    private static int hp;
    private static int attackdmg;
    private static int def;



    public player(String ID, int level, int hp, int attackdmg, int def){
        this.ID = ID;
        this.level = level;
        this.hp = hp;
        this.attackdmg = attackdmg;
        this.def = def;
    }

    public static String getID(){
        return ID;
    }

    public static int getLvl(){
        return level;
    }
    public static int getHp(){
        return hp;
    }
    public static int getAttackdmg(){
        return attackdmg;
    }
    public static int getDef(){
        return def;
    }

}
