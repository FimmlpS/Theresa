package Theresa.card.attack;

import Theresa.action.CivilightAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SarkazSee extends AbstractTheresaCard {
    public static final String ID = "theresa:SarkazSee";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public SarkazSee() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);
        baseDamage = damage = 3;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn),attackEffect));
        addToBot(new CivilightAction(CardGroup.CardGroupType.HAND,1).setType(true,false,false,false,false).setCopy(1,true,false).setCostDiff(-1));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}

