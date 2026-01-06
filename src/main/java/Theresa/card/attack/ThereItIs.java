package Theresa.card.attack;

import Theresa.action.TheresaAttackAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.silk.AbstractSilk;
import Theresa.silk.MindSilk;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ThereItIs extends AbstractTheresaCard {
    public static final String ID = "theresa:ThereItIs";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public ThereItIs() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        baseDamage = damage = 7;
        baseMagicNumber = magicNumber = 1;
        SilkPatch.setSilkWithoutTrigger(this,new MindSilk());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new TheresaAttackAction(true));
        for(int i =0;i<magicNumber;i++){
            addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn), skillEffect));
        }
    }

    @Override
    public void applyPowers() {
        int tmp = baseMagicNumber;
        AbstractSilk silk = SilkPatch.SilkCardField.silk.get(this);
        if (silk != null) {
            baseMagicNumber += silk.amount;
        }
        super.applyPowers();
        magicNumber = baseMagicNumber;
        baseMagicNumber = tmp;
        isMagicNumberModified = baseMagicNumber != magicNumber;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int tmp = baseMagicNumber;
        AbstractSilk silk = SilkPatch.SilkCardField.silk.get(this);
        if (silk != null) {
            baseMagicNumber += silk.amount;
        }
        super.calculateCardDamage(mo);
        magicNumber = baseMagicNumber;
        baseMagicNumber = tmp;
        isMagicNumberModified = baseMagicNumber != magicNumber;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }
}


