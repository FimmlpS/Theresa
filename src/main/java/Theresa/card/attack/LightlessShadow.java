package Theresa.card.attack;

import Theresa.action.LightlessAction;
import Theresa.action.TheresaAttackAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LightlessShadow extends AbstractTheresaCard {
    public static final String ID = "theresa:LightlessShadow";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public LightlessShadow() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        baseDamage = damage = 6;
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new TheresaAttackAction(true));
        addToBot(new LightlessAction(abstractMonster,damage,damageTypeForTurn,attackEffect,magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeMagicNumber(1);
        }
    }
}

