package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import Theresa.modifier.EternalMod;
import Theresa.power.buff.HatePower;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class NightBeforeWar extends AbstractTheresaCard {
    public static final String ID = "theresa:NightBeforeWar";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public NightBeforeWar() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new AStory();
        CardModifierManager.addModifier(this,new EternalMod());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for(int i = 0; i < this.magicNumber; i++) {
            addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new HatePower(abstractPlayer,1),1));
        }
    }

    @Override
    public void onRemoveFromMasterDeck() {
        AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new AStory(), Settings.WIDTH/2F, Settings.HEIGHT/2F));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}



