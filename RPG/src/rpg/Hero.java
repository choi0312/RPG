package rpg;

// 히어로 클래스: 게임 내 주인공의 상태(이름, 레벨, 스탯, 경험치, 골드 등)를 관리하는 클래스

class Hero {
    // 정적 변수: 모든 인스턴스가 공유하는 값으로, 본 예제에서는 단 한 명의 영웅만 존재한다고 가정
    private static String hero_name;       // 영웅의 이름
    private static int hero_level;           // 영웅의 레벨
    private static int hero_power;           // 영웅의 힘 (공격력에 영향을 줌)
    private static int hero_hp;              // 영웅의 체력 (HP)
    private static int hero_defense;         // 영웅의 방어력 (몬스터의 공격을 방어하는 역할)
    private static int hero_mp;              // 영웅의 마나
    private static int hero_experience;      // 영웅의 경험치 (레벨업을 위해 누적)
    private static int hero_money;           // 영웅의 소지 골드 (아이템 구매, 보상 등에 사용)

    // 생성자: 영웅의 이름을 받아 초기 스탯을 설정합니다.
    public Hero(String hero_name) {
        this.hero_name = hero_name;         // 입력받은 이름으로 영웅 이름 설정
        hero_level = 1;                     // 초기 레벨은 1
        hero_power = 15;                    // 초기 힘은 15
        hero_defense = 25;                  // 초기 방어력은 25
        hero_hp = 80;                       // 초기 체력은 80
        hero_mp = 0;                        // 초기 MP는 0 (필요시 확장 가능)
        hero_experience = 0;                // 초기 경험치는 0
        hero_money = 0;                     // 초기 골드는 0
    }

    // Getter 메서드들: 외부에서 영웅의 각 스탯 값을 읽을 수 있게 함
    public String getHero_name() {
        return hero_name;
    }

    public int getHero_level() {
        return hero_level;
    }

    public int getHero_power() {
        return hero_power;
    }

    public int getHero_hp() {
        return hero_hp;
    }

    public int getHero_defense() {
        return hero_defense;
    }

    public int getHero_mp() {
        return hero_mp;
    }

    public int getHero_experience() {
        return hero_experience;
    }

    public int getHero_money() {
        return hero_money;
    }

    // Setter 메서드들: 외부에서 영웅의 각 스탯 값을 수정할 수 있도록 함
    public void setHero_name(String hero_name) {
        this.hero_name = hero_name;
    }

    public void setHero_level(int hero_level) {
        this.hero_level = hero_level;
    }

    public void setHero_power(int hero_power) {
        this.hero_power = hero_power;
    }

    public void setHero_hp(int hero_hp) {
        this.hero_hp = hero_hp;
    }

    public void setHero_defense(int hero_defense) {
        this.hero_defense = hero_defense;
    }

    public void setHero_mp(int hero_mp) {
        this.hero_mp = hero_mp;
    }

    public void setHero_experience(int hero_experience) {
        this.hero_experience = hero_experience;
    }

    public void setHero_money(int hero_money) {
        this.hero_money = hero_money;
    }

    // [히어로 공격] 메서드: 영웅의 공격력을 계산하여 몬스터에게 데미지 값을 전달한다
    // 계산식: (영웅 레벨 * 10) + (영웅 힘 * 30)
    static int hero_attack() {
        int sum = (hero_level * 10) + (hero_power * 30);
        System.out.println(hero_name + "의 공격입니다.");
        // 계산된 공격 데미지를 반환하여 몬스터의 체력 감소 등에 활용합니다.
        return sum;
    }

    // [히어로 공격받기] 메서드: 몬스터의 공격 데미지(sum)를 입력받아 영웅의 HP를 갱신
    // - 만약 영웅의 방어력이 공격 데미지 이상이면 데미지를 무시
    // - 그렇지 않으면, 영웅 HP를 (영웅 HP + 영웅 방어력 - 공격 데미지) 만큼 감소시킴
    // - HP가 0 이하가 되면, 영웅은 "부활"하여 HP가 1로 재설정
    static void hero_attacked(int sum) {
        System.out.println(hero_name + "이(가) 공격을 당했습니다!");
        if (hero_defense >= sum) {
            System.out.println(hero_name + "의 방어력이 높아 데미지를 받지 않았습니다!");
        } else {
            hero_hp = hero_hp + hero_defense - sum;
            System.out.println("실제 데미지를 입어 HP가 " + hero_hp + " 로 감소했습니다.");
        }
        if (hero_hp <= 0) {
            // 부활 처리: HP를 1로 설정 후 부활 메시지 출력
            hero_hp = 1;
            System.out.println(hero_name + "이(가) 쓰러졌으나... 기적적으로 부활했습니다! (HP=1)");
        }
    }

    // 상점 구매 이력을 확인하기 위한 변수 (기본값은 false: 아직 상점에서 구매하지 않음)
    private static boolean hasBoughtItem = false;

    // hasBoughtItem의 Getter, Setter 추가
    public static boolean getHasBoughtItem() {
        return hasBoughtItem;
    }

    public static void setHasBoughtItem(boolean purchased) {
        hasBoughtItem = purchased;
    }



    // 몬스터를 처치했을 때, 보상(경험치, 골드)를 지급하고 레벨업 여부를 확인
    public void gainReward(int monsterExp, int monsterMoney) {
        System.out.println("몬스터를 처치하여 경험치 " + monsterExp + ", 돈 " + monsterMoney + "원을 획득했습니다!");
        hero_experience += monsterExp;   // 획득한 경험치 추가
        hero_money += monsterMoney;        // 획득한 골드 추가

        checkLevelUp(); // 전투 후 레벨업 체크: 경험치 기준에 따라 레벨 증가 여부를 판단함
    }

    // [레벨업 프로세스]
    // 조건: 영웅 경험치가 (영웅 레벨 * 80) 이상이면 레벨업 수행
    // - 레벨업 시, 영웅의 레벨을 증가시키고, 레벨업 보상으로 골드, 힘, HP를 증가시킴
    // - 이후 경험치는 0으로 초기화됨
    public void checkLevelUp() {
        if (hero_experience >= (hero_level * 80)) {
            hero_level++;  // 레벨 1 증가
            System.out.println(hero_name + "의 레벨이 " + hero_level + "이 되었습니다!");
            int addMoney = hero_level * 50;  // 레벨업 보상 골드 계산
            hero_money += addMoney;
            System.out.println("레벨업 기념으로 돈이 " + addMoney + "원 증가하여 " + hero_money + "원이 되었습니다.");
            // 추가 보상: 영웅의 힘과 HP도 증가 (힘 +5, HP +100)
            hero_power += 5;
            hero_hp += 100;
            System.out.println("또한, 힘이 5 증가하고 HP가 100 증가했습니다.");
            hero_experience = 0;  // 경험치 초기화 (다음 레벨업을 위해)
        }
    }

    // 영웅 정보 출력 메서드: 현재 영웅의 모든 스탯(이름, 레벨, 힘, 방어력, HP, 경험치, 돈)을 문자열로 반환
    public String printHeroInfo() {
        return "현재 Hero 의 이름 : " + hero_name + '\n' +
                "현재 " + hero_name + " 의 레벨 : " + hero_level + '\n' +
                "현재 " + hero_name + "의 힘 : " + hero_power + '\n' +
                "현재 " + hero_name + "의 방어력 : " + hero_defense + '\n' +
                "현재 " + hero_name + "의 HP : " + hero_hp + '\n' +
                "현재 " + hero_name + "의 경험치 : " + hero_experience + '\n' +
                "현재 " + hero_name + "의 돈 : " + hero_money + '\n';
    }
}
