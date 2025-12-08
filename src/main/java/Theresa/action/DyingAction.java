package Theresa.action;

import Theresa.effect.DyingFlashEffect;
import Theresa.power.debuff.DyingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.OmegaFlashEffect;

public class DyingAction extends AbstractGameAction {
    public DyingAction(DyingPower power) {
        this.p = power;
    }

    DyingPower p;

    public void update() {
        AbstractDungeon.topLevelEffectsQueue.add(new DyingFlashEffect(p.owner.hb.cX, p.owner.hb.cY));
        p.waitDyingActionDone = false;
        p.owner.currentHealth = 0;
        p.owner.healthBarUpdatedEvent();
        p.owner.damage(new DamageInfo((AbstractCreature)null, 0, DamageInfo.DamageType.HP_LOSS));
        this.isDone = true;
    }
}

