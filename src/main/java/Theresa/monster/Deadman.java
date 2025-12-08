package Theresa.monster;

import Theresa.action.LongWaitAction;
import Theresa.action.TopTalkAction;
import Theresa.card.status.Decide;
import Theresa.patch.BabelPatch;
import Theresa.power.buff.AssassinPower;
import Theresa.power.buff.PromiseDiePower;
import Theresa.power.buff.StrongStrikePower;
import Theresa.room.DeadBossRoom;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import java.util.Iterator;

public class Deadman extends AbstractMonster {
    public static final String ID = "theresa:Deadman";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String BOSS_ICON = "TheresaResources/img/characters/mmcike/Mmcike_Icon.png";
    public static final String BOSS_ICON_O = "TheresaResources/img/characters/mmcike/Mmcike_Icon_O.png";

    private static final String ATTACK = "Attack";
    private static final String IDLE = "Idle";
    private static final String DIE = "Die";
    public static final String MOVE = "Move";
    private static final String SKILL = "Skill";
    private static final String SKILL2_BEGIN = "Skill2_Begin";
    private static final String SKILL2_END = "Skill2_End";

    public static float[] xList = {-700F,-450F,-200F, 50F, 300F};
    public static float[] yList = {17F,18F,19F,17F,18F};

    int attack = 8;
    boolean upgraded = false;
    boolean changeBGM = false;

    public boolean placed = false;
    public float moveTimer = 3F;
    public float startMoveTimer = 3F;
    public int placeIndex;

    private boolean halfAttack = false;

    @Override
    public void showHealthBar() {
        if(!placed)
            return;
        super.showHealthBar();
    }

    public void initializeSpine(){
        this.loadAnimation("TheresaResources/img/characters/mmcike/enemy_1419_mmcike.atlas","TheresaResources/img/characters/mmcike/enemy_1419_mmcike.json",1.75F);
        this.flipHorizontal = true;
        this.state.setAnimation(0,IDLE,true);
    }

    @Override
    public void update() {
        super.update();
        if(placed)
            return;
        float delta = Gdx.graphics.getDeltaTime();
        moveTimer -= delta;
        if(moveTimer>0){
            animX = Interpolation.fade.apply(0.0F, 250F * (6-placeIndex) * Settings.xScale, moveTimer/startMoveTimer);
        }
        else {
            placed = true;
            animX = 0F;
            this.initializeSpine();
            this.showHealthBar();
            this.createIntent();
            DeadBossRoom d = DeadBossRoom.getCurrent();
            if(d!=null && d.canTalk()){
                CardCrawlGame.music.fadeOutTempBGM();
                BabelPatch.BOSS_KEY = "BOSS2";
                AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
                BabelPatch.BOSS_KEY = "";
                addToBot(new TopTalkAction(this,monsterStrings.DIALOG[1]));
                if(Settings.FAST_MODE){
                    addToBot(new LongWaitAction(1.2F));
                }
                addToBot(new TopTalkAction(this,monsterStrings.DIALOG[2]));
                if(Settings.FAST_MODE){
                    addToBot(new LongWaitAction(1.2F));
                }
                addToBot(new TopTalkAction(this,monsterStrings.DIALOG[3]));
            }
        }
    }

    int realIndex = 0;
    int realStrength = 0;

    public Deadman setRealIndex(int realIndex){
        this.realIndex = realIndex;
        return this;
    }

    public Deadman setStrength(int strength){
        this.realStrength = strength;
        if(realStrength>=9){
            this.setHp(AbstractDungeon.ascensionLevel>=9?224:204);
        }
        return this;
    }

    public Deadman(int index, boolean placed) {
        super(monsterStrings.NAME,ID,124,0F,0F,220F,360F,null,xList[index], yList[index]);
        this.type = EnemyType.BOSS;
        this.placed = placed;
        this.placeIndex = index;
        if(placed){
            changeBGM = true;
            moveTimer = 0F;
        }
        else {
            startMoveTimer = moveTimer = (6-index)*0.6F;
        }
        initializeSpine();
        int extra = 0;
        if(placed)
            extra = 40;
        if(AbstractDungeon.ascensionLevel>=9){
            setHp(144 + extra);
        }
        else {
            setHp(124 + extra);
        }

        if(AbstractDungeon.ascensionLevel>=4){
            attack = 8;
        }
        else {
            attack = 7;
        }

        if(AbstractDungeon.ascensionLevel>=19){
            upgraded = true;
        }

        this.damage.add(new DamageInfo(this,attack, DamageInfo.DamageType.NORMAL));
    }

    @Override
    public void usePreBattleAction() {
        if(changeBGM){
            CardCrawlGame.music.unsilenceBGM();
            AbstractDungeon.scene.fadeOutAmbiance();
            AbstractDungeon.getCurrRoom().cannotLose = true;
            BabelPatch.BOSS_KEY = "BOSS1";
            AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
            BabelPatch.BOSS_KEY = "";
        }

        //power
        addToBot(new ApplyPowerAction(this,this,new PromiseDiePower(this,upgraded)));
        addToBot(new ApplyPowerAction(this,this,new AssassinPower(this)));
        int strength = realStrength;
        if(strength>0){
            addToBot(new ApplyPowerAction(this,this,new StrengthPower(this,strength)));
        }
        switch (realIndex) {
            case 2:
                addToBot(new ApplyPowerAction(this, this, new FlameBarrierPower(this, 4), 4));
                break;
            case 3:
                addToBot(new ApplyPowerAction(this, this, new ArtifactPower(this, 5), 5));
                break;
            case 5:
                addToBot(new ApplyPowerAction(this, this, new SporeCloudPower(this, 2), 2));
                break;
            case 6:
                addToBot(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, 8), 8));
                break;
            case 7:
                addToBot(new ApplyPowerAction(this, this, new PainfulStabsPower(this)));
                break;
            case 9:
                addToBot(new ApplyPowerAction(this, this, new AngerPower(this, 2), 2));
                addToBot(new ApplyPowerAction(this,this, new StrongStrikePower(this)));
                break;
            case 10:
                addToBot(new ApplyPowerAction(this, this, new BarricadePower(this)));
                break;
            case 11:
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new EntanglePower(AbstractDungeon.player)));
                addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new StrengthPower(AbstractDungeon.player,-3),-3));
                addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new DexterityPower(AbstractDungeon.player,-3),-3));
                for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                    if(!m.isDeadOrEscaped()){
                        addToBot(new ApplyPowerAction(m,this,new MetallicizePower(m,6),6));
                        addToBot(new ApplyPowerAction(m,this,new PlatedArmorPower(m,6),6));
                        addToBot(new ApplyPowerAction(m,this,new BufferPower(m,3),3));
                    }
                }
                addToBot(new ApplyPowerAction(this,this,new ArtifactPower(this,99),99));
                break;
        }

        super.usePreBattleAction();

        if(changeBGM){
            addToBot(new LongWaitAction(0.4F));
            addToBot(new TopTalkAction(this,monsterStrings.DIALOG[0]));
        }
    }

    @Override
    protected void getMove(int i) {
        if(realIndex!=4 && realIndex!=8)
            this.setMove((byte) 0,Intent.ATTACK_BUFF,damage.get(0).base);
        else {
            this.setMove((byte) 0,Intent.ATTACK_DEBUFF,damage.get(0).base);
        }
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 0:
                boolean gainStrength = false;
                if (hasPower(StrongStrikePower.POWER_ID)) {
                    addToBot(new ChangeStateAction(this, SKILL));
                    addToBot(new LongWaitAction(1.0F));
                    addToBot(new DamageAction(AbstractDungeon.player, damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                    gainStrength = true;
                } else {
                    AbstractPower s = getPower(StrengthPower.POWER_ID);
                    if (!halfAttack && currentHealth*2<maxHealth && (s != null && s.amount >= 10)) {
                        halfAttack = true;
                        addToBot(new ChangeStateAction(this, SKILL2_BEGIN));
                        addToBot(new LongWaitAction(0.1F));
                        addToBot(new DamageAction(AbstractDungeon.player, damage.get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                        addToBot(new MakeTempCardInDrawPileAction(new Decide(),1,true,true));
                    } else {
                        addToBot(new ChangeStateAction(this, ATTACK));
                        addToBot(new LongWaitAction(0.4F));
                        addToBot(new DamageAction(AbstractDungeon.player, damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                        gainStrength = true;
                    }
                }
                if (gainStrength) {
                    if (realIndex == 12) {
                        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                            if (!m.isDeadOrEscaped()) {
                                addToBot(new ApplyPowerAction(m, this, new StrengthPower(m, 4), 4));
                            }
                        }
                    }
                    else if(realIndex==10){
                        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                            if (!m.isDeadOrEscaped()) {
                                addToBot(new GainBlockAction(m,this,12));
                            }
                        }
                    }
                    else if (realIndex == 8) {
                        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 1, true), 1));
                    } else if (realIndex == 4) {
                        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
                    } else if (realIndex == 1) {
                        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));
                    } else {
                        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
                    }
                }

                break;
        }
        addToBot(new RollMoveAction(this));
    }

    @Override
    public void changeState(String stateName) {
        switch (stateName) {
            case ATTACK:
                this.state.setAnimation(0, ATTACK, false);
                this.state.addAnimation(0, IDLE, true, 0F);
                break;
            case SKILL:
                this.state.setAnimation(0, SKILL, false);
                this.state.addAnimation(0, IDLE, true, 0F);
                break;
            case SKILL2_BEGIN:
                this.state.setAnimation(0, SKILL2_BEGIN, false);
                this.state.addAnimation(0, SKILL2_END, false, 0F);
                this.state.addAnimation(0, IDLE, true, 0F);
                break;
        }
    }

    @Override
    public void die() {
        this.state.setTimeScale(1F);
        this.state.setAnimation(0, DIE, false);
        super.die();
        if(DeadBossRoom.getCurrent()!=null){
            boolean zero = DeadBossRoom.getCurrent().dieOne();
            if(zero){
                for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                    if(m instanceof Empty){
                        ((Empty) m).setCanDie();
                        m.die();
                    }
                }
                this.useFastShakeAnimation(5.0F);
                CardCrawlGame.screenShake.rumble(4.0F);
                (AbstractDungeon.getCurrRoom()).rewardAllowed = false;
                this.onBossVictoryLogic();
                CardCrawlGame.stopClock = true;
                BabelPatch.KilledDead = true;
                AbstractDungeon.getCurrRoom().cannotLose = false;
            }
        }
    }
}
