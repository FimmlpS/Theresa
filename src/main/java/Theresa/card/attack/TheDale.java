package Theresa.card.attack;

import Theresa.action.DaleAction;
import Theresa.action.SpecialAnimationAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.helper.AttackHelper;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheDale extends AbstractTheresaCard {
    public static final String ID = "theresa:TheDale";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public TheDale() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ALL_ENEMY);
        baseDamage = damage = 9;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SpecialAnimationAction(AttackHelper.ForCharacter.Dale));
        addToBot(new DaleAction(multiDamage,damageTypeForTurn,skillEffect));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}



