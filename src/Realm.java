import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Realm<string> {
    private static BufferedReader br;
    private static Hero player = null;
    private static Battle battle = null;

    public static void main(String[] args) {
        br = new BufferedReader(new InputStreamReader(System.in));
        battle = new Battle();
        System.out.println("Введите имя персонажа");
        try {
            command(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void command(String string) throws IOException {
        if (player == null) {
            player = new Gamer(
                    string,
                    100,
                    20,
                    20,
                    0,
                    0
            );
            System.out.println(String.format("Спасти наш мир от злодеев вызвался %s!", player.getName()));
            printNavigation();
        }

        switch (string) {
            case "1": {
                System.out.println("Торговец еще не вышел на работу");
                command(br.readLine());
            }
            break;
            case "2": {
                commitFight();
            }
            break;
            case "3":
                System.exit(1);
                break;
            case "да":
                command("2");
                break;
            case "нет": {
                printNavigation();
                command(br.readLine());
            }
        }

        command(br.readLine());
    }


        private static void printNavigation () {
            System.out.println("Куда вы хотите пойти");
            System.out.println("1.К торгову зельем");
            System.out.println("2.В темный лес");
            System.out.println("3.Выход");
        }


    private static void commitFight() {
        battle.fight(player, createMonster(), new FightCallback() {

            @Override
            public void fightWin() {
                System.out.println(String.format("%s Победил! Теперь у вас %d опыт и %d Золото, а так же осталось %d единиц здоровья", player.getName(), player.getXp(), player.getGold(), player.getHealth()));
                System.out.println("Жулаете продолжить поход или вернуться в город? (да/нет)");
                try {
                    command(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fightLost() {

            }
        });
    }



    private static Hero createMonster() {
        int random = (int) (Math.random()*10);
        if(random %2 == 0) return new Goblin(
                "Гоблин",
                50,
                10,
                10,
                100,
                20
        );
        else return new Skeleton(
                "Скелет",
                25,
                20,
                20,
                100,
                10
        );
    }

    interface FightCallback {
    void fightWin();
    void fightLost();
    }
}