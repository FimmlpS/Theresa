package Theresa.action;

import Theresa.patch.DustPatch;
import Theresa.patch.SilkPatch;
import Theresa.silk.AbstractSilk;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;

public class TerminateAction extends AbstractGameAction {
    private boolean freeToPlayOnce = false;
    private AbstractPlayer p;
    private int energyOnUse;
    private String silkID;
    private AbstractCard except;


    public TerminateAction(AbstractPlayer p, AbstractMonster m, int damage, DamageInfo.DamageType damageType, boolean freeToPlayOnce, int energyOnUse, String silkID,AbstractCard except) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.target = m;
        this.amount = damage;
        this.damageType = damageType;
        this.silkID = silkID;
        this.except = except;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect+=2;
            this.p.getRelic("Chemical X").flash();
        }

        if (effect > 0) {
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
            for(int i = 0;i<effect;i++) {
                addToTop(new DamageAction(target,new DamageInfo(p,amount,damageType),AttackEffect.SLASH_HEAVY));
            }
            if(silkID != null) {
                ArrayList<AbstractCard> hand = new ArrayList<>(p.hand.group);
                ArrayList<AbstractCard> silks = new ArrayList<>();
                for(AbstractCard card : hand){
                    if(card == except || card.costForTurn<=-1)
                        continue;
                    AbstractSilk silk = SilkPatch.SilkCardField.silk.get(card);
                    if(silk != null && silkID.equals(silk.silkID)) {
                        p.hand.removeCard(card);
                        silks.add(card);
                    }
                    if(silks.size() == effect)
                        break;
                }
                for(AbstractCard card : silks){
                    addToBot(new PlayCardAction(card,target,false));
                }
            }
            p.hand.refreshHandLayout();
        }

        this.isDone = true;
    }
}

