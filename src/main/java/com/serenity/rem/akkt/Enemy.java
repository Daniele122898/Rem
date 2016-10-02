package com.serenity.rem.akkt;

import com.serenity.rem.modules.RandomNumberGen;


public class Enemy {
    private String ID;
    private int level;
    private double hp;
    private double attackdmg;
    private double def;
    private boolean alive = false;
    public Inventory inv;



    public Enemy(String ID, int playerLevel){
        this.ID = ID;
        level = generateLvl(playerLevel);
        hp = level * RandomNumberGen.getRandIntBetween(30,50);
        attackdmg = (int) (level * RandomNumberGen.getRandIntBetween(8,16) *0.8);

        def = RandomNumberGen.getRandint(level);
        update();
        alive = true;
        inv = new Inventory();
        generateInv();
    }

    private void generateInv(){
        inv.add(new Gold(this, Player.itemL[0] , RandomNumberGen.getRandIntBetween(level * 2,level * 13)));
    }

    private int generateLvl(int plvl){
        //playerLevel + rand(0, X) * randomSign
        int level = plvl + RandomNumberGen.getRandIntBetween(0,3) * RandomNumberGen.randomSign();
        if (level <1)
            level = 1;
        return level;
    }

    private void update(){

    }

    public double attack(double dmg){
        double realDmg = dmg - (def/2);
        if(realDmg <0){
            realDmg = 0;
        }
        hp -= realDmg;
        if(hp <=0){
            alive = false;
            return 0.0;
        }

        return attackdmg;
    }

    public String getID(){
        return ID;
    }

    public int getLvl(){
        return level;
    }
    public double getHp(){
        return hp;
    }
    public double getAttackdmg(){
        return attackdmg;
    }
    public double getDef(){
        return def;
    }
    public boolean isAlive(){
        return alive;
    }
}
