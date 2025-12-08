package Theresa.patch;

import Theresa.character.Theresa;
import Theresa.helper.RegisterHelper;
import Theresa.modcore.TheresaMod;
import Theresa.relic.KnownRelic;
import Theresa.screen.TypeSelectScreen;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

public class TypePatch {
    public TypePatch() {

    }

    public static boolean isPuzzlerSelected() {
        return CardCrawlGame.chosenCharacter == ClassEnum.Theresa_CLASS && (Boolean) ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected");
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "render"
    )
    public static class RenderButtonPatch {
        public RenderButtonPatch() {
        }

        public static void Postfix(CharacterSelectScreen _inst, SpriteBatch sb) {
            if (TypePatch.isPuzzlerSelected()) {
                TypeSelectScreen.Inst.render(sb);
            }

        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "update"
    )
    public static class UpdateButtonPatch {
        public UpdateButtonPatch() {
        }

        public static void Prefix(CharacterSelectScreen _inst) {
            if (TypePatch.isPuzzlerSelected()) {
                TypeSelectScreen.Inst.update();
            }

        }
    }

    //开局
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "initializeRelicList"
    )
    public static class InitializeRelicListPatch {
        public static void Postfix(AbstractDungeon _inst) {
            if (AbstractDungeon.player instanceof Theresa) {
                if (TypeSelectScreen.getType() == 0) {
                    AbstractDungeon.player.increaseMaxHp(10, false);
                    AbstractRelic relicToObtain = new KnownRelic();
                    AbstractDungeon.bossRelicPool.remove(relicToObtain.relicId);
                    relicToObtain.obtain();
                    AbstractDungeon.player.reorganizeRelics();
                }
                else if (TypeSelectScreen.getType() == 1) {
                    AbstractDungeon.commonRelicPool.remove(Strawberry.ID);
                    AbstractDungeon.uncommonRelicPool.remove(Pear.ID);
                    AbstractDungeon.rareRelicPool.remove(Mango.ID);
                    AbstractDungeon.uncommonRelicPool.remove(SingingBowl.ID);
                    AbstractDungeon.rareRelicPool.remove(Calipers.ID);
                }

                //ban的遗物
                AbstractDungeon.shopRelicPool.remove(ChemicalX.ID);
                AbstractDungeon.bossRelicPool.remove(TinyHouse.ID);
            }
        }
    }
}
