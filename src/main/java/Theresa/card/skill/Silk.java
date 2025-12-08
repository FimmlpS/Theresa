package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.silk.NormalSilk;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Silk extends AbstractTheresaCard {
    public static final String ID = "theresa:Silk";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Silk() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        baseBlock = block = 9;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, block));
        addToBot(new GainEnergyAction(1));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            //upgradeBlock(3);
            SilkPatch.setSilkWithoutTrigger(this,new NormalSilk());
        }
    }
}





