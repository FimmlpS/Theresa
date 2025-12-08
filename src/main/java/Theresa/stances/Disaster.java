package Theresa.stances;

import Theresa.power.buff.DisasterPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;

public class Disaster extends AbstractStance {
    public static final String STANCE_ID = "theresa:Disaster";
    private static final StanceStrings stanceStrings = CardCrawlGame.languagePack.getStanceString(STANCE_ID);

    public Disaster(){
        this.ID = STANCE_ID;
        this.name = stanceStrings.NAME;
        updateDescription();
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if(damageType == DamageInfo.DamageType.NORMAL)
            return 1.2F*damage;
        return damage;
    }

    @Override
    public void onEnterStance() {
        CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
    }

    @Override
    public void updateDescription() {
        this.description = stanceStrings.DESCRIPTION[0];
    }

    @Override
    public void onExitStance() {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DisasterPower(AbstractDungeon.player)));
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "damage")
    public static class DisasterPatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer _inst, DamageInfo info) {
            if(_inst.lastDamageTaken>0 && _inst.stance instanceof Disaster){
                AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(new NeutralStance()));
            }
        }
    }
}
