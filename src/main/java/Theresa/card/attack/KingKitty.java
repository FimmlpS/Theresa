package Theresa.card.attack;

import Theresa.action.ChooseDustToPileAction;
import Theresa.action.RandomSilkAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.DustPatch;
import Theresa.patch.OtherEnum;
import Theresa.patch.SilkPatch;
import Theresa.silk.NormalSilk;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KingKitty extends AbstractTheresaCard {
    public static final String ID = "theresa:KingKitty";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public KingKitty() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);
        baseDamage = damage = 7;
        SilkPatch.setSilkForPreview(this,new NormalSilk());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn),attackEffect));
        addToBot(new RandomSilkAction(DustPatch.dustManager.dustCards,new NormalSilk(),false,true));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}

