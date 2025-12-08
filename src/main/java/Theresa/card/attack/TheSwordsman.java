package Theresa.card.attack;

import Theresa.action.LessCopyAction;
import Theresa.action.SpecialAnimationAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.helper.AttackHelper;
import Theresa.patch.SilkPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheSwordsman extends AbstractTheresaCard {
    public static final String ID = "theresa:TheSwordsman";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public TheSwordsman() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        baseDamage = damage = 14;
        baseMagicNumber = magicNumber = 2;
        SilkPatch.SilkCardField.silkTriggerTimes.set(this,magicNumber);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SpecialAnimationAction(AttackHelper.ForCharacter.Swordsman,false));
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new LessCopyAction(this,1));
    }



    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeMagicNumber(1);
            SilkPatch.SilkCardField.silkTriggerTimes.set(this,magicNumber);
        }
    }
}



