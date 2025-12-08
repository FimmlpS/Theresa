package Theresa.action;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class LostHPAction extends AbstractGameAction {
    private static final float DURATION = 0.33F;
    private static boolean UNABLE_DIE = false;

    public LostHPAction(AbstractCreature target, AbstractCreature source, int amount) {
        this(target, source, amount, AttackEffect.NONE);
    }

    public LostHPAction(AbstractCreature target, AbstractCreature source, int amount, AbstractGameAction.AttackEffect effect) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.33F;
    }

    public void update() {
        if (this.duration == 0.33F && this.target.currentHealth > 0) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        }

        this.tickDuration();
        if (this.isDone) {
            UNABLE_DIE = true;
            this.target.damage(new DamageInfo(this.source, this.amount, DamageInfo.DamageType.HP_LOSS));
            UNABLE_DIE = false;
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(0.1F));
            }
        }

    }

    @SpirePatch(clz = AbstractPlayer.class,method = "damage")
    public static class LostHPActionPatch {
        @SpireInsertPatch(rloc = 112)
        public static void Insert(AbstractPlayer _inst, DamageInfo info){
            if(UNABLE_DIE && _inst.currentHealth < 1) {
                _inst.currentHealth = 1;
            }
        }
    }


}
