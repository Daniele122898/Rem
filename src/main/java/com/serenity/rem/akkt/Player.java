package com.serenity.rem.akkt;

import com.serenity.rem.main.RemListener;
import com.serenity.rem.modules.RandomNumberGen;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.*;

public class Player {

    private String ID;
    private int level;
    private double hp;
    private double maxHP;
    private double attackdmg;
    private double def;
    private double EXP;
    private MessageReceivedEvent event;
    Enemy e;
    private boolean onAdv;
    private boolean idle;
    String[] msg;
    private boolean alive;
    private Inventory inv;
    private IMessage Mess;
    private int skillPoints;
    private double skillHp;
    private long cooldown;

                        //mHP,attackdmg, def
    private int[] scale = {1 ,13, 12};


    public static String[] itemL= new String[] {
                "Gold", "Health Potion"
    };



    public Player(String ID, int level, int hp, int attackdmg, int def){
        this.ID = ID;
        this.level = level;
        this.hp = hp;
        maxHP = hp;
        this.attackdmg = attackdmg;
        this.def = def;
        EXP = 1.0;
        update();
        onAdv = false;
        idle = true;
        alive = true;
        inv = new Inventory();
        inv.add(new Gold(this, itemL[0],50));
        cooldown = System.currentTimeMillis();
    }

    public void addItem(Item item){
        inv.add(item);
    }

    private void update(){



        int temp = level;
        level = (int)Math.sqrt(EXP);
        if(level == 1){
            maxHP = 50;
        } else{
            maxHP = level * 30 + skillHp;
        }

        if(level > temp){
            System.out.println("LEVEL UP");
            RequestBuffer.request(()->{
                try {
                    event.getMessage().getChannel().sendMessage("**Level Up! Current Level: " + level+"**");
                } catch (MissingPermissionsException | DiscordException e1) {
                    e1.printStackTrace();
                }
            });
            skillPoints += 5;
            hp += 50;
            //skillSystem(event);
        }
    }

    public void skillSystem(MessageReceivedEvent ev){//TODO
        event = ev;
        String[] msg = event.getMessage().toString().split("\\s");
        if(msg.length == 2){
            RequestBuffer.request(()->{
                try {
                    Mess = event.getMessage().getChannel().sendMessage("" +
                            "**SKILL TREE** ```Markdown\n" +
                            "#Current Stats:\n" +
                            "Level: " + level + "\n" +
                            "Max HP: " + maxHP + "\n" +
                            "Attack Dmg: " + attackdmg + "\n" +
                            "Def: " + def + "\n" +
                            "Skill Points: " + skillPoints + "\n\n" +
                            "#Choose Skills: \n" +
                            RemListener.getPre()+"ak skill hp for increased max HP\n" +
                            RemListener.getPre()+"ak skill def for increased Defense stats\n" +
                            RemListener.getPre()+"ak skill dmg for increased Attack dmg\n" +
                            "```");
                } catch (MissingPermissionsException |DiscordException e1) {
                    e1.printStackTrace();
                }
            });
        } else{
            switch (msg[2].toLowerCase()){
                case"health":
                case"max":
                case"hp":
                    scaleHealth(msg);
                    break;
                case"def":
                case"defense":
                    scaleDef(msg);
                    break;
                case"attk":
                case"dmg":
                case"attack":
                    scaleDmg(msg);
                    break;
                default:
                    try {
                        event.getMessage().getChannel().sendMessage("Failed to add Skill");
                    } catch (MissingPermissionsException | RateLimitException | DiscordException  e1) {
                        e1.printStackTrace();
                    }
                    break;
            }
        }

    }

    private void scaleHealth(String [] msg){
        if(msg.length == 3) {
            if (skillPoints != 0) {
                skillHp += 30;
                update();
                skillPoints--;
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("Successfully increased Stats");
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            } else {
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("Not enough Skill Points!");
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            }
        } else{
            if(msg[3].matches("\\d+")) {
                int askA = Integer.parseInt(msg[3]);
                if (skillPoints >= askA) {
                    skillHp += 30 * askA;
                    update();
                    skillPoints-= askA;
                    RequestBuffer.request(() -> {
                        try {
                            event.getMessage().getChannel().sendMessage("Successfully increased Stats");
                        } catch (MissingPermissionsException | DiscordException e1) {
                            e1.printStackTrace();
                        }
                    });
                } else {
                    RequestBuffer.request(() -> {
                        try {
                            event.getMessage().getChannel().sendMessage("Not enough Skill Points!");
                        } catch (MissingPermissionsException | DiscordException e1) {
                            e1.printStackTrace();
                        }
                    });
                }
            } else{
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("Amount can only consist of Numbers!");
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            }
        }



    }

    private void scaleDmg(String[] msg){
        if(msg.length == 3) {
            if (skillPoints != 0) {
                attackdmg += 3;
                update();
                skillPoints--;
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("Successfully increased Stats");
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            } else {
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("Not enough Skill Points!");
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            }
        } else{
            if(msg[3].matches("\\d+")) {
                int askA = Integer.parseInt(msg[3]);
                if (skillPoints >= askA) {
                    attackdmg += 3 * askA;
                    update();
                    skillPoints-= askA;
                    RequestBuffer.request(() -> {
                        try {
                            event.getMessage().getChannel().sendMessage("Successfully increased Stats");
                        } catch (MissingPermissionsException | DiscordException e1) {
                            e1.printStackTrace();
                        }
                    });
                } else {
                    RequestBuffer.request(() -> {
                        try {
                            event.getMessage().getChannel().sendMessage("Not enough Skill Points!");
                        } catch (MissingPermissionsException | DiscordException e1) {
                            e1.printStackTrace();
                        }
                    });
                }
            } else{
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("Amount can only consist of Numbers!");
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            }
        }
    }

    private void scaleDef(String[] msg){
        if(msg.length == 3) {
            if (skillPoints != 0) {
                def += 2;
                update();
                skillPoints--;
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("Successfully increased Stats");
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            } else {
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("Not enough Skill Points!");
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            }
        } else{
            if(msg[3].matches("\\d+")) {
                int askA = Integer.parseInt(msg[3]);
                if (skillPoints >= askA) {
                    def += 2 * askA;
                    update();
                    skillPoints-= askA;
                    RequestBuffer.request(() -> {
                        try {
                            event.getMessage().getChannel().sendMessage("Successfully increased Stats");
                        } catch (MissingPermissionsException | DiscordException e1) {
                            e1.printStackTrace();
                        }
                    });
                } else {
                    RequestBuffer.request(() -> {
                        try {
                            event.getMessage().getChannel().sendMessage("Not enough Skill Points!");
                        } catch (MissingPermissionsException | DiscordException e1) {
                            e1.printStackTrace();
                        }
                    });
                }
            } else{
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("Amount can only consist of Numbers!");
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            }
        }
    }

    public void shop(MessageReceivedEvent ev){
        event = ev;
        msg = event.getMessage().toString().split("\\s");
        if(msg.length == 2){
            showShop();
        } else{
            switch (msg[2].toLowerCase()){
                case"health":
                case"healthpotion":
                case"h":
                    System.out.println("GOTO BUYING POTION");
                    buyPotion(event);
                    break;
                default:
                    try {
                        event.getMessage().getChannel().sendMessage("Product not found");
                    } catch (MissingPermissionsException | RateLimitException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                    break;
            }
        }
    }

    public void inve(MessageReceivedEvent ev){
        event = ev;
        MessageBuilder msgB = new MessageBuilder(event.getClient()).withChannel(event.getMessage().getChannel());
        RequestBuffer.request(()->{
            try {
                msgB.appendContent("**INVENTORY** ```Markdown\n");
                for(int i = 0; i< inv.getSize();i++){
                    if(inv.get(i) != null){
                        String temp = inv.get(i).name + " | " + inv.get(i).amount+"\n";
                        msgB.appendContent(temp);
                    }
                }
                msgB.appendContent("\n ```");
                msgB.send();
            } catch (MissingPermissionsException | DiscordException e1) {
                e1.printStackTrace();
            }
        });

    }

    public void use(MessageReceivedEvent ev){
        event = ev;
        String[] msg = event.getMessage().toString().split("\\s");
        if(msg.length == 2){
            try {
                event.getMessage().getChannel().sendMessage("**USE: " + RemListener.getPre()+"ak use <First word of Item Name>** to use an item");
            } catch (MissingPermissionsException | RateLimitException | DiscordException e1) {
                e1.printStackTrace();
            }
        } else {
            switch (msg[2].toLowerCase()){
                case"health":
                case"heal":
                case"h":
                    useHealth(msg);
                    break;
                default:
                    try {
                        event.getMessage().getChannel().sendMessage("Item not found!");
                    } catch (MissingPermissionsException | RateLimitException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                    break;
            }
        }

    }

    private void useHealth(String[] msg){
        if(msg.length == 3) {
            if (inv.get(itemL[1]) == null) {
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("You dont have the Item!");
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            } else if (inv.get(itemL[1]).amount < 1) {
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("You dont have the Item!");
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            } else {
                inv.get(itemL[1]).minusAmount(1);
                increaseHealth(30);
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("You successfully used " + itemL[1]);
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            }
        } else{
            if(msg[3].matches("\\d+")) {
                int askA = Integer.parseInt(msg[3]);
               if(inv.get(itemL[1]) == null){
                   RequestBuffer.request(() -> {
                       try {
                           event.getMessage().getChannel().sendMessage("You dont have the Item!");
                       } catch (MissingPermissionsException | DiscordException e1) {
                           e1.printStackTrace();
                       }
                   });
               } else if(inv.get(itemL[1]).amount < askA ){
                   RequestBuffer.request(() -> {
                       try {
                           event.getMessage().getChannel().sendMessage("You dont have enough of the Item!");
                       } catch (MissingPermissionsException | DiscordException e1) {
                           e1.printStackTrace();
                       }
                   });
               }else{
                   inv.get(itemL[1]).minusAmount(askA);
                   increaseHealth(30*askA);
                   RequestBuffer.request(() -> {
                       try {
                           event.getMessage().getChannel().sendMessage("You successfully used " + itemL[1] + " " + askA + " times!");
                       } catch (MissingPermissionsException | DiscordException e1) {
                           e1.printStackTrace();
                       }
                   });
               }
            } else{
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("Amount can only consist of Numbers!");
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            }

        }
    }

    private void increaseHealth(double inc){
        hp += inc;
        if(hp > maxHP){
            hp = maxHP;
        }
    }

    private void buyPotion(MessageReceivedEvent eve){
        String[] msg = eve.getMessage().getContent().split("\\s");
        if(msg.length == 3) {
            if (inv.get(0).amount >= 10) {
                if (inv.get("Health Potion") == null) {
                    inv.add(new RedPot(this, itemL[1], 1));
                } else {
                    inv.get("Health Potion").addAmount(1);
                }
                inv.get(0).minusAmount(10);
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("Successfully bought item!");
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            } else {
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("Not enough Gold!");
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            }
        } else {
            //msg[1].matches("\\d+")
            if(msg[3].matches("\\d+")){
                if (inv.get(0).amount >= 10 * Integer.parseInt(msg[3])) {
                    if (inv.get("Health Potion") == null) {
                        inv.add(new RedPot(this, itemL[1], Integer.parseInt(msg[3])));
                    } else {
                        inv.get("Health Potion").addAmount(Integer.parseInt(msg[3]));
                    }
                    inv.get(0).minusAmount(10* Integer.parseInt(msg[3]));
                    RequestBuffer.request(() -> {
                        try {
                            event.getMessage().getChannel().sendMessage("Successfully bought items!");
                        } catch (MissingPermissionsException | DiscordException e1) {
                            e1.printStackTrace();
                        }
                    });
                } else {
                    RequestBuffer.request(() -> {
                        try {
                            event.getMessage().getChannel().sendMessage("Not enough Gold!");
                        } catch (MissingPermissionsException | DiscordException e1) {
                            e1.printStackTrace();
                        }
                    });
                }
            }else{
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("Amount can only consist of Numbers!");
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });
            }
        }
    }

    private void showShop(){
        RequestBuffer.request(()->{
            try {
                Mess = event.getMessage().getChannel().sendMessage("" +
                        "**SHOP** ```Markdown\n" +
                        "#Potions: \n" +
                        "Health Potion           10g\n" +
                        "Rest is coming soon....\n\n" +
                        "#Your Funds:\n" +
                        "Gold: " + inv.get("Gold").amount + "\n" +
                        "```");
            } catch (MissingPermissionsException | DiscordException e1) {
                e1.printStackTrace();
            }
        });
    }

    public boolean checkCooldown(){
        long current = System.currentTimeMillis();
        if (current > cooldown) {
            return true;
        } else if(event.getMessage().getAuthor().getID().equals("192750776005689344")){

        }
        return false;
    }

    public void notCool(MessageReceivedEvent ev){
        event = ev;
        RequestBuffer.request(()->{
            try {
                event.getMessage().getChannel().sendMessage("You are still on cooldown! " + (cooldown-System.currentTimeMillis())/1000 + "s left");
            } catch (MissingPermissionsException | DiscordException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void setCooldown(){
        cooldown = System.currentTimeMillis()+ 10000;
    }

    public void adv(MessageReceivedEvent ev){
        event = ev;
        msg = event.getMessage().toString().split("\\s");
        if(msg.length == 2){
            if(onAdv){
                if(checkCooldown())
                        choose();
                else{
                    notCool(event);
                }
            } else{
                if(checkCooldown()){
                    onAdv = true;
                    idle = false;
                    e = new Enemy(ID, level);
                    found();
                }else{
                    notCool(event);
                }

            }
        } else if(msg.length > 2){
            switch (msg[2].toLowerCase()){
                case"1":
                    if(checkCooldown()){
                        fight(event);
                    } else{
                        notCool(event);
                    }

                    break;
                case"2":
                    if(checkCooldown()) {
                        flee(event);
                    } else{
                        notCool(event);
                    }
                    break;
                default:
                    try {
                        event.getMessage().getChannel().sendMessage("Command Unknown!");
                    }  catch (MissingPermissionsException | RateLimitException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                    break;
            }
        } else {
            try {
                event.getMessage().getChannel().sendMessage("Command Unknown!");
            } catch (MissingPermissionsException | RateLimitException | DiscordException e1) {
                e1.printStackTrace();
            }
        }

    }

    public void fight(MessageReceivedEvent ev){
        event = ev;
        double dmg = e.attack(attackdmg);
        double realDmg = dmg - def;
        if(realDmg <0){
            realDmg = 0;
        }
        hp -= realDmg;
        if(hp <=0){
            alive = false;
            death();
        }
        else if(e.isAlive()){
            choose();
        } else{
            victory();
        }
        setCooldown();

    }

    public void flee(MessageReceivedEvent ev){
        event = ev;
        int rand = RandomNumberGen.getRandint(100);
        if(rand < 65){
            onAdv = false;
            idle = true;
        }else{
            double dmg = e.attack(0);
            double realDmg = dmg - (def/2);
            if(realDmg <0){
                realDmg = 0;
            }
            hp -= realDmg;
            if(hp <=0){
                alive = false;
                death();
            } else{
                choose();
            }

        }
        setCooldown();
    }
    private void victory(){
        //TODO
        onAdv = false;
        idle = true;
        double expGain = expGain();
        inv.get(0).addAmount(e.inv.get(0).amount);
        update();
        RequestBuffer.request(()->{
            try {
                Mess.delete();
                Mess = event.getMessage().getChannel().sendMessage("" +
                        "**VICTORY** ```Markdown\n" +
                        "#Your Stats:\n" +
                        "Level:         " + level + "\n" +
                        "HP:            " + hp + "\n" +
                        "Attack Damage: " + attackdmg+ "\n"+
                        "Defense:       " + def + "\n\n"+
                        "#Loot:\n" +
                        "EXP: "+  expGain + "\n" +
                        "Gold: " + e.inv.get(0).amount + "\n\n" +
                        "#What will you do?\n" +
                        RemListener.getPre()+"ak shop to enter the shop"+"\n" +
                        RemListener.getPre()+"ak adv to adventure again"+"\n" +
                        RemListener.getPre()+"ak inv to view the inventory" + "\n" +
                        "```"
                );
            } catch (MissingPermissionsException | DiscordException e1) {
                e1.printStackTrace();
            }
        });
        setCooldown();
    }

    private double expGain(){
        double EXPGain = e.getLvl() * RandomNumberGen.getRandIntBetween(1,4) + RandomNumberGen.getRandint(3);
        EXP += EXPGain;
        return EXPGain;
    }

    private void death(){
        //TODO
        RequestBuffer.request(()->{
            try {
                event.getMessage().getChannel().sendMessage("**YOU DIED**");
            } catch (MissingPermissionsException | DiscordException e1) {
                e1.printStackTrace();
            }
        });
        reset();
    }

    private void reset(){
        EXP = 1.0;
        level = 1;
        hp = 50;
        maxHP = 50;
        skillHp = 0;
        attackdmg = 10;
        def = 0;
        alive = true;
        onAdv = false;
        idle = true;
        inv = new Inventory();
        inv.add(new Gold(this, itemL[0],50));
    }

    private void found(){
        RequestBuffer.request(()->{
            try {
                Mess = event.getMessage().getChannel().sendMessage("" +
                        "**Enemy Found!** ```Markdown\n" +
                        "#Enemy Stats:\n" +
                        "Level:         " + e.getLvl() + "\n" +
                        "HP:            " + e.getHp() + "\n" +
                        "Attack Damage: " + e.getAttackdmg() + "\n" +
                        "Defense:       " + e.getDef() + "\n\n" +
                        "#Your Stats:\n" +
                        "Level:         " + level + "\n" +
                        "HP:            " + hp + "\n" +
                        "Attack Damage: " + attackdmg+ "\n"+
                        "Defense:       " + def + "\n\n"+
                        "#What will you do?\n" +
                        "1. Fight\n" +
                        "2. Run away\n" +
                        "```"
                );
            } catch (MissingPermissionsException | DiscordException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void choose(){

        RequestBuffer.request(()->{
            try {
                Mess.delete();
                Mess = event.getMessage().getChannel().sendMessage("" +
                        "**Resume Combat** ```Markdown\n" +
                        "#Enemy Stats:\n" +
                        "Level:         " + e.getLvl() + "\n" +
                        "HP:            " + e.getHp() + "\n" +
                        "Attack Damage: " + e.getAttackdmg() + "\n" +
                        "Defense:       " + e.getDef() + "\n\n" +
                        "#Your Stats:\n" +
                        "Level:         " + level + "\n" +
                        "HP:            " + hp + "\n" +
                        "Attack Damage: " + attackdmg+ "\n"+
                        "Defense:       " + def + "\n\n"+
                        "#What will you do?\n" +
                        "1. Fight\n" +
                        "2. Run away\n" +
                        "```"
                );
            } catch (MissingPermissionsException | DiscordException e1) {
                e1.printStackTrace();
            }
        });
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
    public double getEXP(){ return EXP;}
    public boolean getAdv(){ return onAdv;}
    public boolean getIdle(){ return idle;}
    public long getCooldown(){return cooldown;}

}
