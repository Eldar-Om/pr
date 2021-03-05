public class Battle<interruptedExeption extends Throwable> {
   public void fight(Hero gamer, Hero monster, Realm.FightCallback fightCallback) {
       Runnable runnable =()-> {
           int turn = 1;
           boolean isFightEnded = false;
           while (!isFightEnded) {
               System.out.println("----Ход:"+turn+"----");
               if(turn++ %2 !=0) {
                   isFightEnded = makeHit(monster, gamer, fightCallback);
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }else {
                   isFightEnded= makeHit(gamer, monster,fightCallback);
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           }
       };
       Thread thread = new Thread(runnable);
       thread.start();
   }
   private boolean makeHit(Hero defender, Hero attacker,Realm.FightCallback fightCallback) {
       int hit = attacker.attack();
       int defenderHealht = defender.getHealth()-hit;
       if(hit !=0) {
           System.out.println(String.format("%s Нанес удар в %d единиц!", attacker.getName(),hit));
           System.out.println(String.format("У %s осталось %d единиц здоровья...", defender.getName(), defenderHealht));
       } else {
           System.out.println(String.format("%s Промахнулся!", attacker.getName()));
       }
       if(defenderHealht <= 0 && defender instanceof Gamer) {
           System.out.println("Извините вы пали в бою...");
           fightCallback.fightLost();
           return true;
       } else if (defenderHealht <=0) {
           System.out.println(String.format("Враг повержен! Вы получаете %d опыт и %d золота", defender.getXp(), defender.getGold() ));
           attacker.setXp(attacker.getXp() + defender.getXp());
           attacker.setGold(attacker.getGold() + defender.getGold());
           fightCallback.fightWin();
           return true;
       }else {
           defender.setHealth(defenderHealht);
           return false;
       }
   }
}
