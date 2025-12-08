package Theresa.relic;

import Theresa.helper.StringHelper;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.lang.reflect.Type;

public class KnownRelic extends CustomRelic implements CustomSavable<Boolean> {
    public static final String ID = "theresa:KnownRelic";
    boolean addedHP = false;
    boolean respawned = false;

    public KnownRelic() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(UnknownRelic.ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(UnknownRelic.ID,true)),RelicTier.BOSS,LandingSound.SOLID);
        this.counter = 0;
    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        this.description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public void obtain() {
        updateDescription(AbstractDungeon.player.chosenClass);
        if(AbstractDungeon.player.hasRelic(UnknownRelic.ID)){
            for(int i =0;i<AbstractDungeon.player.relics.size();i++){
                if((AbstractDungeon.player.relics.get(i)).relicId.equals(UnknownRelic.ID)){
                    this.counter = AbstractDungeon.player.relics.get(i).counter;
                    this.instantObtain(AbstractDungeon.player,i,true);
                    break;
                }
            }
        }
        else{
            this.counter = 0;
            super.obtain();
        }
    }

    @Override
    public void atBattleStartPreDraw() {
        if(this.counter>=2000){
            this.flash();
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new RelicAboveCreatureAction(p, this));
            addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,1),1));
            addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,1),1));
            if(this.counter>=4000){
                addToBot(new GainEnergyAction(1));
                addToBot(new DrawCardAction(1));
            }
            if(this.counter>=6000){
                AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER).makeCopy();
                if (c.cost >= 0) {
                    c.updateCost(-99);
                }
                UnlockTracker.markCardAsSeen(c.cardID);
                this.addToBot(new MakeTempCardInHandAction(c));
            }
            if(this.counter>=8000){
                addedHP = true;
                p.increaseMaxHp(5,true);
            }
        }
    }

    @Override
    public void onVictory() {
        if(addedHP){
            addedHP = false;
            AbstractDungeon.player.decreaseMaxHealth(5);
        }
    }

    @SpirePatch(clz = GameActionManager.class,method = "addToBottom")
    @SpirePatch(clz = GameActionManager.class,method = "addToTop")
    public static class AddToBottomPatch {
        public static void Postfix(GameActionManager _inst, AbstractGameAction action) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                AbstractRelic relic = AbstractDungeon.player.getRelic(ID);
                if (relic != null) {
                    relic.counter++;
                }
            }
        }
    }

    //小patch一下
    @SpirePatch(clz = AbstractPlayer.class,method = "damage")
    public static class DamagePatch {
        @SpireInsertPatch(rloc = 128)
        public static SpireReturn<Void> Insert(AbstractPlayer _inst, DamageInfo info) {

            AbstractRelic hj = AbstractDungeon.player.getRelic(ID);
            if (hj instanceof KnownRelic) {
                if (hj.counter >= 10000 && !((KnownRelic) hj).respawned) {
                    ((KnownRelic) hj).respawned = true;
                    _inst.currentHealth = 0;
                    _inst.heal(_inst.maxHealth);
                    hj.flash();
                    hj.updateDescription(AbstractDungeon.player.chosenClass);
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[respawned?1:0];
    }

    @Override
    public Boolean onSave() {
        return respawned;
    }

    @Override
    public void onLoad(Boolean aBoolean) {
        this.respawned = aBoolean;
        this.description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public Type savedType() {
        return Boolean.TYPE;
    }
}


