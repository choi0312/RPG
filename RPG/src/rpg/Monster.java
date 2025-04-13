package rpg;

import java.util.Objects;

// 몬스터 클래스: 게임 내 적 캐릭터(너구리, 살쾡이 등)를 나타내며, 각 몬스터는 고유의 스탯(레벨, 공격력, HP, 방어력, 경험치 보상, 골드 보상 등)을 가진다
class Monster {
    // 멤버 변수: 몬스터의 이름, 레벨, 공격력, 체력(HP), 방어력, MP(마력), 처치시 지급될 경험치 및 골드
    private String monster_name;
    private int monster_level;
    private int monster_power;
    private int monster_hp;
    private int monster_defense;
    private int monster_mp;
    private int monster_experience;
    private int monster_money;

    // 생성자: 몬스터 이름을 받아, 그에 따른 스탯을 초기화
    // 만약 입력된 이름이 "너구리"이면, 레벨1, 공격력20, 방어력5, HP100, 경험치10, 골드10으로 설정
    // "살쾡이"인 경우, 레벨5, 공격력100, 방어력20, HP2000, 경험치50, 골드30으로 설정
    public Monster(String monster_name) {

        if (Objects.equals(monster_name, "너구리")) {
            this.monster_name = monster_name;
            monster_level = 1;
            monster_power = 20;
            monster_defense = 5;
            monster_hp = 100;
            monster_mp = 0;
            monster_experience = 10;
            monster_money = 10;
        } else if (Objects.equals(monster_name, "살쾡이")) {
            this.monster_name = monster_name;
            monster_level = 5;
            monster_power = 100;
            monster_defense = 20;
            monster_hp = 2000;
            monster_mp = 0;
            monster_experience = 50;
            monster_money = 30;
        }
    }

    // Getter 및 Setter 메서드
    // 각 메서드는 외부에서 몬스터의 스탯 값을 읽거나 수정할 수 있도록 함
    public String getMonster_name() {
        return monster_name;
    }

    public void setMonster_name(String monster_name) {
        this.monster_name = monster_name;
    }

    public int getMonster_level() {
        return monster_level;
    }

    public void setMonster_level(int monster_level) {
        this.monster_level = monster_level;
    }

    public int getMonster_power() {
        return monster_power;
    }

    public void setMonster_power(int monster_power) {
        this.monster_power = monster_power;
    }

    public int getMonster_hp() {
        return monster_hp;
    }

    public void setMonster_hp(int monster_hp) {
        this.monster_hp = monster_hp;
    }

    public int getMonster_defense() {
        return monster_defense;
    }

    public void setMonster_defense(int monster_defense) {
        this.monster_defense = monster_defense;
    }

    public int getMonster_mp() {
        return monster_mp;
    }

    public void setMonster_mp(int monster_mp) {
        this.monster_mp = monster_mp;
    }

    public int getMonster_experience() {
        return monster_experience;
    }

    public void setMonster_experience(int monster_experience) {
        this.monster_experience = monster_experience;
    }

    public int getMonster_money() {
        return monster_money;
    }

    public void setMonster_money(int monster_money) {
        this.monster_money = monster_money;
    }

    // [몬스터 공격받기] 메서드:
    // 입력된 데미지(sum)를 받아, 몬스터의 방어력과 비교
    // - 만약 몬스터의 방어력이 데미지보다 높으면 "방어력이 높아 데미지를 받지 않았다"는 메시지를 출력
    // - 그렇지 않으면 몬스터의 체력을 (현재 체력 + 방어력 - 데미지)로 갱신하며, 데미지 값을 출력
    public void monster_attacked(int sum) {
        if (monster_defense >= sum) {
            System.out.println(monster_name + "의 방어력이 높아 데미지를 받지 않았습니다!");
        } else {
            // 몬스터 체력 갱신: 일부 공격 데미지를 방어력만큼 상쇄시칸다.
            monster_hp = monster_hp + monster_defense - sum;
            System.out.println(monster_name + "의 데미지는 " + sum + "입니다.");
        }
    }

    // [몬스터 공격] 메서드:
    // 몬스터의 기본 공격력을 출력하고, 해당 공격력을 리턴한다.
    public int monster_attack() {
        System.out.println(monster_name + "이(가) 공격합니다! 공격력: " + monster_power);
        return monster_power;
    }

    // [몬스터 사망 여부] 메서드:
    // 몬스터의 체력이 0 이하이면 true를 리턴하여 몬스터가 죽었음을 판단한다.
    public boolean isMonsterDead() {
        return (monster_hp <= 0);
    }
}
