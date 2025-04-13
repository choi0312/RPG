package rpg;

import java.util.Scanner;
import java.util.Random;

// 실제 게임 진행(메인) 클래스
public class Game {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1) 용사 이름 입력
        System.out.print("영웅의 이름을 입력하세요: ");
        String heroName = sc.nextLine();

        // 2) 히어로 생성
        Hero hero = new Hero(heroName);
        System.out.println("이름이 입력되었습니다");
        System.out.println("게임에 입장하였습니다\n");

        // 히어로 초기 상태 출력
        System.out.println("**********************");
        System.out.println(hero.printHeroInfo());

        // -----------------------------
        //    메인 게임 루프
        // -----------------------------
        while (true) {
            System.out.println("========== 메인 메뉴 ==========");
            System.out.println("1. 전투하기 (사냥터)");
            System.out.println("2. 포션 상점");
            System.out.println("3. 게임 종료");
            System.out.print("메뉴 선택: ");
            int menuChoice = sc.nextInt();

            if (menuChoice == 1) {
                // 전투하기 선택 시, 먼저 랜덤 이벤트 발생!
                randomEvent(hero);

                // 한 번 전투(사냥)를 진행
                Monster monster = chooseMonster(hero);
                if (monster != null) {
                    System.out.println("\n===== 전투를 시작합니다! =====");
                    fight(hero, monster);

                    // 전투 후 히어로 상태 출력
                    System.out.println("--- 전투 결과 ---");
                    System.out.println(hero.printHeroInfo());

                    // 전투가 끝나면 "사냥이 종료" 메뉴를 표시
                    while (true) {
                        System.out.println("사냥이 종료되었습니다. 무엇을 하시겠습니까?");
                        System.out.println("1. 다시 사냥하기");
                        System.out.println("2. 포션 상점 가기");
                        System.out.println("3. 메인 메뉴로 돌아가기");
                        System.out.print("선택: ");
                        int postFightChoice = sc.nextInt();

                        if (postFightChoice == 1) {
                            // 다시 몬스터 선택 → 전투 진행
                            Monster nextMonster = chooseMonster(hero);
                            if (nextMonster != null) {
                                System.out.println("\n===== 전투를 시작합니다! =====");
                                fight(hero, nextMonster);
                                System.out.println("--- 전투 결과 ---");
                                System.out.println(hero.printHeroInfo());
                            } else {
                                System.out.println("(전투 불가 / 선택 취소)\n");
                            }
                            // 전투 종료 후 이 while문 계속 → 또 1/2/3 물어봄
                        }
                        else if (postFightChoice == 2) {
                            // 상점 이용
                            potionStore(hero);
                        }
                        else if (postFightChoice == 3) {
                            // 메인 메뉴로 돌아감 → break
                            break;
                        }
                        else {
                            System.out.println("잘못된 입력입니다. 다시 선택해주세요.\n");
                        }
                    }
                } else {
                    System.out.println("(전투를 진행할 수 없습니다: 잘못된 선택 / 전투 취소 / 조건 불충족)\n");
                }
            }
            else if (menuChoice == 2) {
                // (B) 포션 상점
                potionStore(hero);
            }
            else if (menuChoice == 3) {
                // (C) 종료
                System.out.println("게임을 종료합니다.");
                break;
            }
            else {
                System.out.println("잘못된 입력입니다. 다시 선택해주세요.\n");
            }

            // 메인 메뉴 루프 끝날 때마다 히어로 상태 표시
            System.out.println("\n--- 현재 히어로 상태 ---");
            System.out.println(hero.printHeroInfo());
        }

        System.out.println("**********************");
        System.out.println("최종 히어로 상태");
        System.out.println(hero.printHeroInfo());
        sc.close();
    }

    // 랜덤 이벤트 메서드 (새로운 추가 기능)
    // 전투 전에 30% 확률로 보물상자(골드 보상), 20% 확률로 함정(HP 감소), 그 외 별다른 이벤트 없음
    public static void randomEvent(Hero hero) {
        Random rand = new Random();
        int chance = rand.nextInt(100); // 0~99 사이의 정수

        if(chance < 30) {  // 40% 확률: 보물상자 발견
            System.out.println("\n[랜덤 이벤트 발생]");
            System.out.println("우연히 보물상자를 발견하였습니다! 50골드를 획득합니다.");
            hero.setHero_money(hero.getHero_money() + 50);
            System.out.println(hero.printHeroInfo());
        } else if(chance < 50) {  // 20% 확률: 함정에 빠짐
            System.out.println("\n[랜덤 이벤트 발생]");
            System.out.println("함정에 빠졌습니다! HP가 20 감소합니다.");
            hero.setHero_hp(hero.getHero_hp() - 20);
            if(hero.getHero_hp() <= 0) {
                hero.setHero_hp(1);
                System.out.println(hero.getHero_name() + "이(가) 쓰러졌으나 부활했습니다! (HP=1)");
            }
            System.out.println(hero.printHeroInfo());
        }
        System.out.println();
    }


    // 사냥터에 입장해 몬스터 선택
    public static Monster chooseMonster(Hero hero) {
        Scanner sc = new Scanner(System.in);

        System.out.println("사냥터에 입장하였습니다.");
        System.out.println("1. 너구리 (레벨1, HP=100, 공격력=20)");
        System.out.println("2. 살쾡이 (레벨5, HP=2000, 공격력=100)");
        System.out.print("전투할 상대를 입력하세요 (숫자): ");
        int choose = sc.nextInt();

        if (choose == 1) {
            // 너구리
            return new Monster("너구리");
        }
        else if (choose == 2) {
            // 살쾡이 전투 조건: 세 조건(레벨>=5, HP>=200, 힘>=30) 중 하나만 만족하고,
            // 추가로 상점에서 한 번이라도 구매한 기록이 있어야만 전투 가능
            if ((hero.getHero_level() >= 5 || hero.getHero_hp() >= 200 || hero.getHero_power() >= 30)
                    && Hero.getHasBoughtItem()) {
                return new Monster("살쾡이");
            } else {
                System.out.println("\n현재 스펙(레벨, HP, 힘) 중 하나만 충족해도 되지만, 상점에서 아이템을 한 번이라도 구매해야 살쾡이와 전투할 수 있습니다!");
                return null;
            }
        }
        else {
            System.out.println("존재하지 않는 몬스터를 선택하셨습니다.");
            return null;
        }
    }

    // 전투 진행 로직
    public static void fight(Hero hero, Monster monster) {
        while (true) {
            // [1] 히어로 공격
            int heroDamage = Hero.hero_attack();
            monster.monster_attacked(heroDamage);

            if (monster.isMonsterDead()) {
                hero.gainReward(monster.getMonster_experience(), monster.getMonster_money());
                System.out.println("\n" + monster.getMonster_name() + " 처치 완료!");
                break;
            }

            System.out.println();

            // [2] 몬스터 공격
            int monsterDamage = monster.monster_attack();
            Hero.hero_attacked(monsterDamage);

            // 히어로가 쓰러져도 HP=1로 부활 → 전투 계속
            System.out.println("------------------------------------");
        }

        System.out.println("===== 전투가 종료되었습니다. =====\n");
    }

    // [포션 상점]
    // 사진 속 구성에 맞춰 수정
    // 1. 힘 증가 포션 (30원) - [힘 +3]
    // 2. 방어력 증가 포션 (30원) - [방어력 +3]
    // 3. 경험치 증가 포션 (100원) - [경험치 +50] (구매 후 checkLevelUp() 호출)
    // 4. HP 증가 포션 (50원) - [HP +50]
    // 5. MP 증가 포션 (50원) - [MP +50]
    // 6. 상점 나가기
    public static void potionStore(Hero hero) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("===== 포션 상점 =====");
            System.out.println("현재 소지금: " + hero.getHero_money() + "원");
            System.out.println("1. 힘 증가 포션 (30원) - [힘 +3]");
            System.out.println("2. 방어력 증가 포션 (30원) - [방어력 +3]");
            System.out.println("3. 경험치 증가 포션 (100원) - [경험치 +50]");
            System.out.println("4. HP 증가 포션 (50원) - [HP +50]");
            System.out.println("5. MP 증가 포션 (50원) - [MP +50]");
            System.out.println("6. 상점 나가기");
            System.out.print("원하는 물건을 입력하세요: ");
            int choice = sc.nextInt();

            if (choice == 1) {
                // 힘 증가 포션 (30원)
                if (hero.getHero_money() >= 30) {
                    hero.setHero_money(hero.getHero_money() - 30);
                    hero.setHero_power(hero.getHero_power() + 3);
                    System.out.println("[구매 성공] 힘이 3 증가했습니다!");
                    // 구매 성공 시 상점 구매 기록 업데이트
                    Hero.setHasBoughtItem(true);
                    System.out.println("업데이트된 영웅 정보:");
                    System.out.println(hero.printHeroInfo());
                } else {
                    System.out.println("[실패] 골드가 부족합니다.");
                }
            }

            else if (choice == 2) {
                // 방어력 증가 포션 (30원)
                if (hero.getHero_money() >= 30) {
                    hero.setHero_money(hero.getHero_money() - 30);
                    hero.setHero_defense(hero.getHero_defense() + 3);
                    System.out.println("[구매 성공] 방어력이 3 증가했습니다!");
                    System.out.println("업데이트된 영웅 정보:");
                    System.out.println(hero.printHeroInfo());
                } else {
                    System.out.println("[실패] 골드가 부족합니다.");
                }
            }
            else if (choice == 3) {
                // 경험치 증가 포션 (100원)
                if (hero.getHero_money() >= 100) {
                    hero.setHero_money(hero.getHero_money() - 100);
                    hero.setHero_experience(hero.getHero_experience() + 50);
                    System.out.println("[구매 성공] 경험치가 50 증가했습니다!");
                    hero.checkLevelUp();
                    System.out.println("업데이트된 영웅 정보:");
                    System.out.println(hero.printHeroInfo());
                } else {
                    System.out.println("[실패] 골드가 부족합니다.");
                }
            }
            else if (choice == 4) {
                // HP 증가 포션 (50원)
                if (hero.getHero_money() >= 50) {
                    hero.setHero_money(hero.getHero_money() - 50);
                    hero.setHero_hp(hero.getHero_hp() + 50);
                    // 구매 성공 시 상점 구매 기록 업데이트
                    Hero.setHasBoughtItem(true);
                    System.out.println("[구매 성공] HP가 50 증가했습니다!");
                    System.out.println("업데이트된 영웅 정보:");
                    System.out.println(hero.printHeroInfo());
                } else {
                    System.out.println("[실패] 골드가 부족합니다.");
                }
            }
            else if (choice == 5) {
                // MP 증가 포션 (50원)
                if (hero.getHero_money() >= 50) {
                    hero.setHero_money(hero.getHero_money() - 50);
                    hero.setHero_mp(hero.getHero_mp() + 50);
                    System.out.println("[구매 성공] MP가 50 증가했습니다!");
                    System.out.println("업데이트된 영웅 정보:");
                    System.out.println(hero.printHeroInfo());
                } else {
                    System.out.println("[실패] 골드가 부족합니다.");
                }
            }
            else if (choice == 6) {
                // 상점 나가기
                System.out.println("상점을 나갑니다.\n");
                break;
            }
            else {
                System.out.println("잘못된 선택입니다. 다시 입력해주세요.");
            }
        }
    }

}
