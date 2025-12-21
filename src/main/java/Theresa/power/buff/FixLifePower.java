package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class FixLifePower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:FixLifePower";

    public FixLifePower(AbstractCreature owner) {
        super(POWER_ID,owner);
    }

    @SpirePatch(clz = WeakPower.class,method = "atDamageGive",paramtypez = {float.class, DamageInfo.DamageType.class})
    public static class WeakPowerPatch {
        @SpirePostfixPatch
        public static float Postfix(float _ret, WeakPower _inst, float damage, DamageInfo.DamageType type){
            if(type == DamageInfo.DamageType.NORMAL){
                if(_inst.owner != null && _inst.owner.hasPower(POWER_ID)){
                    return damage * 1.25F;
                }
            }
            return _ret;
        }
    }

    @SpirePatch(clz = FrailPower.class,method = "modifyBlock",paramtypez = {float.class})
    public static class FrailPowerPatch {
        @SpirePostfixPatch
        public static float Postfix(float _ret, FrailPower _inst, float blockAmount){
            if(_inst.owner != null && _inst.owner.hasPower(POWER_ID)){
                return blockAmount * 1.25F;
            }
            return _ret;
        }
    }

    @SpirePatch(clz = VulnerablePower.class,method = "atDamageReceive",paramtypez = {float.class, DamageInfo.DamageType.class})
    public static class VulnerablePowerPatch {
        @SpirePostfixPatch
        public static float Postfix(float _ret, VulnerablePower _inst, float damage, DamageInfo.DamageType type){
            if (type == DamageInfo.DamageType.NORMAL){
                if(_inst.owner != null && _inst.owner.hasPower(POWER_ID)){
                    return damage * 0.75F;
                }
            }
            return _ret;
        }
    }
}
