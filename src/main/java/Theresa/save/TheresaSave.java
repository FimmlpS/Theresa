package Theresa.save;

import Theresa.helper.CivilightHelper;
import Theresa.patch.BabelPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class TheresaSave {

    boolean ownQuiZa;
    ArrayList<CardSave> cardSaves;
    boolean enterBabel;
    boolean enteredBabel;
    boolean killedDead;
    boolean noKilledDead;


    public void onSave(){
        ownQuiZa = CivilightHelper.ownQuiZa;
        cardSaves = new ArrayList<>();
        for(AbstractCard c: CivilightHelper.cardSaveList){
            CardSave save = CardSave.toSave(c);
            if(save!=null){
                cardSaves.add(save);
            }
        }
        enterBabel = BabelPatch.EnterBabel;
        enteredBabel = BabelPatch.EnteredBabel;
        killedDead = BabelPatch.KilledDead;
        noKilledDead = BabelPatch.NoKilledDead;
    }

    public void onLoad(){
        CivilightHelper.ownQuiZa = ownQuiZa;
        if(cardSaves!=null){
            CivilightHelper.cardSaveList.clear();
            for(CardSave cardSave:cardSaves){
                try {
                    CivilightHelper.cardSaveList.add(CardSave.toCard(cardSave));
                }
                catch (Exception e){
                    //haha
                }
            }
        }
        BabelPatch.EnterBabel = enterBabel;
        BabelPatch.EnteredBabel = enteredBabel;
        BabelPatch.KilledDead = killedDead;
        BabelPatch.NoKilledDead = noKilledDead;
    }

    public void onDelete(){
        CivilightHelper.ownQuiZa = false;
        CivilightHelper.cardSaveList.clear();
        BabelPatch.EnterBabel = false;
        BabelPatch.EnteredBabel = false;
    }

    public void onReset(){
        BabelPatch.KilledDead = false;
        BabelPatch.NoKilledDead = false;
    }
}
