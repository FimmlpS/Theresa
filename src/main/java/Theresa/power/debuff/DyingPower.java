package Theresa.power.debuff;

import Theresa.action.DyingAction;
import Theresa.power.AbstractTheresaPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DyingPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:DyingPower";
    private int lastAmt;
    private int lastHp;
    public boolean waitDyingActionDone = false;
    float dyingBarWidth;
    float targetDyingBarWidth;
    public boolean reCheck = false;

    public DyingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        setDebuff();
        lastAmt = amount;
        lastHp = owner.currentHealth;
        updateDescription();
    }

    public float calculateReduced(boolean percent){
        float reduced = 0F;
        if(this.owner.currentHealth>0){
            reduced = (float)this.amount/(float)this.owner.currentHealth;
        }
        reduced = Math.min(reduced, 0.25F);
        if(owner.hasPower(DyingBurstPower.POWER_ID)){
            reduced*=2F;
        }
        return reduced * (percent?100F:1F);
    }

    public boolean reachMax(){
        float reduced = 0F;
        if(this.owner.currentHealth>0){
            reduced = (float)this.amount/(float)this.owner.currentHealth;
        }
        return reduced>=0.25F;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if(type == DamageInfo.DamageType.NORMAL){
            return damage * (1F-calculateReduced(false));
        }
        return super.atDamageGive(damage, type);
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        if(owner.hb!=null){
            int amt = Math.min(amount,owner.currentHealth);
            targetDyingBarWidth = owner.hb.width * (float) amt / (float) owner.maxHealth;
        }
        if(dyingBarWidth != targetDyingBarWidth && targetDyingBarWidth > dyingBarWidth){
            this.dyingBarWidth = MathHelper.uiLerpSnap(this.dyingBarWidth, this.targetDyingBarWidth);
        }

        int amt = this.amount;
        int hp = this.owner.currentHealth;
        if(reCheck || amt!=lastAmt || hp!=lastHp){
            reCheck = false;
            if(this.owner instanceof AbstractMonster){
                if(!this.owner.isDeadOrEscaped())
                    ((AbstractMonster) this.owner).applyPowers();
            }
            updateDescription();
        }
        lastAmt = amt;
        lastHp = hp;
        if(amt>=hp && !waitDyingActionDone && !this.owner.isDeadOrEscaped()){
            waitDyingActionDone = true;
            addToTop(new DyingAction(this));
        }
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0]+(int)calculateReduced(true)+powerStrings.DESCRIPTIONS[1];
    }

    @SpirePatch(clz = AbstractCreature.class,method = "renderRedHealthBar")
    public static class RenderRedHealthBar {
        public static void Postfix(AbstractCreature _inst, SpriteBatch sb,float x,float y) {
            AbstractPower dPower = _inst.getPower(DyingPower.POWER_ID);
            if(dPower instanceof DyingPower){
                float healthBarWidth = ((DyingPower) dPower).dyingBarWidth;
                if(healthBarWidth>0){
                    int amt = Math.min(_inst.currentHealth,dPower.amount);
                    Color tmpColor = Color.DARK_GRAY.cpy();
                    tmpColor.a = _inst.hbAlpha;
                    sb.setColor(tmpColor);
                    if(amt>0){
                        sb.draw(ImageMaster.HEALTH_BAR_L, x - 20F* Settings.scale, y - 28F*Settings.scale,20F* Settings.scale,20F* Settings.scale);
                    }
                    sb.draw(ImageMaster.HEALTH_BAR_B, x, y -28.0F * Settings.scale, healthBarWidth, 20.0F * Settings.scale);
                    sb.draw(ImageMaster.HEALTH_BAR_R, x + healthBarWidth, y -28.0F * Settings.scale, 20.0F * Settings.scale, 20.0F * Settings.scale);
                }
            }
        }
    }
}
