package Theresa.modifier;

import Theresa.helper.StringHelper;
import Theresa.patch.SilkPatch;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

public class ConnectivityMod extends AbstractCardModifier {
    public static String ID = "theresa:ConnectivityMod";

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL " + StringHelper.TAGS.TEXT[10]+ (Settings.lineBreakViaCharacter ? " " : "") + LocalizedStrings.PERIOD;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        int initialAmt = SilkPatch.SilkCardField.silkTriggerTimes.get(card)+1;
        SilkPatch.SilkCardField.silkTriggerTimes.set(card,initialAmt);
    }

    @Override
    public void onRemove(AbstractCard card) {
        int initialAmt = SilkPatch.SilkCardField.silkTriggerTimes.get(card)-1;
        if(initialAmt<1)
            initialAmt = 1;
        SilkPatch.SilkCardField.silkTriggerTimes.set(card,initialAmt);
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ConnectivityMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
