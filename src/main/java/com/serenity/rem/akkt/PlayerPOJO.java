package com.serenity.rem.akkt;

/**
 * Created by Daniele on 02.10.2016.
 */
public class PlayerPOJO {

    public String ID;
    public int level;
    public double hp;
    public double maxHP;
    public double attackdmg;
    public double def;
    public double EXP;
    public Enemy e;
    public boolean onAdv;
    public boolean idle;
    public boolean alive;
    public Inventory inv;
    public int skillPoints;
    public double skillHp;


    public String getID(){
        return ID;
    }

}
