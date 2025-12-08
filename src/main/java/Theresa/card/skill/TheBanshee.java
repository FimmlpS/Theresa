package Theresa.card.skill;

import Theresa.action.BansheeAction;
import Theresa.action.SpecialAnimationAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.helper.AttackHelper;
import Theresa.patch.OtherEnum;
import Theresa.patch.SilkPatch;
import Theresa.silk.TearSilk;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheBanshee extends AbstractTheresaCard {
    public static final String ID = "theresa:TheBanshee";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public TheBanshee() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.RARE,CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
        this.tags.add(OtherEnum.Theresa_Darkness);
        SilkPatch.setSilkForPreview(this,new TearSilk());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SpecialAnimationAction(AttackHelper.ForCharacter.Banshee));
        addToBot(new BansheeAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}







