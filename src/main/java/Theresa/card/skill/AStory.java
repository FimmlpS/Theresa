package Theresa.card.skill;

import Theresa.action.CivilightAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AStory extends AbstractTheresaCard {
    public static final String ID = "theresa:AStory";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public AStory() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        baseBlock = block = 3;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, block));
        addToBot(new CivilightAction(CardGroup.CardGroupType.HAND,1).setType(false,true,false,false,false).setCopy(1,true,false).setCostDiff(-1));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
}




