package Theresa.card.status;

import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Decide extends AbstractTheresaCard {
    public static final String ID = "theresa:Decide";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Decide() {
        super(ID,cardStrings.NAME,-2,cardStrings.DESCRIPTION,CardType.STATUS,CardColor.COLORLESS,CardRarity.SPECIAL,CardTarget.NONE);
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void triggerWhenDrawn() {
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if(!m.isDeadOrEscaped()){
                addToBot(new ApplyPowerAction(m,m,new StrengthPower(m,1),1));
            }
        }
        if(upgraded){
            boolean weak = AbstractDungeon.cardRandomRng.randomBoolean();
            if(weak)
                addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new WeakPower(AbstractDungeon.player,1,false),1));
            else {
                addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new FrailPower(AbstractDungeon.player,1,false),1));
            }
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}






