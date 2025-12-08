package Theresa.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import spireTogether.patches.VictoryScreenPatch;

public class TogetherPatch {
    @SpirePatch(
            clz = VictoryScreenPatch.class,
            method = "HandleVictory",
            requiredModId = "spireTogether",
            optional = true
    )
    public static class VictoryPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(boolean trueVictory) {
            if(BabelPatch.enableExtra())
                return SpireReturn.Return();
            return SpireReturn.Continue();
        }
    }
}
