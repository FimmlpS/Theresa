package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EnlightenmentPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:EnlightenmentPower";

    public EnlightenmentPower(AbstractCreature owner) {
        super(POWER_ID, owner);
        this.loadRegion("confusion");
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_CONFUSION", 0.05F);
    }

    public void onCardDraw(AbstractCard card) {
        if (card.cost >= 0) {
            int newCost = AbstractDungeon.cardRandomRng.random(3);
            card.setCostForTurn(newCost);
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
}
