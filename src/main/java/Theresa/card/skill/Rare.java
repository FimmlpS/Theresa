package Theresa.card.skill;

import Theresa.action.SetNewCostAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

public class Rare extends AbstractTheresaCard {
    public static final String ID = "theresa:Rare";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Rare() {
        super(ID,cardStrings.NAME,3,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.RARE,CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        tags.add(OtherEnum.Theresa_Darkness);
        shouldLocked = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BufferPower(abstractPlayer,1),1));
        if(abstractPlayer.hand.contains(this)){
            int index = abstractPlayer.hand.group.indexOf(this);
            if(index-1>=0){
                AbstractCard c = abstractPlayer.hand.group.get(index-1);
                addToBot(new SetNewCostAction(c,-magicNumber).forTurn());
            }
            if(index+1<abstractPlayer.hand.size()){
                AbstractCard c = abstractPlayer.hand.group.get(index+1);
                addToBot(new SetNewCostAction(c,-magicNumber).forTurn());
            }
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}



