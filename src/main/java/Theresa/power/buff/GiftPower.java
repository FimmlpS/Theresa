package Theresa.power.buff;

import Theresa.card.attack.TheBlooder;
import Theresa.power.AbstractTheresaPower;
import Theresa.relic.WhiteCrown;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Bite;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GiftPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:GiftPower";
    public GiftPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        setAmountDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        float extra = AbstractDungeon.player.hasRelic(WhiteCrown.ID)? 0.25F * this.amount : 0F;
        if(card.cardID!=null && (card.cardID.equals(TheBlooder.ID)||card.cardID.equals(Bite.ID)))
            return damage + this.amount + extra;
        return super.atDamageGive(damage, type, card) + extra;
    }
}
