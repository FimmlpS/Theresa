package Theresa.card.skill;

import Theresa.action.ConnectivityAction;
import Theresa.action.DrawSilkAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.silk.NormalSilk;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SilkPoem extends AbstractTheresaCard {
    public static final String ID = "theresa:SilkPoem";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public SilkPoem() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DrawCardAction(magicNumber,new ConnectivityAction(),true));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            SilkPatch.setSilkWithoutTrigger(this,new NormalSilk());
        }
    }
}


