package Theresa.card.power;

import Theresa.action.CureAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.power.buff.YoreLingerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Cure extends AbstractTheresaCard {
    public static final String ID = "theresa:Cure";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Cure() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.POWER,CardRarity.UNCOMMON,CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new CureAction(abstractPlayer,upgraded?4:5));
    }

    @Override
    public void onRemoveFromMasterDeck() {
        AbstractDungeon.player.increaseMaxHp(magicNumber,true);
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}






