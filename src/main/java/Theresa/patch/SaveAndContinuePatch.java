package Theresa.patch;

import Theresa.save.TheresaSave;
import Theresa.save.TheresaSaveAndContinue;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

public class SaveAndContinuePatch {
    public static TheresaSave theresaSave = new TheresaSave();

    @SpirePatch(clz = SaveAndContinue.class, method = "save")
    public static class SavePatch {
        @SpirePostfixPatch
        public static void Postfix(SaveFile save) {
            theresaSave.onSave();
            TheresaSaveAndContinue.saveTheresa(theresaSave);
        }
    }

    @SpirePatch(clz = SaveAndContinue.class, method = "loadSaveFile",paramtypez = {AbstractPlayer.PlayerClass.class})
    public static class LoadPatch {
        @SpirePostfixPatch
        public static SaveFile Postfix(SaveFile _ret, AbstractPlayer.PlayerClass c) {
            TheresaSave e = TheresaSaveAndContinue.loadTheresa(c);
            if(e!=null){
                theresaSave = e;
                theresaSave.onLoad();
            }
            return _ret;
        }
    }

    @SpirePatch(clz = SaveAndContinue.class,method = "deleteSave")
    public static class DeletePatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer p){
            theresaSave.onDelete();
            TheresaSaveAndContinue.deleteTheresa(p.chosenClass);
        }
    }

    @SpirePatch(clz = AbstractDungeon.class,method = "nextRoomTransition",paramtypez = {SaveFile.class})
    public static class ResetPatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractDungeon _inst, SaveFile saveFile){
            if(AbstractDungeon.floorNum<=1){
                theresaSave.onReset();
            }
        }
    }
}
