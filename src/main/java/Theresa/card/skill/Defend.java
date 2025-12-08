package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import Theresa.relic.QuiZaTueStaWord;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend extends AbstractTheresaCard {
    public static final String ID = "theresa:Defend";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Defend(){
        this(0);
    }

    public Defend(int upgrades) {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.BASIC,CardTarget.SELF);
        baseBlock = block = 5;
        tags.add(CardTags.STARTER_DEFEND);
        this.timesUpgraded = upgrades;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer,block));
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            upgradeBlock(3);
        }
        else if(QuiZaTueStaWord.canUpgrade()){
            this.upgradeBlock(3);
            ++this.timesUpgraded;
            this.upgraded = true;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            this.initializeTitle();
        }
    }

    @Override
    public boolean canUpgrade() {
        return !upgraded || QuiZaTueStaWord.canUpgrade();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Defend(timesUpgraded);
    }
}

