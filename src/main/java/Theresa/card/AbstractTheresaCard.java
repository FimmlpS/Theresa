package Theresa.card;

import Theresa.helper.StringHelper;
import Theresa.patch.ColorEnum;
import Theresa.patch.DustPatch;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AbstractTheresaCard extends CustomCard {
    public static AbstractGameAction.AttackEffect attackEffect = AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
    public static AbstractGameAction.AttackEffect skillEffect = AbstractGameAction.AttackEffect.FIRE;

    public boolean exhaustAfterBlockDamage = false;
    public boolean dontExhaustIfExhaust = false;
    public boolean shouldLocked = false;

    public boolean shouldExhaust(){
        return this.exhaust;
    }

    public AbstractTheresaCard(String id, String name, int cost, String rawDescription, AbstractCard.CardType type, CardRarity rarity, CardTarget target) {
        super(id, name, StringHelper.getCardIMGPath(id, type), cost, rawDescription, type, ColorEnum.Theresa_COLOR, rarity, target);
    }

    public AbstractTheresaCard(String id, String name, int cost, String rawDescription, AbstractCard.CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, StringHelper.getCardIMGPath(id, type), cost, rawDescription, type, color, rarity, target);
    }

    public boolean isDust(){
        return DustPatch.dustManager.containsCard(this);
    }

    public void triggerWhenLingered(){

    }

    public void triggerWhenBecomeDust(){}
    public void triggerWhenNoLongerDust(){}

    public void atTurnEndIfDust(){

    }

    public int blockDamageIfDust(){
        return 0;
    }
}
